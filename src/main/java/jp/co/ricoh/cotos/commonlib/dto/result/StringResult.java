package jp.co.ricoh.cotos.commonlib.dto.result;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Data;

/**
 * 単一のString結果を取得するためのクラスです。<br/>
 * 承認者を取得するためには、こちらのクラスを使ってください。 <br/>
 * このクラスを使用してDBへの保存を行うことは出来ません。
 */
@Entity
@Data
public class StringResult {

	@Id
	private String result;
}
