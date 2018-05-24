package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.entity.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
}