package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;

@Repository
public interface VKjbMasterRepository extends CrudRepository<VKjbMaster, String> {
	public VKjbMaster findByMclMomRelId(String mclMomRelId);

	public List<VKjbMaster> findByMclMomKjbIdOrderByMclMomRelId(String mclMomKjbId);

	@Query(value = "select" + //
			"  * " + //
			"from" + //
			"  v_kjb_master kjb " + //
			"  inner join ( " + //
			"    select" + //
			"      kjb2.mcl_mom_rel_id as kjb2_mcl_mom_rel_id" + //
			"      , max(mcl_updt_dt) " + //
			"    from" + //
			"      v_kjb_master kjb2 " + //
			"    where" + //
			"      mcl_mom_kjb_id = :mclMomKjbId " + //
			"      AND mcl_dlt_flg = :mclDltFlg " + //
			"    group by" + //
			"      mcl_mom_rel_id" + //
			"  ) kjb2 " + //
			"    on kjb.mcl_mom_rel_id = kjb2_mcl_mom_rel_id ", nativeQuery = true)
	public List<VKjbMaster> findByMclMomKjbIdAndMclDltFlgAndMaxMclUpdateDt(@Param("mclMomKjbId") String mclMomKjbId, @Param("mclDltFlg") String mclDltFlg);
}
