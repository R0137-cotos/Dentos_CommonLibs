package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.KjbMaster;
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
	@ApiModelProperty(value = "ID", required = true, position = 1)
	private long id;

	/**
	 * MoM企事部
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "mom_kjb_system_id", referencedColumnName = "mclMomRelId")
	@ApiModelProperty(value = "MoM企事部", required = false, position = 2)
	private KjbMaster kjbMaster;

	/**
	 * 販売店名
	 */
	@ApiModelProperty(value = "販売店名", required = false, position = 3, allowableValues = "range[0,255]")
	private String dealerName;

	/**
	 * 郵便番号
	 */
	@ApiModelProperty(value = "郵便番号", required = false, position = 4, allowableValues = "range[0,255]")
	private String postNumber;

	/**
	 * 住所
	 */
	@ApiModelProperty(value = "住所", required = false, position = 5, allowableValues = "range[0,1000]")
	private String address;

	/**
	 * 会社代表電話番号
	 */
	@ApiModelProperty(value = "会社代表電話番号", required = false, position = 6, allowableValues = "range[0,255]")
	private String orgPhoneNumber;

	/**
	 * 担当者名
	 */
	@ApiModelProperty(value = "担当者名", required = false, position = 7, allowableValues = "range[0,255]")
	private String picName;

	/**
	 * 担当者部署名
	 */
	@ApiModelProperty(value = "担当者部署名", required = false, position = 8, allowableValues = "range[0,255]")
	private String picDeptName;

	/**
	 * 担当者電話番号
	 */
	@ApiModelProperty(value = "担当者電話番号", required = false, position = 9, allowableValues = "range[0,255]")
	private String picPhoneNumber;

	/**
	 * 担当者FAX番号
	 */
	@ApiModelProperty(value = "担当者FAX番号", required = false, position = 10, allowableValues = "range[0,255]")
	private String picFaxNumber;

	/**
	 * 契約
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@ApiModelProperty(value = "契約", required = true, position = 11)
	private Contract contract;

	/**
	 * 販売店商流順
	 */
	@ApiModelProperty(value = "販売店商流順", required = true, position = 12, allowableValues = "range[0,999]")
	private int dealerFlowOrder;
}