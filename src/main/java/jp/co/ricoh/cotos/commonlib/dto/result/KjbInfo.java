package jp.co.ricoh.cotos.commonlib.dto.result;

import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;
import lombok.Data;

/**
 * 企事部情報取得Dto
 */
@Data
public class KjbInfo {

	/**
	 * 企事部マスタ
	 */
	private VKjbMaster vKjbMaster;

	/**
	 * 法人格
	 */
	private String kgyHjnKaku;
}
