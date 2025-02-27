package br.com.gfctech.project_manager.service;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.gfctech.project_manager.entity.UserEntity;

public class UserDetailsImpl implements UserDetails{

	private Long id;
	
	private String name;
	
	private String login;
	
	private String email;
	
	private String password;
	
	
	public UserDetailsImpl(Long id, String name, String login, String password, String email,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.name = name;
		this.login = login;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(UserEntity user) {
		
		return new UserDetailsImpl(
				user.getId(), 
				user.getName(), 
				user.getLogin(),
				user.getPassword(),
				user.getEmail(), 
				new ArrayList<>());
	}
	
	private Collection<? extends GrantedAuthority> authorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}