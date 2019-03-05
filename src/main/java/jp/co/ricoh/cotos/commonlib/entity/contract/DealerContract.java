package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.common.DealerAbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約情報の中で保持する販売店情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "dealer_contract")
public class DealerContract extends DealerAbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dealer_contract_seq")
	@SequenceGenerator(name = "dealer_contract_seq", sequenceName = "dealer_contract_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 契約
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 2)
	private Contract contract;

	/**
	 * 販売店コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店コード", required = false, position = 3, allowableValues = "range[0,255]")
	private String distributorCd;

	/**
	 * OE届け先コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "OE届け先コード", required = false, position = 4, allowableValues = "range[0,255]")
	private String oeDeliveryCd;

	/**
	 * 担当営業メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当営業メールアドレス", required = false, position = 5, allowableValues = "range[0,255]")
	private String distributorEmployeeMailAddress;

	/**
	 * Rings得意先コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "Rings得意先コード", required = false, position = 6, allowableValues = "range[0,255]")
	private String ringsCustomerCd;

	/**
	 * 取引先コード（手数料用）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "取引先コード（手数料用）", required = false, position = 7, allowableValues = "range[0,255]")
	private String distributorRtcCd;

	/**
	 * MoM会社ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM会社ID", required = false, position = 8, allowableValues = "range[0,255]")
	private String distributorMomCmpId;

	/**
	 * MoM販売店識別コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM販売店識別コード", required = false, position = 9, allowableValues = "range[0,255]")
	private String distributorMomShikiCd;

	/**
	 * MoM組織ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM組織ID", required = false, position = 10, allowableValues = "range[0,255]")
	private String distributorMomSoshikiId;

	/**
	 * MoMデポコード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoMデポコード", required = false, position = 11, allowableValues = "range[0,255]")
	private String distributorMomDepoCd;

	/**
	 * MoM設置届先サイトID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM設置届先サイトID", required = false, position = 12, allowableValues = "range[0,255]")
	private String orbSendSiteId;

}