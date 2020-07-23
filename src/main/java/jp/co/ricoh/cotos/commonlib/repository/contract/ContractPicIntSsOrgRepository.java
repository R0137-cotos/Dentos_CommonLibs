package jp.co.ricoh.cotos.commonlib.repository.contract;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.ContractPicIntSsOrg;

@Repository
public interface ContractPicIntSsOrgRepository extends CrudRepository<ContractPicIntSsOrg, Long> {

}
