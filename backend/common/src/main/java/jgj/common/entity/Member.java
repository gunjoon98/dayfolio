package jgj.common.entity;

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

    @Column(name = "id")
    private String loginId;

    private String password;

    private String nickname;

    @Builder
    public Member(Long memberSeq, String loginId, String password, String nickname) {
        this.memberSeq = memberSeq;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
    }
}
