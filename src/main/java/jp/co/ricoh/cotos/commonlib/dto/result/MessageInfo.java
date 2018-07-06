package jp.co.ricoh.cotos.commonlib.dto.result;

import lombok.Data;

/**
 * メッセージ情報を格納するDTO
 */
@Data
public class MessageInfo {

	/** メッセージID */
	private String id;

	/** メッセージ種別 */
	private String type;

	/** メッセージ内容 */
	private String msg;
}
