package jp.co.ricoh.cotos.commonlib.excel.seishiki;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfo {

	/**
	 * 商品コード
	 */
	private String code;

	/**
	 * 商品名
	 */
	private String name;

	/**
	 * 購入日
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "JST")
	private Date date;

	/**
	 * 単価
	 */
	private int price;

	/**
	 * 数量
	 */
	private int count;

	public String getDate() {
		return new SimpleDateFormat("yyyy/MM/dd").format(date);
	}
}
