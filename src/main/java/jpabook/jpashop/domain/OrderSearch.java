package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String memberName;//멤버 고유값
    private OrderStatus orderStatus;//주문 상태


}