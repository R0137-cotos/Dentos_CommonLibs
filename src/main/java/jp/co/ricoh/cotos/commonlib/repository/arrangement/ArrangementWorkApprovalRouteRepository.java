package jp.co.ricoh.cotos.commonlib.repository.arrangement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRoute;

@Repository
public interface ArrangementWorkApprovalRouteRepository extends CrudRepository<ArrangementWorkApprovalRoute, Long> {
	public ArrangementWorkApprovalRoute findByArrangementWorkId(Long arrangementWorkId);
}
