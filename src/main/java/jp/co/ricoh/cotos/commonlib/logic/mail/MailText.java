package jp.co.ricoh.cotos.commonlib.logic.mail;

import java.util.List;

import lombok.Data;

@Data
public class MailText {
	private String replaceValue1;
	private String replaceValue2;
	private String replaceValue3;
	private String replaceValue4;
	private String replaceValue5;
	private String replaceValue6;
	private String replaceValue7;
	private String replaceValue8;
	private String replaceValue9;
	private String replaceValue10;

	public MailText(List<String> replaceValueList) {
		this.replaceValue1 = replaceValueList.size() > 0 ? replaceValueList.get(0) : "";
		this.replaceValue2 = replaceValueList.size() > 1 ? replaceValueList.get(1) : "";
		this.replaceValue3 = replaceValueList.size() > 2 ? replaceValueList.get(2) : "";
		this.replaceValue4 = replaceValueList.size() > 3 ? replaceValueList.get(3) : "";
		this.replaceValue5 = replaceValueList.size() > 4 ? replaceValueList.get(4) : "";
		this.replaceValue6 = replaceValueList.size() > 5 ? replaceValueList.get(5) : "";
		this.replaceValue7 = replaceValueList.size() > 6 ? replaceValueList.get(6) : "";
		this.replaceValue8 = replaceValueList.size() > 7 ? replaceValueList.get(7) : "";
		this.replaceValue9 = replaceValueList.size() > 8 ? replaceValueList.get(8) : "";
		this.replaceValue10 = replaceValueList.size() > 9 ? replaceValueList.get(9) : "";
	}
}
