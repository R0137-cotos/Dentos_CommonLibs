package jp.co.ricoh.cotos.commonlib.excel;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestExcelPersonDataRow {
	/**
	 * 氏名
	 */
	private String name;

	/**
	 * 年齢
	 */
	private int age;

	/**
	 * 誕生日
	 */
	private Date birthday;

	/**
	 * 体重
	 */
	private double weight;

	/**
	 * 誕生日のgetter
	 * @return
	 */
	public String getBirthday() {
		return new SimpleDateFormat("yyyy/MM/dd").format(birthday);
	}
}
