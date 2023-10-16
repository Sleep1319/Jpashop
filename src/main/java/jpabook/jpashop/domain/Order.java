package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)//다수의 오더와 하나의 멤버?
    @JoinColumn(name = "member_id")//길은 하나로 통한다
    private Member member;//주문회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)//모든 타입이건 다 쓰기 위한 
    private List<OrderItem> orderItems = new ArrayList<>();// 한 오더에 대한 주문 아이템들

    @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
    @JoinColumn(name = "delivery_id") //배송 하나당 배송하나
    private Delivery delivery; //배송정보


    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING) //열거형
    private OrderStatus status; //주문 상태 주문이 오더 또는 캔슬 상태

    //연관 간계 메서드~
    public void setMember(Member member) {
        this.member = member;//멤버 정보를 받아서 세팅하고
        member.getOrders().add(this);//멤버 객체에서 연관관계가 있는 오더를 가져오는데 추가를 해서 가져온다 현재 오더를?
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);//받아온 주문 아이템 정보를 추가하고(오더인 여기다가)
        orderItem.setOrder(this);//오더 아이템에 연관 관계가 있는 현재 오더를 세팅한다
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;//배송정보를 받고 (오더에다가)
        delivery.setOrder(this);//배송정보와 연관 관계가있는 현재 오더를 세팅
    }

    //생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) { // ... = 가변인자 오더아이템들의 들어온 정보수만큼 증가 되게 하기 위합
        Order order = new Order();
        order.setMember(member);//오더에 받아온 멤버값
        order.setDelivery(delivery);//오더에 받아온 배송값

        for( OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);//너가 받아올 오더 아이템이 있는 만큼 오더에 아이템 정보들 추가
        }

        order.setStatus(OrderStatus.ORDER);// 오더에 셋 스테이터스 (열거형) 오더로 변경
        order.setOrderDate(LocalDateTime.now());// 현재 시간으로 대입

        return order;//오더 정보를 리턴
    }

    //비지니스 로직
    /**
     * 주문 취소
     */
    public void cancel() {
        if( delivery.getStatus() == DeliveryStatus.COMP) {// 열거형 스태이터스가 컴플일때
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가함"); //응 꺼져
        }

        this.setStatus(OrderStatus.CANCLE); //현 오더의 스태이스를 취소 시키고
        for( OrderItem orderItem : orderItems ) { //오더에 저장된 오더 아이템 정보 만큼 가져오고
            orderItem.cancel(); //고걸 다 캔슬 그다음 수량 복구
        }
    }

    /**
     * 전체 가격 조회 로직
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for( OrderItem orderItem : orderItems ) {
            totalPrice += orderItem.getTotalPrice(); //각 주문 아이템의 최종값을 받아서
        }
        return totalPrice; //최종값 리턴
    }


}

//만들어 주신거
//@Entity
//@Table(name = "orders")
//@Getter @Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성자 접근제어 protected
//public class Order {
//
//    @Id
//    @GeneratedValue
//    @Column(name = "order_id")
//    private Long id;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)   // cascade는 유의해서 사용해야한다.
//    private List<OrderItem> orderItems = new ArrayList<>();
//
//    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "delivery_id") // 주로 Order에서 Delivery를 조회하기 때문에, 연관관계의 주인(FK와 가까운 쪽)을 Order로 둔다.
//    private Delivery delivery;
//
//    private LocalDateTime orderDate; // 주문시간
//
//    @Enumerated(EnumType.STRING) // 타입은 반드시 String으로!
//    private OrderStatus status; // 주문 상태 [ORDER, CANCLE)
//
//    //==연관관계 (편의)메서드==///
//    // 주체적으로 관계를 컨트롤하는 클래스에 넣으면 좋다.
//    public void setMember(Member member) {
//        this.member = member;
//        member.getOrders().add(this);
//    }
//
//    public void addOrderItem(OrderItem orderItem) {
//        orderItems.add(orderItem);
//        orderItem.setOrder(this);
//    }
//
//    public void setDelivery(Delivery delivery) {
//        this.delivery = delivery;
//        delivery.setOrder(this);
//    }
//
//    //==생성 메서드==//
//    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
//        Order order = new Order();
//        order.setMember(member);
//        order.setDelivery(delivery);
//        for (OrderItem orderItem : orderItems) {
//            order.addOrderItem(orderItem);
//        }
//        order.setStatus(OrderStatus.ORDER);
//        order.setOrderDate(LocalDateTime.now());
//        return order;
//    }
//
//    //==비즈니스 로직==//
//    /**
//     * 주문 취소
//     */
//    public void cancel() {
//        if (delivery.getStatus() == DeliveryStatus.COMP) {
//            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
//        }
//
//        this.setStatus(OrderStatus.CANCLE);
//        for (OrderItem orderItem : orderItems) {
//            orderItem.cancel();
//        }
//    }
//
//    //==조회 로직==//
//    /**
//     * 전체 주문 가격 조회
//     */
//    public int getTotalPrice() {
//        int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;
////        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
//    }
//
//}