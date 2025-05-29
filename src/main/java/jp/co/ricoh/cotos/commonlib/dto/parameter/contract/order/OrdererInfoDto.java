package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文者情報DTO
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrdererInfoDto {

	/**
	 * 注文者情報 会社名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "注文者情報　会社名", required = false, position = 2, allowableValues = "range[0,255]")
	private String ordererCompanyName;

	/**
	 * 注文者情報 漢字（姓＋名）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "注文者情報　漢字（姓＋名）", required = false, position = 3, allowableValues = "range[0,255]")
	private String ordererNameKanji;

	/**
	 * 注文者情報 カナ（姓＋名）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "注文者情報　カナ（姓＋名）", required = false, position = 4, allowableValues = "range[0,255]")
	private String ordererNameKana;

	/**
	 * 注文者情報 メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "注文者情報　メールアドレス", required = false, position = 5, allowableValues = "range[0,255]")
	private String ordererMailAddress;

	/**
	 * 注文者情報 郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "注文者情報　郵便番号", required = false, position = 6, allowableValues = "range[0,255]")
	private String ordererPostNumber;

	/**
	 * 注文者情報 事業所名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "注文者情報　事業所名", required = false, position = 7, allowableValues = "range[0,255]")
	private String ordererOfficeName;

	/**
	 * 注文者情報 住所
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "注文者情報　住所", required = false, position = 8, allowableValues = "range[0,255]")
	private String ordererAddress;

	/**
	 * 注文者情報 電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "注文者情報　電話番号", required = false, position = 9, allowableValues = "range[0,255]")
	private String ordererPhoneNumber;

}
