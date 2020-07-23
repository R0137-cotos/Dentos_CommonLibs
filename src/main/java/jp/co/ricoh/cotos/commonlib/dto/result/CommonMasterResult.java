package jp.co.ricoh.cotos.commonlib.dto.result;

import java.util.List;

import lombok.Data;

/**
 * 汎用マスタ結果
 */
@Data
public class CommonMasterResult {

	/**
	 * カラム名
	 */
	private String columnName;

	/**
	 * 汎用マスタ名称
	 */
	private String articleName;

	/**
	 * 汎用マスタ明細
	 */
	private List<CommonMasterDetailResult> commonMasterDetailResultList;
}
