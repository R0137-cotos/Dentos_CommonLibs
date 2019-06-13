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

	@Query(value = "SELECT * FROM common_master_detail WHERE common_master_id = (select id from common_master where column_name = :COLUMN_NAME and rownum <= 1) AND to_date(:AVAILABLE_PERIOD, 'yyyymmdd') between trunc(available_period_from) and trunc(available_period_to) AND delete_flg = 0 and rownum <= 1", nativeQuery = true)
	public CommonMasterDetail findByCommonMasterColumnNameAndAvailablePeriodBetween(@Param("COLUMN_NAME") String columnName, @Param("AVAILABLE_PERIOD") String availablePeriod);

	// 受注完了時のメール送信でのみ使用可能
	@Query(value = "FROM CommonMasterDetail WHERE COMMON_MASTER_ID = :COMMON_MASTER_ID AND TRUNC(CURRENT_DATE) BETWEEN TRUNC(AVAILABLE_PERIOD_FROM) AND TRUNC(AVAILABLE_PERIOD_TO) AND DATA_AREA_1 = :DATA_AREA_1 AND DELETE_FLG = 0")
	public CommonMasterDetail findByCommonMasterIdAndDataArea1(@Param("COMMON_MASTER_ID") Long commonMasterId, @Param("DATA_AREA_1") String dataArea1);
}
