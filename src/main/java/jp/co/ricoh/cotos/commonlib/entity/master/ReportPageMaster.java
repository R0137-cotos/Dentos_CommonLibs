package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帳票ページ管理マスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "report_page_master")
public class ReportPageMaster extends EntityBaseMaster {

	/**
	 * 状態
	 */
	public enum Status {

		NOUPDATE("1"), ADD("2"), DELETE("3");

		private final String text;

		private Status(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static Status fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * ページID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_page_master_seq")
	@SequenceGenerator(name = "report_page_master_seq", sequenceName = "report_page_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "商品マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999999999999]")
	private long id;

	/**
	 * 帳票テンプレート管理マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "template_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "帳票テンプレート管理マスタ", required = true, position = 2)
	private ReportTemplateMaster reportTemplateMaster;

	/**
	 * ページ名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "ページ名", required = false, position = 3, allowableValues = "range[0,255]")
	private String pageName;

	/**
	 * 常時出力フラグ
	 */
	@Max(9)
	@Min(0)
	@ApiModelProperty(value = "常時出力フラグ", required = false, position = 4, allowableValues = "range[0,9]")
	private Integer alwaysOutputFlg;

	/**
	 * 状態
	 */
	@ApiModelProperty(value = "状態", required = false, position = 5)
	private Status status;

	/**
	 * リコー品種コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "リコー品種コード", required = false, position = 6, allowableValues = "range[0,255]")
	private String ricohItemCode;
}
