package Graduation.donkas.dto.MemberDto;

import Graduation.donkas.domain.member.Authority;
import Graduation.donkas.domain.member.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String telNo;
    @NotBlank(message = "신분 구분은 필수 입력 값입니다.")
    private Authority authority;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .telNo(telNo)
                .authority(authority)
                .build();
    }
}
