package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue//프라이머리키 지정 번호 자동생성 지정
    @Column(name = "order_item_id")//컬럼명 지정
    private Long id;

    @ManyToOne(fetch = LAZY) //다대1준비
    @JoinColumn(name = "item_id")//여러 주문이 가르키는? 아이템 하나
    private Item item; //주문상품

    @ManyToOne(fetch = LAZY)//다대1 준비
    @JoinColumn(name = "order_id")//여러 주문정보가 있을고 그걸 하나로 받는다?
    private Order order; //주문

    private int orderPrice; //주문가격
    private int count;

    //생성 메서드 주문 아이템 정보를 주문 가격 총 개수 받아옴
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        //오더 아이템 클래스로 값 저장
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;//주문 아이템 최종 정보를 리턴
    }

    
    //비지니스 로직
    public void cancel() {
        getItem().addStock(count);// 취소시 수량 복구
    }
    
    //전체가격 조회 로직
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
//만들어 주신거
//@Entity
//@Getter @Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성자 접근제어 protected
//public class OrderItem {
//
//    @Id @GeneratedValue
//    @Column(name = "order_item_id")
//    private Long id;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "item_id")
//    private Item item;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "order_id")
//    private Order order;
//
//    private int orderPrice; // 주문 가격
//    private int count; //주문 수량
//
//    //==생성 메서드==//
//    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
//        OrderItem orderItem = new OrderItem();
//        orderItem.setItem(item);
//        orderItem.setOrderPrice(orderPrice);
//        orderItem.setCount(count);
//
//        item.removeStock(count);
//        return orderItem;
//    }
//
//    //==비즈니스 로직==//
//    public void cancel() {
//        getItem().addStock(count);
//    }
//
//    //==조회 로직==//
//
//    /**
//     * 주문상품 전체 가격 조회
//     */
//    public int getTotalPrice() {
//        return getOrderPrice() * getCount();
//    }
//}
