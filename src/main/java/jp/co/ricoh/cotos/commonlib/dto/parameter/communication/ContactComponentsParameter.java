package jp.co.ricoh.cotos.commonlib.dto.parameter.communication;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiParam;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.TargetDirectionType;
import jp.co.ricoh.cotos.commonlib.entity.communication.Contact;
import lombok.Data;

/**
 * 問い合わせ情報を作成するためのパラメーターを表します。
 */
@Data
public class ContactComponentsParameter {

	/**
	 * 親問い合わせエンティティ
	 */
	@ApiParam(value = "親問い合わせエンティティ", required = false)
	private Contact parentContact;

	/**
	 * 問い合わせタイトル
	 */
	@NotNull
	@Size(max = 255)
	@ApiParam(value = "問い合わせタイトル", allowableValues = "range[0,255]", required = true)
	private String contactTitle;

	/**
	 * 問い合わせ内容
	 */
	@NotNull
	@ApiParam(value = "問い合わせ内容", allowableValues = "range[0,255]", required = true)
	private String contactMessage;

	/**
	 * 宛先種別
	 */
	@NotNull
	@ApiParam(value = "宛先種別", allowableValues = "担当CE(\"1\"), 担当SA(\"2\"), 全担当者(\"98\"), その他(\"99\");", required = true)
	private TargetDirectionType targetDirectionType;

	/**
	 * メールTO送付先MoM社員IDリスト
	 */
	@ApiParam(value = "メールTO送付先MoM社員IDリスト", required = false)
	private List<String> momEmpIdSendToList;

	/**
	 * メールCC送付先MoM社員IDリスト
	 */
	@ApiParam(value = "メールCC送付先MoM社員IDリスト", required = false)
	private List<String> momEmpIdSendCcList;
}
