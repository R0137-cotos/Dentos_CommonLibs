package jp.co.ricoh.cotos.commonlib.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ActionDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AuthDiv;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthLevel;
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
	private String origin;

	@Getter
	private String applicationId;

	@Getter
	private String jwt;

	@Getter
	private boolean isSuperUser;

	@Getter
	private Map<ActionDiv, Map<AuthDiv, AuthLevel>> momAuthorities;

	public CotosAuthenticationDetails(String momEmployeeId, String singleUserId, String origin, String applicationId, String jwt, boolean isSuperUser, Map<ActionDiv, Map<AuthDiv, AuthLevel>> momAuthorities) {
		super();
		this.momEmployeeId = momEmployeeId;
		this.singleUserId = singleUserId;
		this.origin = origin;
		this.applicationId = applicationId;
		this.jwt = jwt;
		this.isSuperUser = isSuperUser;
		this.momAuthorities = momAuthorities;
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