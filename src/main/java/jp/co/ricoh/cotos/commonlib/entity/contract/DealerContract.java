package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DealerFlowOrder;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約情報の中で保持する販売店情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "dealer_contract")
public class DealerContract extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dealer_contract_seq")
	@SequenceGenerator(name = "dealer_contract_seq", sequenceName = "dealer_contract_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * MoM企事部
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "mom_kjb_system_id", referencedColumnName = "mclMomRelId")
	@ApiModelProperty(value = "MoM企事部", required = false, position = 2)
	private VKjbMaster vKjbMaster;

	/**
	 * 販売店名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店名", required = false, position = 3, allowableValues = "range[0,255]")
	private String dealerName;

	/**
	 * 郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "郵便番号", required = false, position = 4, allowableValues = "range[0,255]")
	private String postNumber;

	/**
	 * 住所
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "住所", required = false, position = 5, allowableValues = "range[0,1000]")
	private String address;

	/**
	 * 会社代表電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "会社代表電話番号", required = false, position = 6, allowableValues = "range[0,255]")
	private String orgPhoneNumber;

	/**
	 * 担当者名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当者名", required = false, position = 7, allowableValues = "range[0,255]")
	private String picName;

	/**
	 * 担当者部署名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当者部署名", required = false, position = 8, allowableValues = "range[0,255]")
	private String picDeptName;

	/**
	 * 担当者電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当者電話番号", required = false, position = 9, allowableValues = "range[0,255]")
	private String picPhoneNumber;

	/**
	 * 担当者FAX番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当者FAX番号", required = false, position = 10, allowableValues = "range[0,255]")
	private String picFaxNumber;

	/**
	 * 契約
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 11)
	private Contract contract;

	/**
	 * 販売店商流順
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "販売店商流順", required = true, position = 12)
	private DealerFlowOrder dealerFlowOrder;
}