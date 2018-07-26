package jp.co.ricoh.cotos.commonlib.testCSVUtil;

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
@JsonPropertyOrder({ "id", "name", "age", "birthday", "weight" })
public class TestCSVData {
	@JsonProperty("id")
	private long id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("age")
	private int age;

	@JsonProperty("birthday")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "JST")
	private Date birthday;

	@JsonProperty("weight")
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
