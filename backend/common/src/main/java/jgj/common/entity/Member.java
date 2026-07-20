package jgj.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member")
    @SequenceGenerator(name = "seq_member", sequenceName = "SEQ_MEMBER", allocationSize = 1)
    @Column(name = "seq_member")
    private Long seqMember;

    @Column(name = "id")
    private String loginId;

    private String password;

    private String nickname;

    @Builder
    public Member(String loginId, String password, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
    }
}
