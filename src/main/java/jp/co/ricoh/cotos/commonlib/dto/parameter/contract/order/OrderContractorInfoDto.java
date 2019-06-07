package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import java.util.Date;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文顧客情報DTO
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderContractorInfoDto extends DtoBase {

	/**
	 * 企業ＩＤ
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "企業ＩＤ", required = false, position = 2, allowableValues = "range[0,]")
	private String companyid;

	/**
	 * 企事部ＩＤ
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "企事部ＩＤ", required = false, position = 3, allowableValues = "range[0,]")
	private String kjbid;

	/**
	 * 会員基本ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "会員基本ID", required = false, position = 4, allowableValues = "range[0,]")
	private String netricohAccount;

	/**
	 * 得意先コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "得意先コード", required = false, position = 5, allowableValues = "range[0,]")
	private String customerCd;

	/**
	 * 会社名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "会社名", required = false, position = 6, allowableValues = "range[0,]")
	private String contractorCompanyName;

	/**
	 * 担当者漢字（姓＋名）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当者漢字（姓＋名）", required = false, position = 7, allowableValues = "range[0,]")
	private String contractorNameKanji;

	/**
	 * 担当者カナ（姓＋名）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当者カナ（姓＋名）", required = false, position = 8, allowableValues = "range[0,]")
	private String contractorNameKana;

	/**
	 * 担当者メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当者メールアドレス", required = false, position = 9, allowableValues = "range[0,]")
	private String contractorMailAddress;

	/**
	 * 郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "郵便番号", required = false, position = 10, allowableValues = "range[0,]")
	private String contractorPostNumber;

	/**
	 * 事業所名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "事業所名", required = false, position = 11, allowableValues = "range[0,]")
	private String contractorOfficeName;

	/**
	 * 住所
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "住所", required = false, position = 12, allowableValues = "range[0,]")
	private String contractorAddress;

	/**
	 * 電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "電話番号", required = false, position = 13, allowableValues = "range[0,]")
	private String contractorPhoneNumber;

	/**
	 * 利用登録権限(NetRICOH)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "利用登録権限(NetRICOH)", required = false, position = 14, allowableValues = "range[0,]")
	private String authorityForNetricoh;

	/**
	 * サービス開始希望日
	 */
	@ApiModelProperty(value = "サービス開始希望日", required = false, position = 15)
	private Date desiredServiceStartDate;

}
