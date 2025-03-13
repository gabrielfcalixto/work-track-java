package br.com.gfctech.project_manager.secury;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.gfctech.project_manager.secury.jwt.AuthEntryPointJwt;
import br.com.gfctech.project_manager.secury.jwt.AuthFilterToken;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private AuthFilterToken authFilterToken;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Permitir o acesso ao Swagger

				.requestMatchers("/auth/**").permitAll() // Permite acesso público aos endpoints de autenticação
                .requestMatchers("/auth/reset-password","/auth/generate-reset-code").permitAll() // Libera acesso público
                .requestMatchers("/user/addUser").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
				.requestMatchers("/user/**").authenticated() // Exige autenticação para outros endpoints de usuário
                .requestMatchers("/clients/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .requestMatchers("/dashboard/**").authenticated()
                .requestMatchers("/project/**").authenticated()

				.anyRequest().authenticated()); // Exige autenticação para qualquer outro endpoint

		http.addFilterBefore(authFilterToken, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
}
