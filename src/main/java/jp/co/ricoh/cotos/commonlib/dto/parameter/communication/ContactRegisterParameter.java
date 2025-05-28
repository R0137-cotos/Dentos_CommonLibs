package jp.co.ricoh.cotos.commonlib.dto.parameter.communication;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * 問い合わせ登録時のパラメーターを表します。
 */
@Data
public class ContactRegisterParameter {

	/**
	 * 問い合わせエンティティ
	 */
	@Valid
	@ApiParam(value = "問い合わせエンティティ", required = false)
	private ContactDto contact;

	/**
	 * 親問い合わせエンティティ
	 */
	@ApiParam(value = "親問い合わせエンティティ", required = false)
	private ContactDto parentContact;

	/**
	 * メール件名置換リスト
	 */
	@NotNull
	@ApiParam(value = "メール件名置換リスト", required = true)
	private List<String> mailSubjectRepalceValueList;

	/**
	 * メール本文置換リスト
	 */
	@NotNull
	@ApiParam(value = "メール本文置換リスト", required = true)
	private List<String> mailTextRepalceValueList;
}
