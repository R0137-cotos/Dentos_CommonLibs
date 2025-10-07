package jp.co.ricoh.cotos.commonlib.entity.estimation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import lombok.Data;

/**
 * 見積番号生成用のオブジェクト
 * 
 * @author tito
 *
 */
@Data
@Entity(name = "EstimationGenaratedNumber")
public class GeneratedNumber {

	/**
	 * numberだとOracleの予約後と重複するのでgeneratedNumberに。
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_number_seq")
	@SequenceGenerator(name = "estimation_number_seq", sequenceName = "estimation_number_seq", allocationSize = 1)
	private long generatedNumber;
}
