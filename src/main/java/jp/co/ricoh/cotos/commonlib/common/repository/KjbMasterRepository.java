package jp.co.ricoh.cotos.commonlib.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.master.KjbMaster;

@Repository
public interface KjbMasterRepository extends CrudRepository<KjbMaster, String> {
	public KjbMaster findByMclMomRelId(String mclMomRelId);

	public List<KjbMaster> findByMclMomKjbIdOrderByMclMomRelId(String mclMomKjbId);
}
