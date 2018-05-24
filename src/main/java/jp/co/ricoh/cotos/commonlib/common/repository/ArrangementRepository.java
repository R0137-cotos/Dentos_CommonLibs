package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.entity.Arrangement;

@Repository
public interface ArrangementRepository extends CrudRepository<Arrangement, Long> {

}
