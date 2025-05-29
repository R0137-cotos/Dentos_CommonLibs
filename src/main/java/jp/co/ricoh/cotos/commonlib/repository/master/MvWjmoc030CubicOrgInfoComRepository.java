package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvWjmoc030CubicOrgInfoCom;
import jp.co.ricoh.cotos.commonlib.entity.master.MvWjmoc030CubicOrgInfoCom.Id;

@Repository
public interface MvWjmoc030CubicOrgInfoComRepository extends CrudRepository<MvWjmoc030CubicOrgInfoCom, Id> {

	@Query(value = "SELECT * FROM mv_wjmoc030_cubic_org_info_com WHERE CUBIC_CORP_ID = :CUBIC_CORP_ID AND CUBIC_ORG_ID = :CUBIC_ORG_ID", nativeQuery = true)
	public MvWjmoc030CubicOrgInfoCom findByCubicCorpIdAndCubicOrgId(@Param("CUBIC_CORP_ID") String cubicCorpId, @Param("CUBIC_ORG_ID") String cubicOrgId);
}
