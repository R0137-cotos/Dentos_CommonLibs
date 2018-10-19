package jp.co.ricoh.cotos.commonlib.repository.arrangement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRouteNode;

@Repository
public interface ArrangementWorkApprovalRouteNodeRepository extends CrudRepository<ArrangementWorkApprovalRouteNode, Long> {

}
