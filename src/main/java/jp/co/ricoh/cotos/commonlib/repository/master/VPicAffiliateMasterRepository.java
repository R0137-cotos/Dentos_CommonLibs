package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.VPicAffiliateMaster;

@Repository
public interface VPicAffiliateMasterRepository extends CrudRepository<VPicAffiliateMaster, String> {
	public List<VPicAffiliateMaster> findAllByOrderByOrgHierarchyLevelAscOrgIdAsc();
}