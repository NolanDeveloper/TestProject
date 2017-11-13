package com.nolan.TestProject.Repository;

import com.nolan.TestProject.Domain.OrderLine;
import org.springframework.data.repository.CrudRepository;

public interface OrderLinesRepository extends CrudRepository<OrderLine, Integer> {
    Iterable<OrderLine> findByOrderIdIs(Integer id);
}
