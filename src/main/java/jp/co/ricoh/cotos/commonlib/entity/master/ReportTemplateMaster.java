package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帳票テンプレート管理マスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "report_template_master")
public class ReportTemplateMaster extends EntityBaseMaster {

	/**
	 * 出力形式
	 */
	public enum OutputType {

		PDF("1"), Excel("2");

		private final String text;

		private OutputType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static OutputType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * サービスカテゴリ
	 */
	public enum ServiceCategory {

		見積("1"), 契約("2");

		private final String text;

		private ServiceCategory(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ServiceCategory fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * 対象種別
	 */
	public enum TargetType {

		新規("1"), プラン変更("2"), 情報変更("3"), 解約("4");

		private final String text;

		private TargetType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static TargetType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * テンプレートID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_template_master_seq")
	@SequenceGenerator(name = "report_template_master_seq", sequenceName = "report_template_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "テンプレートID", required = true, position = 1, allowableValues = "range[0,9999999999999999999999999999]")
	private long id;

	/**
	 * テンプレート名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "テンプレート名", required = false, position = 2, allowableValues = "range[0,255]")
	private String templateName;

	/**
	 * テンプレート区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "テンプレート区分", required = false, position = 3, allowableValues = "range[0,255]")
	private String templateType;

	/**
	 * 出力形式
	 */
	@ApiModelProperty(value = "出力形式", required = false, position = 4)
	private OutputType outputType;

	/**
	 * テンプレートパス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "テンプレート名", required = false, position = 5, allowableValues = "range[0,255]")
	private String templatePath;

	/**
	 * サービスカテゴリ
	 */
	@ApiModelProperty(value = "サービスカテゴリ", required = false, position = 6)
	private ServiceCategory serviceCategory;

	/**
	 * 対象種別
	 */
	@ApiModelProperty(value = "対象種別", required = false, position = 6)
	private TargetType targetType;

	/**
	 * 商流区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商流区分", required = false, position = 7, allowableValues = "range[0,255]")
	private String commercialFlowDiv;

	/**
	 * 商品マスタID
	 */
	@ApiModelProperty(value = "テンプレート名", required = false, position = 8, allowableValues = "range[0,9999999999999999999]")
	private Long productMasterId;

	/**
	 * 帳票ページ管理マスタ
	 */
	@OneToMany(mappedBy = "reportTemplateMaster")
	@ApiModelProperty(value = "帳票ページ管理マスタ", required = false, position = 9)
	private List<ReportPageMaster> reportPageMasterList;
}
