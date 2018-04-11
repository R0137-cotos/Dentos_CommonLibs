package jp.co.ricoh.cotos.commonlib;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class TestData {
	@Id
	@GeneratedValue
	private long id;
	private String moji;
	private String likeSearchString;
}