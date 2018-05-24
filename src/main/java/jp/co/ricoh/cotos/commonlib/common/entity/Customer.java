package jp.co.ricoh.cotos.commonlib.common.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.common.converter.KjbSetDivConverter;
import jp.co.ricoh.cotos.commonlib.common.master.KjbMaster;
import jp.co.ricoh.cotos.commonlib.common.master.KjbMaster.KjbSetDiv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 見積情報の中で保持する顧客情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(CustomerListener.class)
@Data
@Table(name = "customer")
public class Customer extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
	@SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 1)
	@ApiModelProperty(value = "顧客ID", required = true, position = 1)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mom_kjb_system_id")
	@ApiModelProperty(value = "企事部マスタ", required = false, position = 2)
	private KjbMaster kjbMaster;

	@OneToOne(optional = false)
	@JsonIgnore
	private Estimation estimation;

	/**
	 * 企事部設定区分
	 */
	@Column(nullable = false)
	@Convert(converter = KjbSetDivConverter.class)
	@ApiModelProperty(value = "企事部設定区分", required = true, position = 3)
	private KjbSetDiv departmentDiv;

	/**
	 * MoM企業ID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "MoM企業ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String companyId;

	/**
	 * MoM事業所ID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "MoM事業所ID", required = true, position = 5, allowableValues = "range[0,255]")
	private String officeId;

	/**
	 * 事業所名
	 */
	@ApiModelProperty(value = "事業所名", required = false, position = 6, allowableValues = "range[0,255]")
	private String officeName;

	/**
	 * MoM企事部ID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "MoM企事部ID", required = true, position = 7, allowableValues = "range[0,255]")
	private String momCustId;

	/**
	 * 顧客名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "顧客名", required = true, position = 8, allowableValues = "range[0,255]")
	private String customerName;

	/**
	 * 企業名
	 */
	@ApiModelProperty(value = "企業名", required = false, position = 9, allowableValues = "range[0,255]")
	private String companyName;

	/**
	 * 部門名
	 */
	@ApiModelProperty(value = "部門名", required = false, position = 10, allowableValues = "range[0,255]")
	private String departmentName;

	/**
	 * 電話番号
	 */
	@ApiModelProperty(value = "電話番号", required = false, position = 11, allowableValues = "range[0,255]")
	private String phoneNumber;

	/**
	 * FAX番号
	 */
	@ApiModelProperty(value = "FAX番号", required = false, position = 12, allowableValues = "range[0,255]")
	private String faxNumber;

	/**
	 * 郵便番号
	 */
	@ApiModelProperty(value = "郵便番号", required = false, position = 13, allowableValues = "range[0,255]")
	private String postNumber;

	/**
	 * 住所
	 */
	@ApiModelProperty(value = "住所", required = false, position = 14, allowableValues = "range[0,255]")
	private String address;
}
