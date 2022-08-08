package kr.co.kmac.pms.common.security.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import kr.co.kmac.pms.common.security.service.SecurityService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	final DataSource dataSource;
	final SecurityService securityService;

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.headers().frameOptions().disable()
				//.addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", "ALLOW-FROM " + /* 허용할 URL입력 */))
			.and()
				.csrf().disable()
			.authorizeRequests() // 접근에 대한 인증 설정
				.antMatchers("/login_", "/signup", "/afterLogout").permitAll() // 누구나 접근 허용
				.antMatchers("/").hasRole("USER") // USER, ADMIN만 접근 가능
				.antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
				.anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
			.and()
				.formLogin() // 로그인에 관한 설정
					.loginPage("/login_") // 로그인 페이지 링크
					.loginProcessingUrl("/doLogin")
					.defaultSuccessUrl("/afterLogin") // 로그인 성공 후 리다이렉트 주소
			.and()
				.logout() // 로그아웃
					.logoutUrl("/logout")
					.logoutSuccessUrl("/afterLogout")// 로그아웃 성공시 리다이렉트 주소
					.invalidateHttpSession(true) // 세션 날리기
			.and()
				.rememberMe()
				.rememberMeParameter("remember-me")
				.tokenValiditySeconds(60* 60 * 24 * 14)
				.userDetailsService(securityService)

		;

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(securityService).passwordEncoder(passwordEncoder());
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new CustomPasswordEncoder();
	}

}
