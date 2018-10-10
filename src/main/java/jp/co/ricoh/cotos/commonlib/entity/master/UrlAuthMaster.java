package jp.co.ricoh.cotos.commonlib.entity.master;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.http.HttpMethod;

import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.ActionDiv;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthDiv;
import lombok.Data;

/**
 * URL毎の権限種別を表したマスター
 */

@Entity
@Data
@Table(name = "url_auth_master")
public class UrlAuthMaster extends EntityBaseMaster {

	public enum Domain {
		estimation, contract, arrangement, communication, master;
	}

	public enum ServiceCategory {

		見積("1"), 契約("2"), 手配("3"), コミュニケーション("4"), マスタ("5");

		private final String text;

		private ServiceCategory(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static ServiceCategory fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum ParameterType {
		path("1"), query("2"), json("3");

		private final String text;

		private ParameterType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static ParameterType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum AccessType {
		参照("1"), 編集("2"), 承認("3");

		private final String text;

		private AccessType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static AccessType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
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
		 * サービスカテゴリ
		 */
		private ServiceCategory serviceCategory;
	}

	@EmbeddedId
	private Id id;

	/**
	 * 認可処理実施要否
	 */
	@Column(nullable = false)
	private int requireAuthorize;

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
	private int existsDb;

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
