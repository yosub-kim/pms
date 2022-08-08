package kr.co.kmac.pms.common.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class PmsUserDetail implements UserDetails {

	private boolean enabled = true;
	private String name;
	private String description;
	private String password = "";
	private String email;
	private String position;
	private String ssn;
	private String loginId;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private String jobClass;
	private String userId;
	private String dept;
	private String companyPosition;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> roles = new HashSet<>();
		roles.add(new SimpleGrantedAuthority("USER"));
		return roles;
	}

	@Override
	public String getUsername() {
		return this.loginId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

}