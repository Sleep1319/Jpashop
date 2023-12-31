package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.Objects;

@Embeddable
@Getter
public class Address {
    
    private String city;
    private String street;
    private String zipcode;
    
    protected Address() {//접근제어자를 프로텍티드를 기본생성자로 만든다 주소를 나중에 수정을 할때 사용 가능하게 한다
    }

    public Address (String city,String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
/*
엔티티 설계시 주의점
    1. 엔티티에선 가급적 Setter 사용을 X
        테이블 하고 맵핑되기에 민감하다
        Setter가 다 있다고 치면 테이블 단위이기에 수정 될 수 있는 포인트가 너무 많음
        유지보수 어렵다
        세터가 없으면 좀 힘드니 열어도 리펙토링 할때 다 제거한다 그다음 프로텍티드 생성로 열든 상속관계를 만들던 등등


 */
// VO 개념의 클래스
//@Embeddable
//@Getter
//@Setter(AccessLevel.PRIVATE) // 불변객체를 만들기 위해서, 세터를 빼거나 접근제한자를 둬야 하는데 내 생각에는 setter가 좀 더 안전해 보인다. 안 만들어야 된다는 느낌을 주니까.
//public class Address {
//
////  장점 2. @Column(length = 10) 이런 rule들이 공통으로 관리된다는 장점도 있다.
//    private String city;
//    private String street;
//    private String zipcode;
//
//    protected Address() {}  // jpa spec에 맞춰주기 위해 기본생성자를 만든다. private이 허용이 안되서 protected로 선언.
//
//    public Address(String city, String street, String zipcode) {
//        this.city = city;
//        this.street = street;
//        this.zipcode = zipcode;
//    }
//
//    // 장점 1. 의미있는 메서드 생성 가능.
//    public String fullAddress() {
//        return getCity() + " " + getStreet() + " " + getZipcode();
//    }
////    public boolean isValid() {}
//
//    // 임베디드 타입의 경우 equals와 hashCode를 자주 사용할 일이 많으므로 오버라이드 하자.
//    // 왠만하면 use Getter 메서드 옵션 체크(프록시 이슈에서 안전)
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Address address = (Address) o;
//        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getZipcode(), address.getZipcode());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getCity(), getStreet(), getZipcode());
//    }
//}
