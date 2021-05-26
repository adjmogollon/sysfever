package com.sysfever.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.sysfever.app.auth.handler.LoginSuccessHandler;
import com.sysfever.app.models.service.JpaUserDatailsService;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginSuccessHandler successHandler;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JpaUserDatailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login/**","/css/**", "/js/**", "/images/**", "/cliente/**","/locale","/test/**","/webjars/**","/api/**","/dist/**","/plugins/**").permitAll()
				.anyRequest().authenticated().and().formLogin().successHandler(successHandler).loginPage("/login/login")
				.permitAll().and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/error_403");

	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

		builder.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
		
		/*
		 *
		 * builder.jdbcAuthentication() .dataSource(dataSource)
		 * .passwordEncoder(passwordEncoder)
		 * .usersByUsernameQuery("select user username, password, enable from users where username=?"
		 * )
		 * .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id = u.id) where u.username=?"
		 * );
		 */

		/*
		 * PasswordEncoder encoder =this.passwordEncoder; //PasswordEncoder encoder =
		 * PasswordEncoderFactories.createDelegatingPasswordEncoder();
		 * 
		 * UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		 * 
		 * builder.inMemoryAuthentication()
		 * .withUser(users.username("admin").password("admin").roles("ADMIN","USER"))
		 * .withUser(users.username("anibal").password("anibal").roles("USER"));
		 */

	}

}
