package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvTjmmb010UtlItem;

@Repository
public interface MvTjmmb010UtlItemRepository extends CrudRepository<MvTjmmb010UtlItem, String> {
	@Query(value = "FROM MvTjmmb010UtlItem WHERE item_id = :ITEM_ID AND del_flg = '0' order by item_id")
	public MvTjmmb010UtlItem findByItemId(@Param("ITEM_ID") String itemId);
}
