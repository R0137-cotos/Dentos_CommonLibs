package jp.co.ricoh.cotos.commonlib;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

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