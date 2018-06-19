package jp.co.ricoh.cotos.commonlib.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

/**
 * COTOS認証情報を保持するDTOクラス
 */
public class CotosAuthenticationDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Getter
	private String momEmployeeId;

	@Getter
	private String singleUserId;

	@Getter
	private String applicationKey;

	public CotosAuthenticationDetails(String momEmployeeId, String singleUserId, String applicationKey) {
		super();
		this.momEmployeeId = momEmployeeId;
		this.singleUserId = singleUserId;
		this.applicationKey = applicationKey;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
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
