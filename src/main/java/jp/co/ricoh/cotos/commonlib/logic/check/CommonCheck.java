package jp.co.ricoh.cotos.commonlib.logic.check;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.util.SearchPropaties;

@Component
public class CommonCheck {

	@Autowired
	DBUtil dbUtil;
	@Autowired
	CheckUtil checkUtil;
	@Autowired
	SearchPropaties searchPropaties;

	@Transactional
	// 検索上限チェック
	public void LimitSizeCheck(Map<String, Object> queryPrams, String path) {

		// application.ymlより取得
		int limitSize = searchPropaties.getLimitSize();

		if (dbUtil.loadCountFromSQLFile(path, queryPrams) >= limitSize) {

			List<ErrorInfo> errorInfoList = new ArrayList<>();

			// Message.propatiesにlimitSizeを代入
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "LimitOverSearchResult", new String[] { String.valueOf(limitSize) });
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "LimitOverSearchResult"));
		}
	}
}
