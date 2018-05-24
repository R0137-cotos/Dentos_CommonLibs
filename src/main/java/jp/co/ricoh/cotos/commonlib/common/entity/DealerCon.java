package jp.co.ricoh.cotos.commonlib.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.common.master.KjbMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約情報の中で保持する販売店情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(DealerConListener.class)
@Data
@Table(name = "dealer_con")
public class DealerCon extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dealer_seq")
	@SequenceGenerator(name = "dealer_seq", sequenceName = "dealer_seq", allocationSize = 1)
	@ApiModelProperty(value = "販売店ID", required = true, position = 1)
	private long id;

	@ManyToOne(optional = false)
	@JsonIgnore
	@JoinColumn(name = "contract_id")
	private Contract contract;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mom_kjb_system_id")
	@ApiModelProperty(value = "企事部マスタ", required = false, position = 2)
	private KjbMaster kjbMaster;

	/**
	 * 販売店商流順
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "販売店商流順", required = true, position = 3, allowableValues = "range[0,255]")
	private String dealerFlowOrder;

	/**
	 * 販売店名
	 */
	@ApiModelProperty(value = "販売店名", required = false, position = 4, allowableValues = "range[0,255]")
	private String dealerName;

	/**
	 * 会社代表電話番号
	 */
	@ApiModelProperty(value = "会社代表電話番号", required = false, position = 5, allowableValues = "range[0,255]")
	private String orgPhoneNumber;

	/**
	 * 郵便番号
	 */
	@ApiModelProperty(value = "郵便番号", required = false, position = 6, allowableValues = "range[0,255]")
	private String postNumber;

	/**
	 * 住所
	 */
	@ApiModelProperty(value = "住所", required = false, position = 7, allowableValues = "range[0,255]")
	private String address;

	/**
	 * 担当者名
	 */
	@ApiModelProperty(value = "担当者名", required = false, position = 8, allowableValues = "range[0,255]")
	private String picName;

	/**
	 * 担当者部署名
	 */
	@ApiModelProperty(value = "担当者部署名", required = false, position = 9, allowableValues = "range[0,255]")
	private String picDeptName;

	/**
	 * 担当者電話番号
	 */
	@ApiModelProperty(value = "担当者電話番号", required = false, position = 10, allowableValues = "range[0,255]")
	private String picPhoneNumber;

	/**
	 * 担当者FAX番号
	 */
	@ApiModelProperty(value = "担当者FAX番号", required = false, position = 11, allowableValues = "range[0,255]")
	private String picFaxNumber;
}