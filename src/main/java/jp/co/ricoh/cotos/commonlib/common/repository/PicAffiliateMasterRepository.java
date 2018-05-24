package jp.co.ricoh.cotos.commonlib.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.master.PicAffiliateMaster;

@Repository
public interface PicAffiliateMasterRepository extends CrudRepository<PicAffiliateMaster, String> {
	public List<PicAffiliateMaster> findAllByOrderByOrgHierarchyLevelAscOrgIdAsc();
}