package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

/**
 * 契約番号生成用のオブジェクト
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_number_seq")
	@SequenceGenerator(name = "contract_number_seq", sequenceName = "contract_number_seq", allocationSize = 1)
	private long generatedNumber;
}
