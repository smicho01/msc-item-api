package org.semicorp.mscitemapi.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.semicorp.mscitemapi.domain.question.Question;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class QuestionResponse implements BasicResponse {

    private Question response;
    private int code;

}
