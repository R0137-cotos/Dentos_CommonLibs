package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import io.swagger.annotations.ApiParam;
import jp.co.ricoh.cotos.commonlib.entity.communication.Contact;
import lombok.Data;

/**
 * 問い合わせ登録時のパラメーターを表します。
 */
@Data
public class ContactRegisterParameter {

	/**
	 * 親問い合わせエンティティ
	 */
	@ApiParam(value = "問い合わせエンティティ", required = false)
	private Contact contact;

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
