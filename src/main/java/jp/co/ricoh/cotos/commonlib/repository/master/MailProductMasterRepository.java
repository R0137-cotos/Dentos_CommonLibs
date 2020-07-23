package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MailProductMaster;

@Repository
public interface MailProductMasterRepository extends JpaRepository <MailProductMaster, Long>{

}
