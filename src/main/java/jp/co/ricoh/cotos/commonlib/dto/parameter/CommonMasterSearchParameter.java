package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import lombok.Data;

@Data
public class CommonMasterSearchParameter {

	/**
	 * 汎用マスタIDリスト
	 */
	private List<String> commonItemIdList;

	/**
	 * 空行追加フラグ
	 */
	private boolean addBlankRowFlg = false;
}
