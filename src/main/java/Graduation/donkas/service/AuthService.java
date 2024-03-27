package Graduation.donkas.service;

import Graduation.donkas.domain.member.Member;
import Graduation.donkas.domain.member.RefreshToken;
import Graduation.donkas.dto.LoginDto;
import Graduation.donkas.dto.MemberRequestDto;
import Graduation.donkas.dto.MemberResponseDto;
import Graduation.donkas.dto.TokenDto;
import Graduation.donkas.jwt.TokenProvider;
import Graduation.donkas.repository.MemberRepository;
import Graduation.donkas.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저(이메일)입니다");
        }
        Member member = memberRequestDto.toMember(passwordEncoder);

        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public TokenDto login(LoginDto loginDto) {
        try{
            Member member = memberRepository.findByEmail(loginDto.getEmail()).get();
            // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();
            // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
            //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
            // 4. RefreshToken 저장
            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(tokenDto.getRefreshToken())
                    .build();
            System.out.println("++"+member.getEmail());
            refreshTokenRepository.save(refreshToken);

            // 5. 토큰 발급
            return tokenDto;
        }catch (BadCredentialsException e){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }catch (InternalAuthenticationServiceException e){
            throw new RuntimeException("데이터베이스에 멤버가 존재하지 않습니다");
        }catch(NoSuchElementException e){
            throw new RuntimeException("데이터베이스에 멤버가 존재하지 않습니다");
        }

    }
}
