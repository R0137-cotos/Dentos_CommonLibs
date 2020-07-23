package jp.co.ricoh.cotos.commonlib.excel.entity.ritos;

import java.util.List;

import lombok.Data;

@Data
public class Shinsei {
	private String itemName;
	private String docNumber;
	private String kjbId;
	private Customer customer;
	private Dealer dealer;
	private List<InitialDetail> initialDetailList;
	private String paymentFlagName;
	private List<Detail> detailList;
}
