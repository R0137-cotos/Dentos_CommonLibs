package jp.co.ricoh.cotos.commonlib.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Configuration
@EnableWebSecurity
public class TestWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	TestVoter testVoter;

	@Autowired
	AccessLogOutputFilter accessLogOutputFilter;

	@Autowired
	MultipleReadEnableFilter multipleReadEnableFilter;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/test/api/swagger-ui.html");
		web.debug(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.addFilterAfter(accessLogOutputFilter, PreAuthenticationFilter.class);
		http.addFilterAfter(multipleReadEnableFilter, AccessLogOutputFilter.class);

		http.csrf().disable()

				// 認証処理用のフィルターを追加
				.addFilter(preAuthenticatedProcessingFilter()).exceptionHandling().authenticationEntryPoint(new Http401AuthenticationEntryPoint("Bearer error=\"invalid_token\""))
				// 成功・失敗処理のハンドラーを追加
				.and().formLogin().and().logout().logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler())
				// 認可処理用のインスタンスを追加
				.and().authorizeRequests().anyRequest().authenticated().accessDecisionManager(createAccessDecisionManager())
				// 認証情報を取得できなければ、401エラー
				.and().anonymous().disable().exceptionHandling().authenticationEntryPoint(new Http401AuthenticationEntryPoint("Bearer error=\"invalid_token\""));

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	// フィルター
	@Bean
	public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter() throws Exception {
		PreAuthenticationFilter filter = new PreAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}

	@Bean
	public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService() {
		return new CotosUserDetailsService();
	}

	// フィルター登録
	@Bean
	public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
		PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
		provider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService());
		provider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(preAuthenticatedAuthenticationProvider());
	}

	private AccessDecisionManager createAccessDecisionManager() {
		return new AffirmativeBased(Arrays.asList(testVoter));
	}
}
