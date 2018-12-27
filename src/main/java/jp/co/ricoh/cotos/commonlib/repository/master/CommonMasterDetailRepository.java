package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.CommonMasterDetail;

@Repository
public interface CommonMasterDetailRepository extends CrudRepository<CommonMasterDetail, Long> {
	@Query(value = "FROM CommonMasterDetail WHERE common_master_id = :COMMON_MASTER_ID AND trunc(current_date) between trunc(available_period_from) and trunc(available_period_to) AND delete_flg = 0 order by display_order")
	public List<CommonMasterDetail> findByCommonMasterId(@Param("COMMON_MASTER_ID") Long commonMasterId);

	// 受注完了時のメール送信でのみ使用可能
	@Query(value = "FROM CommonMasterDetail WHERE common_master_id = :COMMON_MASTER_ID AND trunc(current_date) between trunc(available_period_from) and trunc(available_period_to) AND data_area_1 = :DATA_AREA_1 AND delete_flg = 0 order by display_order")
	public CommonMasterDetail findByCommonMasterIdAndDataArea1(@Param("COMMON_MASTER_ID") Long commonMasterId, @Param("DATA_AREA_1") String dataArea1);
}
