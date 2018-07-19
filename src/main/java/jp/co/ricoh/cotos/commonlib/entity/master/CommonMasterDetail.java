package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 汎用マスタ明細
 */
@Entity
@Data
@Table(name = "common_master_detail")
public class CommonMasterDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_master_detail_seq")
	@SequenceGenerator(name = "common_master_detail_seq", sequenceName = "common_master_detail_seq", allocationSize = 1)
	@ApiModelProperty(value = "汎用マスタ明細ID", required = true, position = 1)
	private long id;

	/**
	 * 汎用マスタ
	 */
	@ManyToOne
	@JoinColumn(name = "common_master_id")
	@JsonIgnore
	@ApiModelProperty(value = "汎用マスタ", required = false, position = 2)
	private CommonMaster commonMaster;

	/**
	 * コード値
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "コード値", required = true, position = 3, allowableValues = "range[0,255]")
	private String cdVal;

	/**
	 * コード内容値
	 */
	@ApiModelProperty(value = "コード内容値", required = false, position = 4, allowableValues = "range[0,255]")
	private String decdVal;

	/**
	 * 設定値1
	 */
	@ApiModelProperty(value = "設定値1", required = false, position = 5, allowableValues = "range[0,255]")
	private String dataArea1;

	/**
	 * 設定値2
	 */
	@ApiModelProperty(value = "設定値2", required = false, position = 6, allowableValues = "range[0,255]")
	private String dataArea2;

	/**
	 * 設定値3
	 */
	@ApiModelProperty(value = "設定値3", required = false, position = 7, allowableValues = "range[0,255]")
	private String dataArea3;

	/**
	 * 設定値4
	 */
	@ApiModelProperty(value = "設定値4", required = false, position = 8, allowableValues = "range[0,255]")
	private String dataArea4;

	/**
	 * 設定値5
	 */
	@ApiModelProperty(value = "設定値5", required = false, position = 9, allowableValues = "range[0,255]")
	private String dataArea5;

	/**
	 * 有効期間From
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "有効期間From", required = false, position = 10)
	private Date availablePeriodFrom;

	/**
	 * 有効期間To
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "有効期間To", required = false, position = 11)
	private Date availablePeriodTo;

	/**
	 * ソート順
	 */
	@ApiModelProperty(value = "ソート順", required = false, position = 12)
	private Integer sortNumber;

	/**
	 * 削除フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "削除フラグ", required = false, position = 13)
	private boolean deleteFlg;
}
