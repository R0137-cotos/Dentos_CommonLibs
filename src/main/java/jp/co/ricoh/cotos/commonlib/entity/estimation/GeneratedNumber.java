package jp.co.ricoh.cotos.commonlib.entity.estimation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

/**
 * 見積番号生成用のオブジェクト
 * 
 * @author tito
 *
 */
@Data
@Entity
public class GeneratedNumber {

	/**
	 * numberだとOracleの予約後と重複するのでgeneratedNumberに。
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_number_seq")
	@SequenceGenerator(name = "estimation_number_seq", sequenceName = "estimation_number_seq", allocationSize = 1)
	private long generatedNumber;
}
