package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

//회원 등록 폼 객체
@Getter @Setter
public class MemberForm { //이녀석이 DTO역할

    @NotEmpty(message = "회원 이름은 필수 입니다.")//비면 안도는거 비어 있을시 메시지 출력,비슷한 블랭크는 스페이스바
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
