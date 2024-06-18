package org.semicorp.mscitemapi.domain.tag;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.question.dao.QuestionDAO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.semicorp.mscitemapi.domain.tag.dao.TagDAO;
import org.semicorp.mscitemapi.domain.tag.dao.TagRow;
import org.semicorp.mscitemapi.domain.tag.response.TagResponse;
import org.semicorp.mscitemapi.kafka.tag.entity.QuestionTagsList;
import org.semicorp.mscitemapi.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TagService {


    private final Jdbi jdbi;

    public TagService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

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
        log.info("Assigning tags list to question id: {}", questionTagsList.getQuestionId());
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
