package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Data
@Table(name = "contract_check_result")
public class ContractCheckResult {

	@Id
	@ApiModelProperty(value = "契約チェック結果ID", required = true, position = 1, allowableValues = "range[0,99999999999999999999999999999]")
	private long id;

	@ManyToOne
	@JsonIgnore
	private Contract contract;
}
