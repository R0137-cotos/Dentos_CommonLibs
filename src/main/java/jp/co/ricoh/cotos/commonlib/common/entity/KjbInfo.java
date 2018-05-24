package jp.co.ricoh.cotos.commonlib.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 企事部情報を取得するためのDtoです。<br/>
 * このクラスを使用してDBへの保存を行うことは出来ません。
 */
@Data
public class KjbInfo {

	@ApiModelProperty(value = "MoM顧客システム連携ID", required = false, position = 1, allowableValues = "range[0,15]")
	private String momRelId;

	@ApiModelProperty(value = "MoM顧客企事部ID", required = false, position = 2, allowableValues = "range[0,15]")
	private String momKjbId;

	@ApiModelProperty(value = "企業名", required = false, position = 3, allowableValues = "range[0,202]")
	private String companyName;

	@ApiModelProperty(value = "事業所代表TEL", required = false, position = 4, allowableValues = "range[0,50]")
	private String telNum;

	@ApiModelProperty(value = "事業所郵便番号", required = false, position = 5, allowableValues = "range[0,50]")
	private String postNum;

	@ApiModelProperty(value = "事業所住所", required = false, position = 6, allowableValues = "range[0,740]")
	private String address;
}
