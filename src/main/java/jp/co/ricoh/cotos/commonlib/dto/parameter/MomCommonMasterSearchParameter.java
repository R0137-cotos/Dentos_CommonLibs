package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class MomCommonMasterSearchParameter {

	/**
	 * 汎用マスタIDリスト
	 */
	@ApiParam(value = "汎用マスタIDリスト", required = false)
	private List<String> commonArticleCdList;

	/**
	 * 空行追加フラグ
	 */
	@ApiParam(value = "空行追加フラグ", required = true, allowableValues = "true, false")
	private boolean addBlankRowFlg = false;
}
