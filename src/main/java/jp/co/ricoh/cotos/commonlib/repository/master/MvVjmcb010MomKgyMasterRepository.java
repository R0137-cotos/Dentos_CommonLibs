package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvVjmcb010MomKgyMaster;

@Repository
public interface MvVjmcb010MomKgyMasterRepository extends CrudRepository<MvVjmcb010MomKgyMaster, String> {
	public MvVjmcb010MomKgyMaster findByMomKishId(String momKishId);
	
	public MvVjmcb010MomKgyMaster findByMomKishIdAndCuicMcdbDltFlg(String momKishId, String cuicMcdbDltFlg);
}
