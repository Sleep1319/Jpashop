package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)//배송 하나당 오더 하나
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)//배송 현황을 업데이트
    private DeliveryStatus status; //Enum준비 comp배송
}

//@Entity
//@Getter
//@Setter
//public class Delivery {
//
//    @Id @GeneratedValue
//    @Column(name = "delivery_id")
//    private Long id;
//
//    @OneToOne(mappedBy = "delivery", fetch = LAZY)
//    private Order order;
//
//    @Embedded
//    private Address address;
//
//    @Enumerated(EnumType.STRING) // 타입은 반드시 String으로!
//    private DeliveryStatus status; //READY, COMP
//}
