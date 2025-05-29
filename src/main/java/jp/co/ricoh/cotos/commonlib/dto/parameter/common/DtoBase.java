package jp.co.ricoh.cotos.commonlib.dto.parameter.common;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * COTOSDTO共通項目 COTOSの更新用DTOはこのクラスのサブクラスとしてください。
 */
@MappedSuperclass
@Data
public class DtoBase {

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * version
	 */
	@Version
	@ApiModelProperty(value = "version", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long version;

}

