package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service//서비스 선언
@RequiredArgsConstructor //생성자 주입
@Transactional//트랜잭션 관리용 롤백 커밋 명시 없어도 되고 등..
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //유효성 검사이용 중복회원 검증
        memberRepository.save(member); // save jpa제공 메소드
        return member.getId();
    }

    //중복 회원 검증 20번 줄
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());//리스트 멤버 클래스 타입으로 멤버 찾기 메소드 이용
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("가입된 회원 입니다.");
            //중복이면 에러를 발생 시커서 진행을 막는다
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 조회
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
