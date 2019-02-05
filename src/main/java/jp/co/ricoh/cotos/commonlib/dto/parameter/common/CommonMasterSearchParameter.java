package jp.co.ricoh.cotos.commonlib.dto.parameter.common;

import io.swagger.annotations.ApiParam;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import lombok.Data;

@Data
public class CommonMasterSearchParameter {

	/**
	 * サービスカテゴリ
	 */
	@ApiParam(value = "サービスカテゴリ", required = false)
	private ServiceCategory serviceCategory;

	/**
	 * 空行追加フラグ
	 */
	@ApiParam(value = "空行追加フラグ", required = true, allowableValues = "true, false")
	private boolean addBlankRowFlg = false;
}
