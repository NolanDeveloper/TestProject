package com.nolan.TestProject.Controller;

import com.nolan.TestProject.Domain.Goods;
import com.nolan.TestProject.Domain.Order;
import com.nolan.TestProject.Domain.OrderLine;
import com.nolan.TestProject.Service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WebController {

    private MarketService service;

    @Autowired
    public void setService(MarketService service) {
        this.service = service;
    }

    @RequestMapping("/goods")
    public String goods(Model model) {
        model.addAttribute("goods", service.getAllGoods());
        model.addAttribute("newGoods", new Goods());
        return "goods";
    }

    @RequestMapping(value = "/goods/add", method = RequestMethod.POST)
    public String goodsAdd(Goods newGoods) {
        service.addGoods(newGoods);
        return "redirect:/goods";
    }

    @RequestMapping("/goods/remove/{id}")
    public String goodsRemove(@PathVariable Integer id) {
        service.deleteGoods(id);
        return "redirect:/goods";
    }

    @RequestMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", service.getAllOrders());
        return "orders";
    }

    @RequestMapping("/orders/new")
    public String ordersNew(Model model) {
        model.addAttribute("goods", service.getAllGoods());
        return "orderChooseGoods";
    }

    private ArrayList<OrderLine> constructOrderLines(int[] goodsIds, int[] counts) {
        if (goodsIds.length != counts.length) {
            throw new AssertionError("goodsIds and counts have different lengths");
        }
        Iterable<Goods> allGoods = service.getAllGoods();
        Map<Integer, Integer> goodsToCounts = new HashMap<>(goodsIds.length);
        for (int i = 0; i < goodsIds.length; ++i) {
            if (0 == counts[i]) continue;
            goodsToCounts.put(goodsIds[i], counts[i]);
        }
        ArrayList<OrderLine> orderLines = new ArrayList<>();
        for (Goods goods : allGoods) {
            Integer count = goodsToCounts.get(goods.getId());
            if (null == count) continue;
            OrderLine orderLine = new OrderLine();
            orderLine.setGoods(goods);
            orderLine.setCount(count);
            orderLines.add(orderLine);
        }
        return orderLines;
    }

    @RequestMapping(value = "/orders/new/create", method = RequestMethod.POST)
    public String ordersNewCreate(@RequestParam int[] goodsIds,
                                  @RequestParam int[] counts,
                                  Model model) {
        model.addAttribute("orderLines", constructOrderLines(goodsIds, counts));
        model.addAttribute("date", LocalDate.now());
        return "orderIssue";
    }

    @RequestMapping(value = "/orders/new/issue", method = RequestMethod.POST)
    public String orderNewIssue(@RequestParam String client,
                                @RequestParam String address,
                                @RequestParam int[] goodsIds,
                                @RequestParam int[] counts,
                                Model model) {
        if (goodsIds.length != counts.length) {
            throw new AssertionError("goodsIds and counts have different lengths");
        }
        Order order = new Order();
        order.setClient(client);
        order.setAddress(address);
        order.setDate(LocalDate.now());
        order = service.save(order);
        ArrayList<OrderLine> orderLines = constructOrderLines(goodsIds, counts);
        for (OrderLine orderLine : orderLines) {
            orderLine.setOrder(order);
        }
        service.save(orderLines);
        return "redirect:/orders";
    }


    @RequestMapping("/orders/details/{id}")
    public String ordersDetails(@PathVariable Integer id, Model model) {
        model.addAttribute("order", service.getOrder(id));
        model.addAttribute("orderLines", service.getLinesOfOrder(id));
        return "ordersDetails";
    }

    @RequestMapping("/orders/remove/{id}")
    public String ordersRemove(@PathVariable Integer id) {
        service.deleteOrder(id);
        return "redirect:/orders";
    }

    @RequestMapping("")
    public String index() {
        return "redirect:/orders";
    }
}