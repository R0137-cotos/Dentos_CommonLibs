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

import lombok.Data;

/**
 * URL毎の権限種別を表したマスター
 */

@Entity
@Data
@Table(name = "url_auth_master")
public class UrlAuthMaster {

	public enum Domain {
		estimation, contract, arrangement, communication;
	}

	public enum ParameterType {
		path, query, json;
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
