package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 汎用マスタ明細
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "common_master_detail")
public class CommonMasterDetail extends EntityBaseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_master_detail_seq")
	@SequenceGenerator(name = "common_master_detail_seq", sequenceName = "common_master_detail_seq", allocationSize = 1)
	@ApiModelProperty(value = "汎用マスタ明細ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 汎用マスタ
	 */
	@ManyToOne
	@JoinColumn(name = "common_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "汎用マスタ", required = true, position = 2)
	private CommonMaster commonMaster;

	/**
	 * コード値
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "コード値", required = true, position = 3, allowableValues = "range[0,255]")
	private String codeValue;

	/**
	 * コード表示値
	 */
	@ApiModelProperty(value = "コード表示値", required = false, position = 4, allowableValues = "range[0,255]")
	private String displayValue;

	/**
	 * コード内容値
	 */
	@ApiModelProperty(value = "コード内容値", required = false, position = 5, allowableValues = "range[0,255]")
	private String description;

	/**
	 * 設定値1
	 */
	@Column(name = "DATA_AREA_1")
	@ApiModelProperty(value = "設定値1", required = false, position = 6, allowableValues = "range[0,255]")
	private String dataArea1;

	/**
	 * 設定値2
	 */
	@Column(name = "DATA_AREA_2")
	@ApiModelProperty(value = "設定値2", required = false, position = 7, allowableValues = "range[0,255]")
	private String dataArea2;

	/**
	 * 設定値3
	 */
	@Column(name = "DATA_AREA_3")
	@ApiModelProperty(value = "設定値3", required = false, position = 8, allowableValues = "range[0,255]")
	private String dataArea3;

	/**
	 * 設定値4
	 */
	@Column(name = "DATA_AREA_4")
	@ApiModelProperty(value = "設定値4", required = false, position = 9, allowableValues = "range[0,255]")
	private String dataArea4;

	/**
	 * 設定値5
	 */
	@Column(name = "DATA_AREA_5")
	@ApiModelProperty(value = "設定値5", required = false, position = 10, allowableValues = "range[0,255]")
	private String dataArea5;

	/**
	 * 有効期間From
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "有効期間From", required = false, position = 11)
	private Date availablePeriodFrom;

	/**
	 * 有効期間To
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "有効期間To", required = false, position = 12)
	private Date availablePeriodTo;

	/**
	 * 表示順
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "表示順", required = true, position = 13, allowableValues = "range[0,999]")
	private Integer displayOrder;

	/**
	 * 削除フラグ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "削除フラグ", required = true, position = 14, allowableValues = "range[0,1]")
	private String deleteFlg;
}
