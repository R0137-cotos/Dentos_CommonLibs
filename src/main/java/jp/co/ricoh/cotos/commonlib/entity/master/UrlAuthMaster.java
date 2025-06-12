package jp.co.ricoh.cotos.commonlib.entity.master;

import java.io.Serializable;
import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.converter.HttpMethodConverter;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * URL権限マスタ
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "url_auth_master")
public class UrlAuthMaster extends EntityBaseMaster {

	public enum Domain {
		estimation, contract, arrangement, communication, master, arrangementDelegation;
	}

	public enum ParameterType {
		none("0"), path("1"), query("2"), json("3");

		private final String text;

		private ParameterType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
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
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ActionDiv fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum AuthDiv {
		なし("0"), 見積_契約_手配("2200"), 請求_計上_本部("2210"), システム管理("2220"), 見積_契約_業務用検索("2230");

		private final String text;

		private AuthDiv(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
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
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
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
		@ApiModelProperty(value = "URLパターン", required = true, position = 1)
		private String urlPattern;

		/**
		 * HTTPメソッド
		 */
		@Convert(converter = HttpMethodConverter.class)
		@Column(nullable = false)
		@ApiModelProperty(value = "HTTPメソッド", required = true, position = 2)
		@JsonSerialize(using = ToStringSerializer.class)
		private HttpMethod method;

		/**
		 * ドメイン
		 */
		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		@ApiModelProperty(value = "システムドメイン", required = true, position = 3)
		private Domain domain;
	}

	@EmbeddedId
	private Id id;

	/**
	 * 認可処理実施要否
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "認可処理実施要否", required = true, position = 4, allowableValues = "range[0,9]")
	private int requireAuthorize;

	/**
	 * 外部参照ドメイン
	 */
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "外部参照ドメイン", required = false, position = 5)
	private Domain externalRefDomain;

	/**
	 * DBデータ存在有無
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "DBデータ存在有無", required = true, position = 6, allowableValues = "range[0,9]")
	private int existsDb;

	/**
	 * パラメータータイプ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "パラメータータイプ", required = true, allowableValues = "none(\"0\"), path(\"1\"), query(\"2\"), json(\"3\")", example = "1", position = 7)
	private ParameterType paramType;

	/**
	 * パラメーターキー
	 */
	@Column(nullable = true)
	@ApiModelProperty(value = "パラメーターキー", required = false, position = 8, allowableValues = "range[0,255]")
	private String paramKey;

	/**
	 * アクション区分
	 */
	@Column(nullable = true)
	@ApiModelProperty(value = "アクション区分", required = true, allowableValues = "なし(\"00\"), 照会(\"01\"), 登録(\"02\"), 更新(\"03\"), 削除(\"04\"), 印刷(\"05\"), ダウンロード(\"06\"), 集計(\"07\")", example = "01", position = 9)
	private ActionDiv actionDiv;

	/**
	 * 権限区分
	 */
	@Column(nullable = true)
	@ApiModelProperty(value = "権限区分", required = true, allowableValues = "なし(\"0\"), 見積_契約_手配(\"2200\"), 請求_計上_本部(\"2210\"), システム管理(\"2220\")", example = "0", position = 10)
	private AuthDiv authDiv;

	/**
	 * 参照種別
	 */
	@Column(nullable = true)
	@ApiModelProperty(value = "参照種別", required = true, allowableValues = "なし(\"0\"), 参照(\"1\"), 編集(\"2\"), 承認(\"3\")", example = "1", position = 11)
	private AccessType accessType;

	/**
	 * 処理概要
	 */
	@Column(nullable = true)
	@ApiModelProperty(value = "処理概要", required = true, position = 12, allowableValues = "range[0,255]")
	private String description;
}
