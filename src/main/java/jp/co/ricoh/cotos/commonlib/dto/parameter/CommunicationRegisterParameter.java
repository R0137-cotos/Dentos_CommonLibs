package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import io.swagger.annotations.ApiParam;
import jp.co.ricoh.cotos.commonlib.entity.communication.Communication;
import lombok.Data;

/**
 * コミュニケーション登録時のパラメーターを表します。
 */
@Data
public class CommunicationRegisterParameter {

	/**
	 * コミュニケーションエンティティ
	 */
	@ApiParam(value = "コミュニケーションエンティティ", required = true)
	private Communication communication;

	/**
	 * メールCC送付先MoM社員IDリスト
	 */
	@ApiParam(value = "メールCC送付先MoM社員IDリスト", required = false)
	private List<String> momEmpList;

	/**
	 * メール件名置換リスト
	 */
	@ApiParam(value = "メール件名置換リスト", required = true)
	private List<String> mailSubjectRepalceValueList;

	/**
	 * メール本文置換リスト
	 */
	@ApiParam(value = "メール本文置換リスト", required = true)
	private List<String> mailTextRepalceValueList;
}
