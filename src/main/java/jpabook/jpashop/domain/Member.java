package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue //아이덴디티나 인크리즈 같은거
    @Column(name = "member_id")
    private Long id;
    private String name;

    @Embedded //우리가 마든 자료형으로 맵핑 하겠다
    private Address address;

    @OneToMany(mappedBy = "member") //누구와 1대 다를 지정할꺼냐
    private List<Order> orders = new ArrayList<>();
}

//미리 써준거
//@Entity
//@Getter
//@Setter
//public class Member {
//
//    @Id
//    @GeneratedValue
//    @Column(name = "member_id")
//    private Long id;
//
//    private String name;
//
//    @Embedded   // Embedded, 또는 Embeddable(클래스에) 둘 중 하나의 어노테이션만 있어도 되긴 한다.
//    private Address address;
//
//    // @JsonIgnore 엔티티에 넣으면 안 좋다.
//    @OneToMany(mappedBy = "member")  // order 객체에 있는 member 필드에 의해서 매핑되었다.
//    // 연관관계의 비 주인임을 표시. 이로써 Member 객체는 Order와의 관계를 수정할 수 없게 됨
//    // (테이블로 생각한다면, FK를 가지고 있는 테이블이 관계를 조정한다고 생각하면 됨.
//    private List<Order> orders = new ArrayList<>();
//
//}
/*
컬럼은 필드와 맵핑, 테이블은 엔티티와 맵핑
@Entity는 jpa에서 쓰는



 */