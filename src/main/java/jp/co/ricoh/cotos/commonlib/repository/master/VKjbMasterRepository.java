package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;

@Repository
public interface VKjbMasterRepository extends CrudRepository<VKjbMaster, String> {
	public VKjbMaster findByMclMomRelId(String mclMomRelId);

	public List<VKjbMaster> findByMclMomKjbIdOrderByMclMomRelId(String mclMomKjbId);
}
