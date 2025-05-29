package jp.co.ricoh.cotos.commonlib.db;

import java.util.List;
import java.util.NoSuchElementException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.dto.result.ApproverInfo;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteNodeMaster.ApproverDeriveMethodDiv;
import jp.co.ricoh.cotos.commonlib.security.AccessLogOutputFilter;

/**
 * ストアドプロシージャー実行用ユーティリティー<br/>
 * ストアドプロシージャー単位で呼び出しメソッドを作成してください
 */
@Component
public class StoredProcedureUtil {

	private static final Log log = LogFactory.getLog(AccessLogOutputFilter.class);

	@Autowired
	EntityManager entityManager;

	/**
	 * PROC_GET_APPROVER を実行する
	 */
	public ApproverInfo procGetApprover(ApproverDeriveMethodDiv processingType, String momId, String momCorpId, Integer hierarchyLevel) {

		// クエリー準備
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PROC_GET_APPROVER", ApproverInfo.class);

		// パラメーターの型設定
		query.registerStoredProcedureParameter("processing_type_", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("mom_id_", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("mom_corp_id_", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("hierarchy_level_", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("ref_leader_", void.class, ParameterMode.REF_CURSOR);

		// パラメーター値設定
		query.setParameter("processing_type_", processingType.toString());
		query.setParameter("mom_id_", momId);
		query.setParameter("mom_corp_id_", momCorpId);
		query.setParameter("hierarchy_level_", hierarchyLevel);
		log.info("processing_type_:" + processingType.toString());
		log.info("mom_id_:" + momId);
		log.info("mom_corp_id_:" + momCorpId);
		log.info("hierarchy_level_:" + hierarchyLevel);
		query.execute();

		@SuppressWarnings("unchecked")
		List<ApproverInfo> resultList = query.getResultList();

		if (resultList.isEmpty()) {
			throw new NoSuchElementException("No Data Found By Calling StoredProcedure");
		}

		// 結果を１件返却
		return resultList.get(0);
	}
}
