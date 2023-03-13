package com.api.websocketapi.config;

import com.api.websocketapi.config.component.CustomAuthenticationFailureHandler;
import com.api.websocketapi.config.component.CustomAuthenticationSuccessHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * @author sion
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    private CustomAuthenticationSuccessHandler successHandler;

    @Resource
    private CustomAuthenticationFailureHandler failureHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //权限认证管理
        http.authorizeHttpRequests(authorize -> {
            try {
                authorize
                        // 放行登录接口
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/register").permitAll()
                        // 放行资源目录
                        .requestMatchers("/static/**", "/resources/**").permitAll()
                        // 其余的都需要权限校验
                        .anyRequest().authenticated()
                        // 防跨站请求伪造
                        .and().csrf(AbstractHttpConfigurer::disable);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        //表单登录
        http.formLogin(
                login -> login.successHandler(successHandler)
                        //自定义用户表单
//                        .loginPage("/login.html")
                        .defaultSuccessUrl("/", false)
                        .failureUrl("/error.html")
                        .failureHandler(failureHandler)
                )
                .logout(
                        logout -> logout.logoutSuccessUrl("/index")
                                .addLogoutHandler((request, response, authentication) -> {
                                    System.out.println(request.getMethod());
                                    System.out.println("===========LogoutHandler============");
                                })
                )
        ;
        return http.build();
    }


}
