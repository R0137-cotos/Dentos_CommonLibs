package jp.co.ricoh.cotos.commonlib.entity.contract.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文販売店情報
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "order_distributor_info")
public class OrderDistributorInfo extends EntityBase {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_distributor_info_seq")
	@SequenceGenerator(name = "order_distributor_info_seq", sequenceName = "order_distributor_info_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1)
	private long id;

	/**
	 * 注文基本情報ID
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "order_basic_info_id", referencedColumnName = "id")
	@JsonIgnore
	private OrderBasicInfo orderBasicInfo;

	/**
	 * 販売店コード
	 */
	@Column
	@ApiModelProperty(value = "販売店コード", required = false, position = 2, allowableValues = "range[0,]")
	private String distributorCd;

	/**
	 * 販売店名
	 */
	@Column
	@ApiModelProperty(value = "販売店名", required = false, position = 3, allowableValues = "range[0,]")
	private String distributorName;

	/**
	 * OE届け先コード
	 */
	@Column
	@ApiModelProperty(value = "OE届け先コード", required = false, position = 4, allowableValues = "range[0,]")
	private String oeDeliveryCd;

	/**
	 * 販売店区分
	 */
	@Column
	@ApiModelProperty(value = "販売店区分", required = false, position = 5, allowableValues = "range[0,]")
	private String distributorCustomerType;

	/**
	 * 販売店担当営業
	 */
	@Column
	@ApiModelProperty(value = "販売店担当営業", required = false, position = 6, allowableValues = "range[0,]")
	private String distributorEmployeeName;

	/**
	 * 販売店担当営業メールアドレス
	 */
	@Column
	@ApiModelProperty(value = "販売店担当営業メールアドレス", required = false, position = 7, allowableValues = "range[0,]")
	private String distributorEmployeeMailAddress;

	/**
	 * Rings得意先コード
	 */
	@Column
	@ApiModelProperty(value = "Rings得意先コード", required = false, position = 8, allowableValues = "range[0,]")
	private String ringsCustomerCd;

	/**
	 * 販売店郵便番号
	 */
	@Column
	@ApiModelProperty(value = "販売店郵便番号", required = false, position = 9, allowableValues = "range[0,]")
	private String distributorPostNumber;

	/**
	 * 販売店住所
	 */
	@Column
	@ApiModelProperty(value = "販売店住所", required = false, position = 10, allowableValues = "range[0,]")
	private String distributorAddress;

}
