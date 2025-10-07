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
 * 注文担当支社情報
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "order_branch_customer_info")
public class OrderBranchCustomerInfo extends EntityBase {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_branch_customer_info_seq")
	@SequenceGenerator(name = "order_branch_customer_info_seq", sequenceName = "order_branch_customer_info_seq", allocationSize = 1)
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
	 * 支社コード
	 */
	@Column
	@ApiModelProperty(value = "支社コード", required = false, position = 2, allowableValues = "range[0,]")
	private String branchCustomerCd;

	/**
	 * 支社名
	 */
	@Column
	@ApiModelProperty(value = "支社名", required = false, position = 3, allowableValues = "range[0,]")
	private String branchCustomerName;

	/**
	 * 課所コード
	 */
	@Column
	@ApiModelProperty(value = "課所コード", required = false, position = 4, allowableValues = "range[0,]")
	private String officeCd;

	/**
	 * 課所名
	 */
	@Column
	@ApiModelProperty(value = "課所名", required = false, position = 5, allowableValues = "range[0,]")
	private String officeName;

	/**
	 * 営業コード
	 */
	@Column
	@ApiModelProperty(value = "営業コード", required = false, position = 6, allowableValues = "range[0,]")
	private String employeeCd;

	/**
	 * 営業名
	 */
	@Column
	@ApiModelProperty(value = "営業名", required = false, position = 7, allowableValues = "range[0,]")
	private String employeeName;

	/**
	 * 担当営業電話番号
	 */
	@Column
	@ApiModelProperty(value = "担当営業電話番号", required = false, position = 8, allowableValues = "range[0,]")
	private String employeePhoneNumber;

	/**
	 * 担当営業メールアドレス
	 */
	@Column
	@ApiModelProperty(value = "担当営業メールアドレス", required = false, position = 9, allowableValues = "range[0,]")
	private String employeeMailAddress;

}
