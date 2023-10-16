package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",//다대다 위험성에 그걸 받아주는 카테고리 아이템 테이블 작성
            joinColumns = @JoinColumn(name = "category_id"),//받아올 다수의 카테고리 아이디
            inverseJoinColumns = @JoinColumn(name = "item_id"))//받아올 다수의 아이템 아이디
    private List<Item> items = new ArrayList<>();// 그정보를 아이템에 저장?

    @ManyToOne(fetch = LAZY)//매니로 받아올 준비 준비
    @JoinColumn(name = "parent_id")//받을 원의 컬럼명
    private Category parent;//받을 원

    @OneToMany(mappedBy = "parent")//parent를 원으로
    private List<Category> child = new ArrayList<>();//다수

    //연관 관계 메서드
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);//여기서 디스는 부모를 뜻한다 즉 현재 부모를 세팅하는데 그게 차일드한테 현 부모를 세팅하는거니 연결관계를 뜻? 뭔소리야
    }

}

//@Entity
//@Getter
//@Setter
//public class Category {
//
//    @Id @GeneratedValue
//    @Column(name = "category_id")
//    private Long id;
//
//    private String name;
//
//    @ManyToMany // 지양해야 할 방법
//    @JoinTable(name = "category_item",
//        joinColumns = @JoinColumn(name = "category_id"),
//        inverseJoinColumns = @JoinColumn(name = "item_id"))  // 중간 테이블 매핑
//    private List<Item> items = new ArrayList<>();
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "parent_id")
//    private Category parent;
//
//    @OneToMany(mappedBy = "parent")
//    private List<Category> child = new ArrayList<>();
//
//    //==연관관계 (편의)메서드==///
//    public void addChildCategory(Category child) {
//        this.child.add(child);
//        child.setParent(this);
//    }
//}
