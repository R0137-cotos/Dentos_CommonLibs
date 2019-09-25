package jp.co.ricoh.cotos.commonlib.dto.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BillingCustomerInfo {

	@ApiModelProperty(value = "得意先コード", required = true, position = 1, allowableValues = "range[0,60]")
	private String originalSystemCode;

	@ApiModelProperty(value = "得意先名称", required = true, position = 2, allowableValues = "range[0,1326")
	private String customerName;

	@ApiModelProperty(value = "サイト番号", required = true, position = 3, allowableValues = "range[0,1326")
	private String customerSiteNumber;

	@ApiModelProperty(value = "販社コード", required = true, position = 4, allowableValues = "range[0,1326")
	private String hanshCd;

	@ApiModelProperty(value = "住所1", required = true, position = 5, allowableValues = "range[0,1326")
	private String address1;

	@ApiModelProperty(value = "住所2", required = true, position = 6, allowableValues = "range[0,1326")
	private String address2;

	@ApiModelProperty(value = "郵便番号", required = true, position = 7, allowableValues = "range[0,1326")
	private String postalCode;

	@ApiModelProperty(value = "回収条件", required = true, position = 8, allowableValues = "range[0,1326")
	private String standardTerms;

	@ApiModelProperty(value = "回収方法", required = true, position = 9, allowableValues = "range[0,1326")
	private String collectMethodName;

	@ApiModelProperty(value = "バーチャル口座使用区分", required = true, position = 9, allowableValues = "range[0,1326")
	private String virtualBnkAccountUseKbn;

	@ApiModelProperty(value = "バーチャル銀行コード", required = true, position = 10, allowableValues = "range[0,1326")
	private String virtualBnkCd;

	@ApiModelProperty(value = "バーチャル本支店コード", required = true, position = 11, allowableValues = "range[0,1326")
	private String virtualBnkBranchCd;

	@ApiModelProperty(value = "バーチャル口座種別", required = true, position = 12, allowableValues = "range[0,1326")
	private String virtualBankAccountType;

	@ApiModelProperty(value = "バーチャル口座番号", required = true, position = 13, allowableValues = "range[0,1326")
	private String virtualBankAccountNum;

	@ApiModelProperty(value = "振込先自社口座_銀行コード(1)", required = true, position = 14, allowableValues = "range[0,1326")
	private String bnkCd1;

	@ApiModelProperty(value = "振込先自社口座_本支店コード(1)", required = true, position = 15, allowableValues = "range[0,1326")
	private String bnkBranchCd1;

	@ApiModelProperty(value = "振込先自社口座_口座番号(1)", required = true, position = 16, allowableValues = "range[0,1326")
	private String bnkAccountCd1;

	@ApiModelProperty(value = "振込先自社口座_銀行コード(2)", required = true, position = 17, allowableValues = "range[0,1326")
	private String bnkCd2;

	@ApiModelProperty(value = "振込先自社口座_支店コード(2)", required = true, position = 18, allowableValues = "range[0,1326")
	private String bnkBranchCd2;

	@ApiModelProperty(value = "振込先自社口座_口座番号(2)", required = true, position = 19, allowableValues = "range[0,1326")
	private String bnkAccountCd2;

	@ApiModelProperty(value = "振込先自社口座_口座種別(1)", required = true, position = 20, allowableValues = "range[0,1326")
	private String bankAccountCls1;

	@ApiModelProperty(value = "振込先自社口座_口座種別(2)", required = true, position = 21, allowableValues = "range[0,1326")
	private String bankAccountCls2;

	@ApiModelProperty(value = "売上課所コード", required = true, position = 22, allowableValues = "range[0,1326")
	private String customerPersonSectionCode;

	@ApiModelProperty(value = "自振口座_口座番号", required = true, position = 23, allowableValues = "range[0,1326")
	private String bankAccountNum;

	@ApiModelProperty(value = "自振口座_銀行番号", required = true, position = 24, allowableValues = "range[0,1326")
	private String bankNumber;

	@ApiModelProperty(value = "自振口座_銀行名", required = true, position = 25, allowableValues = "range[0,1326")
	private String bankName;

	@ApiModelProperty(value = "自振口座_支店番号", required = true, position = 26, allowableValues = "range[0,1326")
	private String bankNum;

	@ApiModelProperty(value = "自振口座_支店名", required = true, position = 27, allowableValues = "range[0,1326")
	private String bankBranchName;

	@ApiModelProperty(value = "自振口座_口座名義人カナ名", required = true, position = 28, allowableValues = "range[0,1326")
	private String accountHolderNameAlt;

	@ApiModelProperty(value = "事業所正式名称", required = true, position = 29, allowableValues = "range[0,1326")
	private String ebsBusinessPlaceName;

	@ApiModelProperty(value = "請求先事業所", required = true, position = 30, allowableValues = "range[0,1326")
	private String billingCustomerAddress;

	@ApiModelProperty(value = "自振引落日", required = true, position = 31, allowableValues = "range[0,1326")
	private String autoTransferDay;

}
