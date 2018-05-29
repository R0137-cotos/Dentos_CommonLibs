package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	public List<Product> findAllByOrderByIdAsc();
}