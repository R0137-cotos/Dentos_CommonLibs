package jp.co.ricoh.cotos.commonlib.entity.contract.order;

import java.util.Date;

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
 * 注文顧客情報
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "order_contractor_info")
public class OrderContractorInfo extends EntityBase {

/**
 * ID
 */
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_contractor_info_seq")
@SequenceGenerator(name = "order_contractor_info_seq", sequenceName = "order_contractor_info_seq", allocationSize = 1)
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
 * 企業ＩＤ
 */
@Column
@ApiModelProperty(value = "企業ＩＤ", required = false, position = 2, allowableValues = "range[0,]")
private String companyId;

/**
 * 企事部ＩＤ
 */
@Column
@ApiModelProperty(value = "企事部ＩＤ", required = false, position = 3, allowableValues = "range[0,]")
private String kjbId;

/**
 * 会員基本ID
 */
@Column
@ApiModelProperty(value = "会員基本ID", required = false, position = 4, allowableValues = "range[0,]")
private String netricohAccount;

/**
 * 得意先コード
 */
@Column
@ApiModelProperty(value = "得意先コード", required = false, position = 5, allowableValues = "range[0,]")
private String customerCd;

/**
 * 会社名
 */
@Column
@ApiModelProperty(value = "会社名", required = false, position = 6, allowableValues = "range[0,]")
private String contractorCompanyName;

/**
 * 担当者漢字（姓＋名）
 */
@Column
@ApiModelProperty(value = "担当者漢字（姓＋名）", required = false, position = 7, allowableValues = "range[0,]")
private String contractorNameKanji;

/**
 * 担当者カナ（姓＋名）
 */
@Column
@ApiModelProperty(value = "担当者カナ（姓＋名）", required = false, position = 8, allowableValues = "range[0,]")
private String contractorNameKana;

/**
 * 担当者メールアドレス
 */
@Column
@ApiModelProperty(value = "担当者メールアドレス", required = false, position = 9, allowableValues = "range[0,]")
private String contractorMailAddress;

/**
 * 郵便番号
 */
@Column
@ApiModelProperty(value = "郵便番号", required = false, position = 10, allowableValues = "range[0,]")
private String contractorPostNumber;

/**
 * 事業所名
 */
@Column
@ApiModelProperty(value = "事業所名", required = false, position = 11, allowableValues = "range[0,]")
private String contractorOfficeName;

/**
 * 住所
 */
@Column
@ApiModelProperty(value = "住所", required = false, position = 12, allowableValues = "range[0,]")
private String contractorAddress;

/**
 * 電話番号
 */
@Column
@ApiModelProperty(value = "電話番号", required = false, position = 13, allowableValues = "range[0,]")
private String contractorPhoneNumber;

/**
 * 利用登録権限(NetRICOH)
 */
@Column
@ApiModelProperty(value = "利用登録権限(NetRICOH)", required = false, position = 14, allowableValues = "range[0,]")
private String authorityForNetricoh;

/**
 * サービス開始希望日
 */
@Column
@ApiModelProperty(value = "サービス開始希望日", required = false, position = 15)
private Date desiredServiceStartDate;

}

