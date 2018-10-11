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
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * URL毎の権限種別を表したマスター
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "url_auth_master")
public class UrlAuthMaster extends EntityBaseMaster {

	public enum Domain {
		estimation, contract, arrangement, communication, master;
	}

	public enum ParameterType {
		none("0"), path("1"), query("2"), json("3");

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

	public enum ActionDiv {
		なし("00"), 照会("01"), 登録("02"), 更新("03"), 削除("04"), 印刷("05"), ダウンロード("06"), 集計("07");

		private final String text;

		private ActionDiv(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static ActionDiv fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum AuthDiv {
		なし("0"), 見積_契約_手配("2200"), 請求_計上_本部("2210"), システム管理("2220");

		private final String text;

		private AuthDiv(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static AuthDiv fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum AccessType {
		なし("0"), 参照("1"), 編集("2"), 承認("3");

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
		 * ドメイン
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
	private AccessType accessType;

	/**
	 * 処理概要
	 */
	@Column(nullable = true)
	private String description;
}
