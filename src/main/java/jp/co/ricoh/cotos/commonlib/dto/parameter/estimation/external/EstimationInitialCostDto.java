package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.external;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EstimationInitialCostDto {

	/**
	 * ログインMoM社員ID
	 */
	@NotNull
	@Size(max = 8)
	@ApiModelProperty(value = "ログインMoM社員Id", required = true, position = 1, allowableValues = "range[0,8]")
	private String loginMoMId;
	
	/**
	 * ログインシングルユーザID
	 */
	@NotNull
	@Size(max = 64)
	@ApiModelProperty(value = "ログインシングルユーザID", required = true, position = 2, allowableValues = "range[0,64]")
	private String loginSingleUserid;
	
	/**
	 * 商流
	 */
	@NotNull
	@Size(max = 2)
	@ApiModelProperty(value = "商流", required = true, position = 3, allowableValues = "range[0,2]")
	private String oeProductRoot;
	
	/**
	 * お客様企事部ID
	 */
	@NotNull
	@Size(max = 15)
	@ApiModelProperty(value = "お客様企事部ID", required = true, position = 4, allowableValues = "range[0,15]")
	private String custInfoCpmpanyId;
	
	/**
	 * RTS見積No
	 */
	@NotNull
	@Size(max = 25)
	@ApiModelProperty(value = "RTS見積No", required = true, position = 5, allowableValues = "range[0,25]")
	private String estimatedNoRts;
	
	/**
	 * 初期日 品種コード
	 */
	@NotNull
	@Size(max = 25)
	@ApiModelProperty(value = "初期日 品種コード", required = true, position = 6, allowableValues = "range[0,25]")
	private String productCd;
	
	/**
	 * 初期日 標準価格
	 */
	@NotNull
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "初期日 標準価格", required = true, position = 7, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal unitPrice;
	
	/**
	 * 初期日 見積り単価
	 */
	@NotNull
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "初期日 見積り単価", required = true, position = 8, allowableValues = "range[0.00,9999999999999999999.99]")
	private Integer estimatedUnitPrice;
	
	/**
	 * 初期日 数量
	 */
	@NotNull
	@Min(0)
	@Max(99999)
	@ApiModelProperty(value = "初期日 数量", required = true, position = 9, allowableValues = "range[0,99999]")
	private Integer amt;
	
	/**
	 * 初期費　見積り金額
	 */
	@NotNull
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "初期日 見積り金額", required = true, position = 10, allowableValues = "range[0.00,9999999999999999999.99]")
	private Integer estimatedPrice;
	
	/**
	 * 販社CD
	 */
	@NotNull
	@Size(max = 3)
	@ApiModelProperty(value = "販社CD", required = true, position = 11, allowableValues = "range[0,3]")
	private String hanshCd;
}
