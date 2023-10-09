package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //저장소 선언
@RequiredArgsConstructor //생성자 주입 파이널!!!!!!
public class ItemRepository {

    @PersistenceContext //영속성 선언
    private final EntityManager em;//관리 선언

    public void save(Item item) {
        if(item.getId() == null) {
            em.persist(item); //값이 없으면 영속성 컨텍스트에 저장, 처음등록 하는 거니깐 persist 추가
        } else {
            em.merge(item); //준영속 상태의 entity를 영속 상태로 변환? 있지만 등록 안된거를 등록한다? merge는 sql에서 업데이트
        }
    }

    public Item findOne(Long id) { return em.find(Item.class, id); }//클래스 컬럼을 지정하고 pk지정 하면 가져와준다

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();//쿼리를 만들어 조회 하고 그값을 받을클래스 지정하고 겟 리졀리스트
    }

}
