package jp.co.ricoh.cotos.commonlib.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Configuration
@EnableWebSecurity
public class TestWebSecurityConfig {

	@Autowired
	TestVoter testVoter;

	@Autowired
	AccessLogOutputFilter accessLogOutputFilter;

	@Autowired
	MultipleReadEnableFilter multipleReadEnableFilter;
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/test/api/swagger-ui.html").and().debug(true);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

		http.addFilterAfter(accessLogOutputFilter, PreAuthenticationFilter.class);
		http.addFilterAfter(multipleReadEnableFilter, AccessLogOutputFilter.class);

		http.csrf(csrf -> csrf.disable())

				// 認証処理用のフィルターを追加
				.addFilter(preAuthenticatedProcessingFilter(authenticationManager)).exceptionHandling(handler -> handler.authenticationEntryPoint(new Http401AuthenticationEntryPoint("Bearer error=\"invalid_token\"")))
				// 成功・失敗処理のハンドラーを追加
				.formLogin(Customizer.withDefaults()).logout(logout -> logout.logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler()))
				// 認可処理用のインスタンスを追加
				.authorizeHttpRequests(httpRequests -> httpRequests.anyRequest().access(testVoter))
				// 認証情報を取得できなければ、401エラー
				.anonymous(anonymous -> anonymous.disable()).exceptionHandling(handler -> handler.authenticationEntryPoint(new Http401AuthenticationEntryPoint("Bearer error=\"invalid_token\"")));

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	// フィルター
	@Bean
	public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter(AuthenticationManager authenticationManager) throws Exception {
		PreAuthenticationFilter filter = new PreAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}

	@Bean
	public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService() {
		return new CotosUserDetailsService();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	// フィルター登録
	@Bean
	public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider(AuthenticationManagerBuilder auth) {
		PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
		provider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService());
		provider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
		auth.authenticationProvider(provider);
		return provider;
	}

}
