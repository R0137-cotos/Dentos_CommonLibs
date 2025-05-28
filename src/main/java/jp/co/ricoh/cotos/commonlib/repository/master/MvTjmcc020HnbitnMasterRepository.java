package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvTjmcc020HnbitnMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MvTjmcc020HnbitnMaster.Id;

@Repository
public interface MvTjmcc020HnbitnMasterRepository extends CrudRepository<MvTjmcc020HnbitnMaster, Id> {
	@Query(value = "SELECT * FROM mv_tjmcc020_hnbitn WHERE mom_kgy_id = :MOM_KGY_ID AND hansh_cd = :HANSH_CD AND nendo = :NENDO AND dlt_flg = '0' ", nativeQuery = true)
	public MvTjmcc020HnbitnMaster findByMomKgyIdAndHanshCdAndNendo(@Param("MOM_KGY_ID") String momKgyId, @Param("HANSH_CD") String hanshCd, @Param("NENDO") String nendo);
}
