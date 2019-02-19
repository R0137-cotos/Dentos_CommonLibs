package jp.co.ricoh.cotos.commonlib.excel.entity.ritos;

import lombok.Data;

@Data
public class Dealer {
	private String postCode;
	private String postCode1;
	private String postCode2;
	private String postCode3;
	private String postCode4;
	private String postCode5;
	private String postCode6;
	private String postCode7;
	private String address;
	private String addressKana;
	private String name;
	private String nameKana;
	private String approver;
	private String approverKana;

	public String getPostCode1() {
		return postCode.substring(0, 1);
	}

	public String getPostCode2() {
		return postCode.substring(1, 2);
	}

	public String getPostCode3() {
		return postCode.substring(2, 3);
	}

	public String getPostCode4() {
		return postCode.substring(3, 4);
	}

	public String getPostCode5() {
		return postCode.substring(4, 5);
	}

	public String getPostCode6() {
		return postCode.substring(5, 6);
	}

	public String getPostCode7() {
		return postCode.substring(6, 7);
	}
}
