package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.CommonMaster;

@Repository
public interface CommonMasterRepository extends CrudRepository<CommonMaster, Long> {
	@Query(value = "FROM CommonMaster WHERE article_cd = :ARTICLE_CD AND delete_flg = '0'")
	public CommonMaster findByArticleCd(@Param("ARTICLE_CD") String articleCd);
}
