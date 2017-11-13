package com.nolan.TestProject.Repository;

import com.nolan.TestProject.Domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Order, Integer> {
}
