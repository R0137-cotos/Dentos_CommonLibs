package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvTjmmb020UtlCd;
import jp.co.ricoh.cotos.commonlib.entity.master.MvTjmmb020UtlCd.Id;

@Repository
public interface MvTjmmb020UtlCdRepository extends CrudRepository<MvTjmmb020UtlCd, Id> {
	@Query(value = "FROM MvTjmmb020UtlCd WHERE Id.itemId = :ITEM_ID AND delFlg = '0' order by sortNumber")
	public List<MvTjmmb020UtlCd> findByItemId(@Param("ITEM_ID") String itemId);
}
