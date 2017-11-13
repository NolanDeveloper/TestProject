package com.nolan.TestProject.Service;

import com.nolan.TestProject.Domain.Goods;
import com.nolan.TestProject.Domain.Order;
import com.nolan.TestProject.Domain.OrderLine;
import com.nolan.TestProject.Repository.GoodsRepository;
import com.nolan.TestProject.Repository.OrderLinesRepository;
import com.nolan.TestProject.Repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketService {

    private GoodsRepository goodsRepository;
    private OrderLinesRepository orderLinesRepository;
    private OrdersRepository ordersRepository;

    @Autowired
    public void setGoodsRepository(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Autowired
    public void setOrderLinesRepository(OrderLinesRepository orderLinesRepository) {
        this.orderLinesRepository = orderLinesRepository;
    }

    @Autowired
    public void setOrdersRepository(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }


    public Iterable<Goods> getAllGoods() {
        return goodsRepository.findAll();
    }

    public Iterable<Order> getAllOrders() {
        return ordersRepository.findAll();
    }

    public void deleteGoods(Integer id) {
        goodsRepository.delete(id);
    }

    public Goods save(Goods goods) {
        return goodsRepository.save(goods);
    }

    public Order save(Order order) {
        return ordersRepository.save(order);
    }

    public void deleteOrder(Integer id) {
        ordersRepository.delete(id);
    }

    public void addGoods(Goods newGoods) {
        goodsRepository.save(newGoods);
    }

    public Order getOrder(Integer id) {
        return ordersRepository.findOne(id);
    }

    public Iterable<OrderLine> getLinesOfOrder(Integer id) {
        return orderLinesRepository.findByOrderIdIs(id);
    }

    public Iterable<OrderLine> save(Iterable<OrderLine> orderLines) {
        return orderLinesRepository.save(orderLines);
    }

    public OrderLine save(OrderLine orderLine) {
        return orderLinesRepository.save(orderLine);
    }
}
