package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * CEマスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "ce_master")
public class CeMaster extends EntityBaseMaster {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ce_master_seq")
	@SequenceGenerator(name = "ce_master_seq", sequenceName = "ce_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999999999999]")
	private long id;

	/**
	 * CEコード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "CEコード", required = false, position = 2, allowableValues = "range[0,255]")
	private String nCeCode;

	/**
	 * CE名(漢字)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "CE名(漢字)", required = false, position = 3, allowableValues = "range[0,255]")
	private String nCeDesc;

	/**
	 * CE名(カナ)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "CE名(カナ)", required = false, position = 4, allowableValues = "range[0,255]")
	private String nCeDescKana;

	/**
	 * 帰属課所コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "帰属課所コード", required = false, position = 5, allowableValues = "range[0,255]")
	private String nBelongOrgCode;

	/**
	 * 社員コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "社員コード", required = false, position = 6, allowableValues = "range[0,255]")
	private String nEmpCode;

	/**
	 * グループコード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "グループコード", required = false, position = 7, allowableValues = "range[0,255]")
	private String nGroupCode;

	/**
	 * 業務区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "業務区分", required = false, position = 8, allowableValues = "range[0,255]")
	private String nBusinessType;

	/**
	 * クラス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "クラス", required = false, position = 9, allowableValues = "range[0,255]")
	private String nClass;

	/**
	 * 入社年月日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "入社年月日", required = false, position = 10)
	private Date nEmpDate;

	/**
	 * 更新区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "更新区分", required = false, position = 11, allowableValues = "range[0,255]")
	private String nModifyType;

	/**
	 * 更新年月日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "更新年月日", required = false, position = 12)
	private Date nModifyDate;

	/**
	 * 登録年月日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "登録年月日", required = false, position = 13)
	private Date nNewDate;

	/**
	 * 退職年月日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "退職年月日", required = false, position = 14)
	private Date nRetireDate;

	/**
	 * TeMS参照区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "TeMS参照区分", required = false, position = 15, allowableValues = "range[0,255]")
	private String nTemsRefType;

	/**
	 * TeMS権限区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "TeMS権限区分", required = false, position = 16, allowableValues = "range[0,255]")
	private String nTemsAuthType;

	/**
	 * MA区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MA区分", required = false, position = 17, allowableValues = "range[0,255]")
	private String nMaType;

	/**
	 * SI区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "SI区分", required = false, position = 18, allowableValues = "range[0,255]")
	private String nSiType;
}
