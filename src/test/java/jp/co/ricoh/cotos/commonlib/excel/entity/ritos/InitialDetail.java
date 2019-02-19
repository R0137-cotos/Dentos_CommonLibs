package jp.co.ricoh.cotos.commonlib.excel.entity.ritos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InitialDetail {
	private String code;
	private String name;
	private int unitPrice;
	private int count;
}
