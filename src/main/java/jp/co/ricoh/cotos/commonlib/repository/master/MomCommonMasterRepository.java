package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MomCommonMaster;

@Repository
public interface MomCommonMasterRepository extends CrudRepository<MomCommonMaster, String> {
	@Query(value = "FROM MomCommonMaster WHERE item_id = :ITEM_ID AND del_flg = '0'")
	public MomCommonMaster findByItemId(@Param("ITEM_ID") String itemId);
}
