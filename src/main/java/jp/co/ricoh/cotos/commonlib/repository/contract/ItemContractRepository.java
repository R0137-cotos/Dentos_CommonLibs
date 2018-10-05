package jp.co.ricoh.cotos.commonlib.repository.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.ItemContract;

@Repository
public interface ItemContractRepository extends CrudRepository<ItemContract, Long>, JpaRepository<ItemContract, Long> {
}