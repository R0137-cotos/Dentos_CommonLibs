package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvTJmci101Master;

@Repository
public interface MvTJmci101MasterRepository extends CrudRepository<MvTJmci101Master, String> {
}
