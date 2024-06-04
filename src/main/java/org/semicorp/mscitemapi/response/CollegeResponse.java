package org.semicorp.mscitemapi.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.semicorp.mscitemapi.domain.college.College;


@Getter
@Setter
@ToString
@AllArgsConstructor
public class CollegeResponse implements BasicResponse {

    private College response;
    private int code;
}
