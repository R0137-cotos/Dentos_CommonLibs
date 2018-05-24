package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.master.MvTJmci105Master;

@Repository
public interface MvTJmci105Repository extends CrudRepository<MvTJmci105Master, String> {
}