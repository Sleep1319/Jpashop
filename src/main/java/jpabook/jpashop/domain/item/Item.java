package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속을 강제하기 위한? 실체는 불가
@DiscriminatorColumn(name = "dtype") //하위 클래스 가져올 키를 구분 (외래키 구분느낌)
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue//프라이머리키 자동생성
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")//items를 다수 아래 카테고리 다수
    private List<Category> categories = new ArrayList<Category>();

    // 비지니스 로직
    /**
     * stock 증가 => stockQuantity setter로 조절
     * 위 처럼 하면 OOP적이지 못함
     */
    //스톡 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;//지금 수량에 새로 들어온 수량을 추가
    }

    //스톡감소
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;//지금 수량에서 빠질 수를 빼서 restStock에 저장
        if ( restStock < 0 ) {
            throw new NotEnoughStockException("재고 부족");
        }
        this.stockQuantity = restStock; //0개 미만이 아닐때 다시 재고에 저장
    }
//    public void addStock(int quantity) {
//        this.stockQuantity += quantity;
//    }
//
//    public void removeStock(int quantity) {
//        int restStock = this.stockQuantity - quantity;
//        if (restStock < 0) {
//            throw new NotEnoughStockException("need more stock");
//        }
//        this.stockQuantity = restStock;
//    }
}
