package Graduation.donkas.dto.MemberDto;

import Graduation.donkas.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;

    public static MemberResponseDto of(Member member){
        return new MemberResponseDto(member.getEmail());
    }
}
