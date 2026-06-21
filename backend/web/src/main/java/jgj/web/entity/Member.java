package jgj.web.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", sequenceName = "MEMBER_SEQ", allocationSize = 1)
    @Column(name = "member_seq")
    private Long memberSeq;

    private String name;

    private String city;

    private String street;

    private String zipcode;

    @Builder
    public Member(Long memberSeq, String name, String city, String street, String zipcode) {
        this.memberSeq = memberSeq;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
