package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.VKbMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.VKbMaster.DepartmentDiv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約情報の中で保持する顧客情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "customer_contract")
public class CustomerContract extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_contract_seq")
	@SequenceGenerator(name = "customer_contract_seq", sequenceName = "customer_contract_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 企事部マスタ
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "mom_kjb_system_id", referencedColumnName = "mclMomRelId")
	@ApiModelProperty(value = "企事部マスタ", required = true, position = 2)
	private VKbMaster vKbMaster;
	
//	/**
//	 * 企事部マスタID
//	 */
//	@Column(nullable = false, name = "mom_kjb_system_id")
//	@ApiModelProperty(value = "企事部マスタID", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
//	private long vKbMasterId;

	/**
	 * MoM企事部ID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "MoM企事部ID", required = true, position = 3, allowableValues = "range[0,255]")
	private String momCustId;

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
	 * 企事部設定区分
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "企事部設定区分", required = true, position = 6)
	private DepartmentDiv departmentDiv;

	/**
	 * 顧客名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "顧客名", required = true, position = 7, allowableValues = "range[0,255]")
	private String customerName;

	/**
	 * 企業名
	 */
	@ApiModelProperty(value = "企業名", required = false, position = 8, allowableValues = "range[0,255]")
	private String companyName;

	/**
	 * 企業名（カナ）
	 */
	@ApiModelProperty(value = "企業名（カナ）", required = false, position = 9, allowableValues = "range[0,255]")
	private String companyNameKana;

	/**
	 * 事業所名
	 */
	@ApiModelProperty(value = "事業所名", required = false, position = 10, allowableValues = "range[0,255]")
	private String officeName;

	/**
	 * 部門名
	 */
	@ApiModelProperty(value = "部門名", required = false, position = 11, allowableValues = "range[0,255]")
	private String departmentName;

	/**
	 * 郵便番号
	 */
	@ApiModelProperty(value = "郵便番号", required = false, position = 12, allowableValues = "range[0,255]")
	private String postNumber;

	/**
	 * 住所
	 */
	@ApiModelProperty(value = "住所", required = false, position = 13, allowableValues = "range[0,1000]")
	private String address;

	/**
	 * 会社代表電話番号
	 */
	@ApiModelProperty(value = "会社代表電話番号", required = false, position = 14, allowableValues = "range[0,255]")
	private String phoneNumber;

	/**
	 * 会社代表FAX番号
	 */
	@ApiModelProperty(value = "会社代表FAX番号", required = false, position = 15, allowableValues = "range[0,255]")
	private String faxNumber;

	/**
	 * 企業代表者名
	 */
	@ApiModelProperty(value = "企業代表者名", required = false, position = 16, allowableValues = "range[0,255]")
	private String companyRepresentativeName;

	/**
	 * MoM非連携_企業代表者名（カナ）
	 */
	@ApiModelProperty(value = "MoM非連携_企業代表者名（カナ）", required = false, position = 17, allowableValues = "range[0,255]")
	private String companyRepresentativeNameKana;

	/**
	 * MoM非連携_担当者氏名
	 */
	@ApiModelProperty(value = "MoM非連携_担当者氏名", required = false, position = 18, allowableValues = "range[0,255]")
	private String picName;

	/**
	 * MoM非連携_担当者氏名（カナ）
	 */
	@ApiModelProperty(value = "MoM非連携_担当者氏名（カナ）", required = false, position = 19, allowableValues = "range[0,255]")
	private String picNameKana;

	/**
	 * MoM非連携_担当者部署
	 */
	@ApiModelProperty(value = "MoM非連携_担当者部署", required = false, position = 20, allowableValues = "range[0,255]")
	private String picDeptName;

	/**
	 * MoM非連携_担当者電話番号
	 */
	@ApiModelProperty(value = "MoM非連携_担当者電話番号", required = false, position = 21, allowableValues = "range[0,255]")
	private String picPhoneNumber;

	/**
	 * MoM非連携_担当者FAX番号
	 */
	@ApiModelProperty(value = "MoM非連携_担当者FAX番号", required = false, position = 22, allowableValues = "range[0,255]")
	private String picFaxNumber;

	/**
	 * MoM非連携_担当者メールアドレス
	 */
	@ApiModelProperty(value = "MoM非連携_担当者メールアドレス", required = false, position = 23, allowableValues = "range[0,255]")
	private String picMailAddress;

	/**
	 * 契約
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 24)
	private Contract contract;
}