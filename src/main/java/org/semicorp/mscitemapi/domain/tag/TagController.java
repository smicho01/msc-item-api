package org.semicorp.mscitemapi.domain.tag;


import lombok.extern.slf4j.Slf4j;
import org.semicorp.mscitemapi.domain.tag.response.TagResponse;
import org.semicorp.mscitemapi.utils.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static org.semicorp.mscitemapi.utils.Logger.logInfo;

@RestController
@RequestMapping("/api/v1/tag")
@Slf4j
public class TagController {


    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return new ResponseEntity<>(tagService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{tagId}")
    public ResponseEntity<Tag> getTagById(@PathVariable(value="tagId") String tagId) {
        return new ResponseEntity<>(tagService.findById(tagId), HttpStatus.OK);
    }

    @GetMapping("/name/{tagName}")
    public ResponseEntity<Tag> getTagByName(@PathVariable(value="tagName") String tagName) {
        return new ResponseEntity<>(tagService.findByName(tagName), HttpStatus.OK);
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Tag>> getTagsForQuestionId(@PathVariable(value="questionId") String questionId) {
        return new ResponseEntity<>(tagService.findTagsForQuestionId(questionId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TagResponse> addTag(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                      @RequestBody Tag tag) {

        TagResponse result = tagService.insert(tag);

        logInfo("Adding new tag: " + result, token);
        log.info("Adding new tag: {} ", result);
        return new ResponseEntity<>(result, result.getStatus());
    }

}
