package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.KjbMaster;

@Repository
public interface KjbMasterRepository extends CrudRepository<KjbMaster, String> {
	public KjbMaster findByMclMomRelId(String mclMomRelId);

	public List<KjbMaster> findByMclMomKjbIdOrderByMclMomRelId(String mclMomKjbId);
}
