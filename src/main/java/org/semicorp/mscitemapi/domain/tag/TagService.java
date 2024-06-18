package org.semicorp.mscitemapi.domain.tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.tag.dao.tag.TagDAO;
import org.semicorp.mscitemapi.domain.tag.dao.tag.TagRow;
import org.semicorp.mscitemapi.domain.tag.response.TagResponse;
import org.semicorp.mscitemapi.kafka.tag.entity.QuestionTagsList;
import org.semicorp.mscitemapi.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagService {

    private final Jdbi jdbi;
    private final TagQuestionService tagQuestionService;

    public List<Tag> findAll() {
        return jdbi.onDemand(TagDAO.class).findAll();
    }

    public Tag findById(String tagId) {
        return jdbi.onDemand(TagDAO.class).findById(tagId);
    }

    public Tag findByName(String tagName) {
        return jdbi.onDemand(TagDAO.class).findByName(tagName);
    }

    public void assignTagsToQuestion(QuestionTagsList questionTagsList) {
        String questionId = questionTagsList.getQuestionId();
        List<String> tagStrings = questionTagsList.getTags();
        if(tagStrings.isEmpty()) {
            return;
        }
        log.info("Assigning {} tags to question id: {}", tagStrings.size(), questionId);

        for(String tagString: tagStrings) {
            // Insert tag if not exists or return existing tag
            TagResponse insertResponse = insert(new Tag(null, tagString));
            System.out.println("Insert Tag Response" + insertResponse.toString());
            if(insertResponse.getTag() != null) {
                tagQuestionService.insert(new TagQuestion(insertResponse.getTag().getId(), questionId));
            }
        }
    }

    public TagResponse insert(Tag tag) {
        tag.setId(UUID.randomUUID().toString());

        // Clean tag name
        tag.setName(StringUtils.cleanString(tag.getName()));
        // Do not insert if any incorrect characters in string after above cleaning.
        if(StringUtils.incorrectString(tag.getName())) {
            return new TagResponse(tag, HttpStatus.BAD_REQUEST, "Incorrect characters in tag name");
        }

        // Check if tag exists
        Tag exists = jdbi.onDemand(TagDAO.class).findByName(tag.getName());
        if(exists != null) {
            return new TagResponse(exists, HttpStatus.OK, "Tag exists");
        }

        try {
            boolean insert = jdbi.onDemand(TagDAO.class).insert(new TagRow(tag));
        } catch (Exception e) {
            log.error("Can't insert new tag: {}", tag);
            return new TagResponse(null, HttpStatus.BAD_REQUEST, "Unexpected error");
        }
        return new TagResponse(tag, HttpStatus.CREATED, "Created");
    }
}
