package Graduation.donkas.domain.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="EMAIL")
    private String email;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="NAME")
    private String name;

    @Column(name="TEL_NO")
    private String telNo;

    @Enumerated(EnumType.STRING)
    @Column(name="AUTHORITY")
    private Authority authority;



}
