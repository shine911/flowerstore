/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.orders;

import client.ProductDetails;
import entities.Orders;
import entities.OrdersDetails;
import entities.Product;
import facades.OrdersDetailsFacadeLocal;
import facades.OrdersFacadeLocal;
import facades.ProductFacadeLocal;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author nguyenhongtruclam
 */
@Named(value = "ordersDetailsMB")
@SessionScoped
public class OrdersDetailsMB implements Serializable {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private OrdersFacadeLocal ordersFacade;

    @EJB
    private OrdersDetailsFacadeLocal ordersDetailsFacade;

    public OrdersDetailsMB() {
    }
    private int id;
    private int qty;
    private List<OrdersDetails> orderDetailList;
    private Orders ordersId;
    private Product productId;

    public ProductFacadeLocal getProductFacade() {
        return productFacade;
    }

    public void setProductFacade(ProductFacadeLocal productFacade) {
        this.productFacade = productFacade;
    }

    public OrdersFacadeLocal getOrdersFacade() {
        return ordersFacade;
    }

    public void setOrdersFacade(OrdersFacadeLocal ordersFacade) {
        this.ordersFacade = ordersFacade;
    }

    public Orders getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Orders ordersId) {
        this.ordersId = ordersId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public OrdersDetailsFacadeLocal getOrdersDetailsFacade() {
        return ordersDetailsFacade;
    }

    public void setOrdersDetailsFacade(OrdersDetailsFacadeLocal ordersDetailsFacade) {
        this.ordersDetailsFacade = ordersDetailsFacade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public List<OrdersDetails> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrdersDetails> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public List<OrdersDetails> showAllOrdersDetailses(int id) {

        return ordersFacade.find(id).getOrdersDetailsCollection().stream().collect(Collectors.toList());
    }

    public String editOrdersDetails(int idOrders) {
        id = idOrders;
        //Bien orders
        ordersId = ordersDetailsFacade.find(idOrders).getOrdersId();
        productId = ordersDetailsFacade.find(idOrders).getProductId();
        qty = ordersDetailsFacade.find(idOrders).getQty();
        return "edit";
    }

    public void editOrdersDetails() {
        OrdersDetails ordersDetails = ordersId.getOrdersDetailsCollection()
                .stream().filter(x -> x.getId() == id)
                .findFirst()
                .get();

        //ordersDetails.setOrdersId(ordersId);
        //ordersDetails.setProductId(productId);
        //O day khi cap nhat so luong chung ta se cap lai gia
        ordersDetails.setQty(qty);

        List<OrdersDetails> orderList = ordersId.getOrdersDetailsCollection().stream().collect(Collectors.toList());
        double price = 0;
        price = orderList.stream().map(ord -> ord.getQty() * ord.getProductId().getPrice()).reduce(price, (accumulator, _item) -> accumulator + _item);
        //Cap nhat gia
        this.ordersId.setTotalValue(price);
        ordersDetailsFacade.edit(ordersDetails);
        ordersFacade.edit(ordersId);
        this.ordersId = ordersFacade.find(ordersId.getId());
        moveToPage("/admin/order/view");
    }

    public String deleteOrdersDetails(int idOrders) {
        OrdersDetails ordersDetails = ordersDetailsFacade.find(idOrders);
        ordersDetailsFacade.remove(ordersDetails);
        return "view";
    }

    public String addOrderDetails() {
        OrdersDetails ordersDetails = new OrdersDetails();
        ordersDetails.setOrdersId(ordersId);
        ordersDetails.setProductId(productId);
        ordersDetails.setQty(qty);
        ordersDetailsFacade.create(ordersDetails);
        return "invoice";
    }

    public void moveToPage(String page) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            String contextPath = origRequest.getContextPath();
            context.getExternalContext().redirect(contextPath + page);
        } catch (IOException ex) {
            Logger.getLogger(ProductDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
