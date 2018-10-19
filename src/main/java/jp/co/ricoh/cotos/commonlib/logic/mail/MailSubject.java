package jp.co.ricoh.cotos.commonlib.logic.mail;

import java.util.List;

import lombok.Data;

@Data
public class MailSubject {
	private String replaceValue1;
	private String replaceValue2;
	private String replaceValue3;
	private String replaceValue4;
	private String replaceValue5;

	public MailSubject(List<String> replaceValueList) {
		this.replaceValue1 = replaceValueList.size() > 0 ? replaceValueList.get(0) : "";
		this.replaceValue2 = replaceValueList.size() > 1 ? replaceValueList.get(1) : "";
		this.replaceValue3 = replaceValueList.size() > 2 ? replaceValueList.get(2) : "";
		this.replaceValue4 = replaceValueList.size() > 3 ? replaceValueList.get(3) : "";
		this.replaceValue5 = replaceValueList.size() > 4 ? replaceValueList.get(4) : "";
	}
}