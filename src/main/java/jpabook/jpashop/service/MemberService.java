package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service//서비스 선언
@RequiredArgsConstructor //필수 인자 생성자 final이 붙은 필드 생성자를 자동 생성함
@Transactional(readOnly = true)//트랜잭션 관리용 롤백 커밋 명시 없어도 되고 등.. 리드온리 제약을 걸어 성능향상 도모 jpa플러시
//jpa의 모든 데이터 변경 로직은 가급적 트렌잭션에서 실행 되어야 한다 (트랜잭션 쪼갤수 없는 최소의 단위)
//ORM은 자동쿼리 생성을 해준다
//클래스 레벨에서 어노테이션을 걸면 모든 퍼블릭 메소드가 트랜잭션이 걸린다
//스프링 어노테이션을 이용해야 함(javax는 사용 금지 그냥 다른거다)
//readOnly는 조회시 성능 최적화 set이 안먹히니깐(조회시 에만)
public class MemberService {

    //Autowired 쓰면 안됨.
    //필드 인젝션(생성자 주입 이기에 새로운 퍼블릭이 생겨버린다)
    private final MemberRepository memberRepository;

    /*
    * 세터를 사용하면 안되기 때문에 사용하면 안되는것들
    * setter 인젝션 예시
    * @Autowired
    * public void setMemberRepository(MemberRepository memberRepository){
    *       this.memberRepository = memberRepository;
    * }
    * @따로 위에 파이널 선언 어노테이션 안쓰면 아래가 사용 법이다
    * Autowired 생성자 주입 (그나마 적절한 예시?)
    * lombok 어노테이션 으로 인해서 생성자 생략
    * public MemberRepository(MemberRepository memberRepository) {
    *       this.memberRepository = memberRepository;
    * }
    *
    * */
    /**
     * 회원가입
     * */
    @Transactional//리드온리가 되면 안되기 때문에 따로 작업을 걸어버린다
    public Long join(Member member) {
        validateDuplicateMember(member); //유효성 검사이용 중복회원 검증 맨위에 해둬야 가입전에 검증을 한다
        memberRepository.save(member);
        return member.getId();
    }
    
    //중복 검증
    //멀티쓰레드 상황을 고려해서 DB에 name을 유니크 제약 조건을 거는것이 좋다
    //그렇지 않으면 두 회원이 동일한 이름으로 동시에 가입 하는 경우, validate를 통과할 수도 있다
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    

    //회원 전체 조회
    public List<Member> findMembers() { return memberRepository.findAll(); }

    //한명 조회
    public Member findOne(Long memberId) { return memberRepository.findOne(memberId); }//파라미터갑 차별성이 좀 필요하다 이젠
    
    //회원 정보 수정
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name); //변경감지
    }
    

    //중복 회원 검증 20번 줄
//    private void validateDuplicateMember(Member member) {
//        List<Member> findMembers = memberRepository.findByName(member.getName());//리스트 멤버 클래스 타입으로 멤버 찾기 메소드 이용
//        if(!findMembers.isEmpty()) {
//            throw new IllegalStateException("가입된 회원 입니다.");
//            //중복이면 에러를 발생 시커서 진행을 막는다
//        }
//    }
//
//    //회원 전체 조회
//    public List<Member> findMembers() {
//        return memberRepository.findAll();
//    }
//
//    //회원 조회
//    public Member findOne(Long id) {
//        return memberRepository.findOne(id);
//    }
}
/*
*
* */