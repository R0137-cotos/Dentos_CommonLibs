package jp.co.ricoh.cotos.commonlib.repository.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.ItemCon;

@Repository
public interface ItemConRepository extends CrudRepository<ItemCon, Long>, JpaRepository<ItemCon, Long> {
}