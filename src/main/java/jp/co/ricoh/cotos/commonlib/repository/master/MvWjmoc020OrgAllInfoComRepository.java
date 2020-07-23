package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvWjmoc020OrgAllInfoCom;

@Repository
public interface MvWjmoc020OrgAllInfoComRepository extends CrudRepository<MvWjmoc020OrgAllInfoCom, String> {
	@Query(value = "FROM MvWjmoc020OrgAllInfoCom WHERE org_id = :ORG_ID AND :BASE_DATE BETWEEN start_date AND end_date")
	public MvWjmoc020OrgAllInfoCom findByOrgId(@Param("ORG_ID") String orgId, @Param("BASE_DATE") Date baseDate);
}
