package Graduation.donkas.config;

import Graduation.donkas.jwt.JwtAccessDeniedHandler;
import Graduation.donkas.jwt.JwtAuthenticationEntryPoint;
import Graduation.donkas.jwt.JwtSecurityConfig;
import Graduation.donkas.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig{
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("*")); // ️ 허용할 origin
            config.setAllowCredentials(false);
            return config;
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        http.csrf(AbstractHttpConfigurer::disable)

                .cors(corsConfigurer->corsConfigurer.configurationSource(corsConfigurationSource()))

                // exception handling 할 때 만든 클래스를 추가
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                .headers((headers)->
                        headers.contentTypeOptions(contentTypeOptionsConfig ->
                                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)))


                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests((authorizeRequests)->
                        authorizeRequests
                                .requestMatchers("/login","/**").permitAll() // 인증없어도 되는 페이지
                                .anyRequest().authenticated() // 그 외 인증 없이 접근X
                ).with(new JwtSecurityConfig(tokenProvider), customizer -> {});

        return http.build();
    }

}
