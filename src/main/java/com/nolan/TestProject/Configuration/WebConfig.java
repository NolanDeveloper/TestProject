package com.nolan.TestProject.Configuration;

import com.nolan.TestProject.Domain.Goods;
import com.nolan.TestProject.Domain.Order;
import com.nolan.TestProject.Domain.OrderLine;
import com.nolan.TestProject.Service.MarketService;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.time.LocalDate;

@Configuration
public class WebConfig implements ApplicationListener<ContextRefreshedEvent> {

    private MarketService service;

    @Autowired
    public void setService(MarketService service) {
        this.service = service;
    }

    private Goods addGoods(String name, int price) {
        Goods goods = new Goods();
        goods.setName(name);
        goods.setPrice(price);
        return service.save(goods);
    }

    private Order addOrder(String client, LocalDate date, String address) {
        Order order = new Order();
        order.setClient(client);
        order.setDate(date);
        order.setAddress(address);
        return service.save(order);
    }

    private OrderLine addOrderLine(Order order, Goods goods, int count) {
        OrderLine orderLine = new OrderLine();
        orderLine.setOrder(order);
        orderLine.setGoods(goods);
        orderLine.setCount(count);
        return service.save(orderLine);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Goods pen = addGoods("Ручка", 10);
        Goods pencil = addGoods("Карандаш", 3);
        Goods colors = addGoods("Краски", 20);
        Order school = addOrder("МБОУСОШ №107", LocalDate.now().minusWeeks(1), "пр. Королёва, д. 16");
        Order market = addOrder("Магазин канцтоваров \"Отличник\"",
                LocalDate.now().minusDays(3), "пр. Космонавтов, д. 2");
        addOrderLine(school, pencil, 200);
        addOrderLine(school, pen, 200);
        addOrderLine(market, pencil, 2000);
        addOrderLine(market, colors, 1530);
    }

    @Bean
    public ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}
