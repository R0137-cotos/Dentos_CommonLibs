package jp.co.ricoh.cotos.commonlib.testcsvutil;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({ "会員番号", "氏名", "年齢", "誕生日", "体重" })
public class TestCsvDataHeaderJapanese {
	@JsonProperty("会員番号")
	private long id;

	@JsonProperty("氏名")
	private String name;

	@JsonProperty("年齢")
	private int age;

	@JsonProperty("誕生日")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "JST")
	private Date birthday;

	@JsonProperty("体重")
	private double weight;

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE) //
				.append("id", id) //
				.append("name", name) //
				.append("age", age) //
				.append("birthday", new SimpleDateFormat("yyyy/MM/dd").format(birthday)) //
				.append("weight", weight).toString(); //
	}
}
