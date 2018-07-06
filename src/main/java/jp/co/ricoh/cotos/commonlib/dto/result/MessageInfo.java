package jp.co.ricoh.cotos.commonlib.dto.result;

import lombok.Data;

/**
 * メッセージ情報を格納するDTO
 */
@Data
public class MessageInfo {

	private String id;
	private String type;
	private String msg;
}
