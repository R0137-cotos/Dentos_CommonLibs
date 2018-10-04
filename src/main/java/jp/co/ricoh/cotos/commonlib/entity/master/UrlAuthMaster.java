package jp.co.ricoh.cotos.commonlib.entity.master;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.http.HttpMethod;

import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.ActionDiv;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthDiv;
import lombok.Data;

/**
 * URL毎の権限種別を表したマスター
 */

@Entity
@Data
@Table(name = "disp_url_auth_master")
public class UrlAuthMaster {

	public enum Domain {
		estimation, contract, arrangement, communication;
	}

	public enum ParameterType {
		path, query, json;
	}

	public enum AccessType {
		参照, 編集, 承認;
	}

	@Embeddable
	@Data
	public static class Id implements Serializable {

		/**
		 * シリアルバージョンID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * URLパターン
		 */
		@Column(nullable = false)
		private String urlPattern;

		/**
		 * HTTPメソッド
		 */
		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		private HttpMethod method;

		/**
		 * アプリケーション
		 */
		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		private Domain domain;
	}

	@EmbeddedId
	private Id id;

	/**
	 * 認可処理実施要否
	 */
	@Column(nullable = false)
	private boolean requireAuthorize;

	/**
	 * 外部参照ドメイン
	 */
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private Domain externalRefDomain;

	/**
	 * DBデータ存在有無
	 */
	@Column(nullable = false)
	private boolean existsDb;

	/**
	 * パラメータータイプ
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ParameterType paramType;

	/**
	 * パラメーターキー
	 */
	@Column(nullable = true)
	private String paramKey;

	/**
	 * アクション区分
	 */
	@Column(nullable = true)
	private ActionDiv actionDiv;

	/**
	 * 権限区分
	 */
	@Column(nullable = true)
	private AuthDiv authDiv;

	/**
	 * 参照種別
	 */
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private AccessType accessType;

	/**
	 * 処理概要
	 */
	@Column(nullable = true)
	private String description;
}
