package jp.co.ricoh.cotos.commonlib.entity.master;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
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
	@ApiModelProperty(value = "状態", required = false, allowableValues = "NOUPDATE(\"1\"), ADD(\"2\"), DELETE(\"3\"), UPDATE(\"4\")", position = 5)
	private DetailStatus status;

	/**
	 * リコー品種コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "リコー品種コード", required = false, position = 6, allowableValues = "range[0,255]")
	private String ricohItemCode;
}
