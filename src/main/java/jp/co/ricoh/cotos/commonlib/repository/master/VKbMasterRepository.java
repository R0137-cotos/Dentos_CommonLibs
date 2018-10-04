package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.VKbMaster;

@Repository
public interface VKbMasterRepository extends CrudRepository<VKbMaster, String> {
	public VKbMaster findByMclMomRelId(String mclMomRelId);

	public List<VKbMaster> findByMclMomKjbIdOrderByMclMomRelId(String mclMomKjbId);
}
