package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository //저장소 선언
@RequiredArgsConstructor //생성자 주입
public class ItemRepository {

    @PersistenceContext //영속성 선언
    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null) {
            em.persist(item); //값이 없으면 영속성 컨텍스트?에 저장
        } else {
            em.merge(item); //준영속 상태의 entity를 영속 상태로 변환? 있지만 등록 안된거를 등록한다?
        }
    }
}
