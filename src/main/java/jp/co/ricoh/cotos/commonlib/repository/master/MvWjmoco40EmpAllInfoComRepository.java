package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvWjmoco40EmpAllInfoCom;

@Repository
public interface MvWjmoco40EmpAllInfoComRepository extends CrudRepository<MvWjmoco40EmpAllInfoCom, String> {

	public List<MvWjmoco40EmpAllInfoCom> findByRingsHanshCdAndRingsEmpCd(String RingsHanshCd, String RingsEmpCd);
}
