package jp.co.ricoh.cotos.commonlib.logic.message;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.dto.result.MessageInfo;

/**
 * チェック共通クラス
 */
@Component
public class MessageUtil {

	private enum MessageAttribute {
		ID, TYPE, MSG;
	}

	@Autowired
	MessageSource messageSource;

	/**
	 * 単一メッセージ項目を取得
	 * 
	 * @param key
	 *            キー
	 * @return メッセージ
	 */
	public String convertSingleValue(String key) {
		return messageSource.getMessage(key, null, Locale.JAPANESE);
	}

	/**
	 * 単一メッセージ項目を取得
	 * 
	 * @param key
	 *            キー
	 * @param regexList
	 *            メッセージ引数
	 * @return メッセージ
	 */
	public String convertSingleValue(String key, String[] regexList) {
		return messageSource.getMessage(key, regexList, Locale.JAPANESE);
	}

	/**
	 * メッセージ生成
	 * 
	 * @param key
	 *            キー
	 * @return メッセージ情報
	 */
	public MessageInfo createMessageInfo(String key) {

		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setId(messageSource.getMessage(key + "." + MessageAttribute.ID, null, Locale.JAPANESE));
		messageInfo.setType(messageSource.getMessage(key + "." + MessageAttribute.TYPE, null, Locale.JAPANESE));
		messageInfo.setMsg(messageSource.getMessage(key + "." + MessageAttribute.MSG, null, Locale.JAPANESE));

		return messageInfo;
	}

	/**
	 * メッセージ生成
	 * 
	 * @param key
	 *            キー
	 * @param regexList
	 *            メッセージ引数
	 * @return メッセージ情報
	 */
	public MessageInfo createMessageInfo(String key, String[] regexList) {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setId(messageSource.getMessage(key + "." + MessageAttribute.ID, null, Locale.JAPANESE));
		messageInfo.setType(messageSource.getMessage(key + "." + MessageAttribute.TYPE, null, Locale.JAPANESE));
		messageInfo.setMsg(messageSource.getMessage(key + "." + MessageAttribute.MSG, regexList, Locale.JAPANESE));

		return messageInfo;
	}
}
