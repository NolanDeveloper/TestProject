package com.nolan.TestProject.Repository;

import com.nolan.TestProject.Domain.Goods;
import org.springframework.data.repository.CrudRepository;

public interface GoodsRepository extends CrudRepository<Goods, Integer> {
}
