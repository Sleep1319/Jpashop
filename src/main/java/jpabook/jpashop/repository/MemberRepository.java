package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext //영속성
    private EntityManager em;

    public void save(Member member) {
        em.persist(member); //새로운 회원 정보를 영속성 컨테스트 실행
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);//뭐 찾는다고 멤버 클래스 아이디에 저장
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)//전체 가져오는 쿼리문 사용 멤버 클래스 형식에 저장
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name",
                Member.class)
                .setParameter("name", name)
                .getResultList();
    }
    
    // @Repository : 스프링 빈으로 등록 . JPA 예외를 스프힝 기반 예외로 변환
    // @PersistenceContext : EntityManager 주입
    // @PersistenceUnit : EntityManagerFactory 주입
    /*
    회원 서비스
    상품 도메인
    상품 엔티티
    상품 레포지토리
    주문 도메인
     */
}
