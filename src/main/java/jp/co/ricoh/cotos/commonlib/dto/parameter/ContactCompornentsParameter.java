package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import io.swagger.annotations.ApiParam;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.TargetDirectionType;
import jp.co.ricoh.cotos.commonlib.entity.communication.Contact;
import lombok.Data;

/**
 * 問い合わせ情報を作成するためのパラメーターを表します。
 */
@Data
public class ContactCompornentsParameter {

	/**
	 * 親問い合わせエンティティ
	 */
	@ApiParam(value = "親問い合わせエンティティ", required = false)
	private Contact parentContact;

	/**
	 * 問い合わせタイトル
	 */
	@ApiParam(value = "問い合わせタイトル", required = true)
	private String contactTitle;

	/**
	 * 問い合わせ内容
	 */
	@ApiParam(value = "問い合わせ内容", required = true)
	private String contactMessage;

	/**
	 * 宛先種別
	 */
	@ApiParam(value = "宛先種別", required = true)
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
