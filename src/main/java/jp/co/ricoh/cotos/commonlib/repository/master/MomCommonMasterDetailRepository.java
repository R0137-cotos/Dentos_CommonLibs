package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MomCommonMasterDetail;
import jp.co.ricoh.cotos.commonlib.entity.master.MomCommonMasterDetail.Id;

@Repository
public interface MomCommonMasterDetailRepository extends CrudRepository<MomCommonMasterDetail, Id> {
	@Query(value = "FROM MomCommonMasterDetail WHERE item_id = :ITEM_ID AND del_flg = '0' order by sort_number")
	public List<MomCommonMasterDetail> findByItemId(@Param("ITEM_ID") String itemId);
}
