package jp.co.ricoh.cotos.commonlib.logic.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;

/**
 * DB存在チェック管理クラス
 */
@Component
public class DBFoundCheck {

	@Autowired
	MvEmployeeMasterRepository mvEmployeeMasterRepository;

	/**
	 * 社員マスタ存在チェック
	 * 
	 * @param momEmployeeId
	 *            MoM社員ID
	 * @return チェック結果
	 */
	public boolean existsEmployeeMaster(String momEmployeeId) {
		if (null != momEmployeeId) {
			MvEmployeeMaster mvEmployeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(momEmployeeId);
			if (null != mvEmployeeMaster) {
				return true;
			}
		}
		return false;
	}
}
