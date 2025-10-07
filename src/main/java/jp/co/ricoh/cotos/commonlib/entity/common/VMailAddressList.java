package jp.co.ricoh.cotos.commonlib.entity.common;

import java.util.Arrays;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import lombok.Data;

/**
 * 添付ファイル情報を表すEntity
 */
@Entity
@Data
@Table(name = "v_mail_address_list")
public class VMailAddressList {

	public enum TableType {

		担当SA("1"), 担当編集者("2"), 担当CE("3"), 販売店("4"), 顧客("5"), 担当作業者("6");

		private final String text;

		private TableType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static TableType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}

	}

	@Id
	@ApiModelProperty(value = "トランザクションID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;
	/**
	 * ドメイン区分
	 */
	@ApiModelProperty(value = "ドメイン区分", required = true, allowableValues = "見積(\"1\"), 契約(\"2\"), 手配(\"3\")", example = "1", position = 2)
	private ServiceCategory domainType;

	/**
	 * テーブル区分
	 */
	@ApiModelProperty(value = "テーブル区分", required = true, position = 3, allowableValues = "担当SA(\"1\"), 担当編集者(\"2\"), 担当CE(\"3\"), 販売店(\"4\"), 顧客(\"5\"), 担当作業者(\"6\")", example = "1")
	private TableType tableType;

	/**
	 * トランザクションID
	 */
	@ApiModelProperty(value = "トランザクションID", required = true, position = 4, allowableValues = "range[0,9999999999999999999]")
	private long transactionId;

	/**
	 * メールアドレス
	 */
	@ApiModelProperty(value = "メールアドレス", required = false, position = 5, allowableValues = "range[0,255]")
	private String mailAddress;
}
