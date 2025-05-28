package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文セットアップ先情報DTO
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderSetupInfoDto {

	/**
	 * セットアップ先情報 会社名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "セットアップ先情報　会社名", required = false, position = 2, allowableValues = "range[0,255]")
	private String setupCompanyName;

	/**
	 * セットアップ先情報 漢字（姓＋名）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "セットアップ先情報　漢字（姓＋名）", required = false, position = 3, allowableValues = "range[0,255]")
	private String setupNameKanji;

	/**
	 * セットアップ先情報 カナ（姓＋名）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "セットアップ先情報　カナ（姓＋名）", required = false, position = 4, allowableValues = "range[0,255]")
	private String setupNameKana;

	/**
	 * セットアップ先情報 メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "セットアップ先情報　メールアドレス", required = false, position = 5, allowableValues = "range[0,255]")
	private String setupMailAddress;

	/**
	 * セットアップ先情報 郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "セットアップ先情報　郵便番号", required = false, position = 6, allowableValues = "range[0,255]")
	private String setupPostNumber;

	/**
	 * セットアップ先情報 事業所名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "セットアップ先情報　事業所名", required = false, position = 7, allowableValues = "range[0,255]")
	private String setupOfficeName;

	/**
	 * セットアップ先情報 住所
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "セットアップ先情報　住所", required = false, position = 8, allowableValues = "range[0,255]")
	private String setupAddress;

	/**
	 * セットアップ先情報 電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "セットアップ先情報　電話番号", required = false, position = 9, allowableValues = "range[0,255]")
	private String setupPhoneNumber;

}
