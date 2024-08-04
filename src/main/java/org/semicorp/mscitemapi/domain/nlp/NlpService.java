package org.semicorp.mscitemapi.domain.nlp;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.semicorp.mscitemapi.domain.nlp.response.CreateEmbeddingResponse;
import org.semicorp.mscitemapi.domain.question.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class NlpService {

    @Value("${app.config.service.nlp.url}")
    private String nlpServiceUrl;
    @Value("${app.config.service.nlp.token}")
    private String nlpServiceToken;

    private final RestTemplate restTemplate;

    public NlpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void createEmbedding(Question question) {
        log.info("Create embedding and store in mongodb for question id: {}", question.getId());
        String url = nlpServiceUrl + "/embedding/mongostore";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-access-tokens", nlpServiceToken);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("question", question.getTitle());
            jsonObject.put("questionId", question.getId());

            HttpEntity<String> request = new HttpEntity<>(jsonObject.toJSONString(), headers);

            ResponseEntity<CreateEmbeddingResponse> exchange = restTemplate.exchange(
                    url, HttpMethod.POST, request, CreateEmbeddingResponse.class);

            HttpStatus statusCode = exchange.getStatusCode();
            switch (statusCode) {
                case OK -> log.info("Embedding created and stored in MongoDB via NLP service at url: {}", nlpServiceUrl);
                case CREATED -> log.info("Embedding created and stored in MongoDB via NLP service at url: {}", nlpServiceUrl);
                default -> log.warn("Potential issue while creating embedding via NLP service at url: {}", nlpServiceUrl);
            }


        } catch (Exception e) {
            log.error("Error while creating embedding: ERROR: {}", e.getMessage());
        }
    }
}
