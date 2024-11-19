package com.samsung.DemoTraining.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.samsung.DemoTraining.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	UserService userService;

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		WebAuthenticationProvider authProvider = new WebAuthenticationProvider(userDetailsService());
		authenticationManagerBuilder.authenticationProvider(authProvider);
		return authenticationManagerBuilder.build();
	}

	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder().encode("user123"))
				.roles("USER")
				.build();
		UserDetails admin = User.builder()
				.username("nn.tien")
				.password(passwordEncoder().encode("123456"))
				.roles("USER", "ADMIN").build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeRequests(request -> request.requestMatchers("/login").permitAll()
						.requestMatchers("/add-user", "/mod-user", "/del-user").hasAuthority("ROLE_ADMIN")
						.requestMatchers("/**").authenticated())
				.formLogin(form -> form.loginPage("/login")
						.defaultSuccessUrl("/?continue")
						.failureUrl("/login?error=true").permitAll())
				.logout(config -> config.logoutUrl("/logout").logoutSuccessUrl("/login?logout=true")).build();
	}
}
