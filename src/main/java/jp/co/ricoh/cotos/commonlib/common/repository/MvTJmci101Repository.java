package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.master.MvTJmci101Master;

@Repository
public interface MvTJmci101Repository extends CrudRepository<MvTJmci101Master, String> {
	public MvTJmci101Master findByOriginalSystemCode(String originalSystemCode);
}