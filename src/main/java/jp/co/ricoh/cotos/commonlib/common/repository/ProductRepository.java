package jp.co.ricoh.cotos.commonlib.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.master.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	public List<Product> findAllByOrderByIdAsc();
}