package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.PicAffiliateMaster;

@Repository
public interface PicAffiliateMasterRepository extends CrudRepository<PicAffiliateMaster, String> {
	public List<PicAffiliateMaster> findAllByOrderByOrgHierarchyLevelAscOrgIdAsc();
}