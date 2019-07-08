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
	
	@ApiModelProperty(value = "バーチャル銀行コード", required = true, position = 10, allowableValues = "range[0,1326")
	private String virtualBnkCd;
	
	@ApiModelProperty(value = "バーチャル本支店コード", required = true, position = 11, allowableValues = "range[0,1326")
	private String virtualBnkBranchCd;
	
	@ApiModelProperty(value = "バーチャル口座種別", required = true, position = 12, allowableValues = "range[0,1326")
	private String virtualBankAccountType;
	
	@ApiModelProperty(value = "バーチャル口座番号", required = true, position = 13, allowableValues = "range[0,1326")
	private String virtualBankAccountNum;
}
