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
@Table(name = "contract_attached_file")
public class ContractAttachedFile {

	@Id
	@ApiModelProperty(value = "契約添付ファイルID", required = true, position = 1, allowableValues = "range[0,99999999999999999999999999999]")
	private long id;

	@ManyToOne
	@JsonIgnore
	private Contract contract;
}
