/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.orders;

import entities.Orders;
import entities.Promo;
import entities.Userinfo;
import facades.OrdersFacadeLocal;
import facades.ProductFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author nguyenhongtruclam
 */
@Named(value = "ordersMb")
@SessionScoped
public class OrdersMb implements Serializable {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private OrdersFacadeLocal ordersFacade;
    private int id;
    private int ordersState;
    private Double discountValue;
    private Double totalValue;
    private Promo promoId;
    private Userinfo cusId;
    private List<Orders> listOrder;

    public OrdersMb() {
    }

    public OrdersFacadeLocal getOrdersFacade() {
        return ordersFacade;
    }

    public void setOrdersFacade(OrdersFacadeLocal ordersFacade) {
        this.ordersFacade = ordersFacade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdersState() {
        return ordersState;
    }

    public void setOrdersState(Integer ordersState) {
        this.ordersState = ordersState;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Promo getPromoId() {
        return promoId;
    }

    public void setPromoId(Promo promoId) {
        this.promoId = promoId;
    }

    public Userinfo getCusId() {
        return cusId;
    }

    public void setCusId(Userinfo cusId) {
        this.cusId = cusId;
    }

    public List<Orders> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<Orders> listOrder) {
        this.listOrder = listOrder;
    }

    public List<Orders> showOrderses() {
        List<Orders> list = new ArrayList<Orders>();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int get = 0;
        if (req.getParameter("state") != null) {
            get = Integer.parseInt(req.getParameter("state"));

        }
        int state = get;
        list = ordersFacade.findAll().stream()
                .filter(p -> p.getOrdersState() == state)
                .collect(Collectors.toList());
        //if (list == null) {
        //    list = ordersFacade.findAll().stream()
        //            .filter(p -> p.getOrdersState() == 0)
        //            .collect(Collectors.toList());
        //}
        return list;
    }

    public String editOrder(int idOrders) {
        id = idOrders;
        ordersState = ordersFacade.find(idOrders).getOrdersState();
        promoId = ordersFacade.find(idOrders).getPromoId();
        cusId = ordersFacade.find(idOrders).getCusId();
        discountValue = ordersFacade.find(idOrders).getDiscountValue();
        totalValue = ordersFacade.find(idOrders).getTotalValue();
        return "editOrders";
    }

    public String editOrder() {
        Orders orders = ordersFacade.find(id);

        orders.setOrdersState(ordersState);
        orders.setDiscountValue(discountValue);

        ordersFacade.edit(orders);
        return "index";
    }

    public String editOrder1(int idOrders) {
        id = idOrders;
        ordersState = ordersFacade.find(idOrders).getOrdersState();
        promoId = ordersFacade.find(idOrders).getPromoId();
        cusId = ordersFacade.find(idOrders).getCusId();
        discountValue = ordersFacade.find(idOrders).getDiscountValue();
        totalValue = ordersFacade.find(idOrders).getTotalValue();
        return "editOrders";
    }

    public String editOrder1() {
        Orders orders = ordersFacade.find(id);
        int stt = ordersState = 1;
        orders.setOrdersState(stt);
        ordersFacade.edit(orders);
        return "index";
    }

    public String editOrder2(int idOrders) {
        id = idOrders;
        ordersState = ordersFacade.find(idOrders).getOrdersState();
        promoId = ordersFacade.find(idOrders).getPromoId();
        cusId = ordersFacade.find(idOrders).getCusId();
        discountValue = ordersFacade.find(idOrders).getDiscountValue();
        totalValue = ordersFacade.find(idOrders).getTotalValue();
        return "editOrders";
    }

    public String editOrder2() {
        Orders orders = ordersFacade.find(id);
        int stt = ordersState = 2;
        orders.setOrdersState(stt);
        ordersFacade.edit(orders);
        return "index";
    }

    public String editOrder3(int idOrders) {
        id = idOrders;
        ordersState = ordersFacade.find(idOrders).getOrdersState();
        promoId = ordersFacade.find(idOrders).getPromoId();
        cusId = ordersFacade.find(idOrders).getCusId();
        discountValue = ordersFacade.find(idOrders).getDiscountValue();
        totalValue = ordersFacade.find(idOrders).getTotalValue();
        return "editOrders";
    }

    public String editOrder3() {
        Orders orders = ordersFacade.find(id);
        int stt = ordersState = 3;
        orders.setOrdersState(stt);
        ordersFacade.edit(orders);
        return "index";
    }

    public String editOrder4(int idOrders) {
        id = idOrders;
        ordersState = ordersFacade.find(idOrders).getOrdersState();
        promoId = ordersFacade.find(idOrders).getPromoId();
        cusId = ordersFacade.find(idOrders).getCusId();
        discountValue = ordersFacade.find(idOrders).getDiscountValue();
        totalValue = ordersFacade.find(idOrders).getTotalValue();
        return "editOrders";
    }

    public String editOrder4() {
        Orders orders = ordersFacade.find(id);
        int stt = ordersState = 0;
        orders.setOrdersState(stt);
        ordersFacade.edit(orders);
        return "index";
    }

    public String deleteOrders(int idOrders) {
        Orders orders = ordersFacade.find(idOrders);
        ordersFacade.remove(orders);
        return "view";
    }

    public String viewOrders(int idOrders) {
        id = idOrders;
        ordersState = ordersFacade.find(idOrders).getOrdersState();
        cusId = ordersFacade.find(idOrders).getCusId();
        promoId = ordersFacade.find(idOrders).getPromoId();
        discountValue = ordersFacade.find(idOrders).getDiscountValue();
        totalValue = ordersFacade.find(idOrders).getTotalValue();

        return "view";
    }

    public String showOrders(int stt) {
        ordersState = stt = 1;
        id = ordersFacade.find(stt).getId();
        cusId = ordersFacade.find(stt).getCusId();
        promoId = ordersFacade.find(stt).getPromoId();
        discountValue = ordersFacade.find(stt).getDiscountValue();
        totalValue = ordersFacade.find(stt).getTotalValue();
        return "index";
    }

    public void findByTotalValue() {
        listOrder = ordersFacade.findByTotalValue(totalValue);
    }
}
