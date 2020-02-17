package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvTJmci106Master;

@Repository
public interface MvTJmci106MasterRepository extends CrudRepository<MvTJmci106Master, String> {
	public MvTJmci106Master findByBusinessPlaceId(String businessPlaceId);
}
