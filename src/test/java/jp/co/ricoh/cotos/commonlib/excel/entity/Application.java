package jp.co.ricoh.cotos.commonlib.excel.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {

	/**
	 * タイトル
	 */
	private String title;

	/**
	 * 文字列項目
	 */
	private String text;

	/**
	 * 数値項目
	 */
	private int number;

	/**
	 * 商品リスト
	 */
	private List<ItemInfo> itemList;
}
