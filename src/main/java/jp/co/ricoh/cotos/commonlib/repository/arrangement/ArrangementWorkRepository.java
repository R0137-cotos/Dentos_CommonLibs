package jp.co.ricoh.cotos.commonlib.repository.arrangement;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;

@Repository
public interface ArrangementWorkRepository extends CrudRepository<ArrangementWork, Long> {
	public ArrangementWork findByIdAndAppId(Long id, String appId);

	public ArrangementWork findByIdAndAppIdNotIn(Long id, List<String> appId);
}
