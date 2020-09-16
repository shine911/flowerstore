/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entities.Images;
import entities.Orders;
import entities.OrdersDetails;
import entities.Promo;
import facades.ImagesFacadeLocal;
import facades.OrdersDetailsFacadeLocal;
import facades.OrdersFacadeLocal;
import facades.PromoFacadeLocal;
import helper.UtilsHelper;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Faces;

/**
 *
 * @author quihuynh
 */
@Named(value = "cartMB")
@SessionScoped
public class CartMB implements Serializable {

    @EJB
    private OrdersDetailsFacadeLocal ordersDetailsFacade;

    @EJB
    private OrdersFacadeLocal ordersFacade;

    @EJB
    private PromoFacadeLocal promoFacade;

    UserManager userManager;

    private List<OrdersDetails> ordersDetials;
    private Orders orders;
    private String promo_code;
    private Promo promoObj;

    /**
     * Creates a new instance of CartMB
     */
    public CartMB() {
    }

    @PostConstruct
    public void init() {
        this.ordersDetials = new ArrayList<>();
        this.userManager = Faces.evaluateExpressionGet("#{userManager}");

    }

    public List<OrdersDetails> getOrdersDetials() {
        return ordersDetials;
    }

    public void setOrdersDetials(List<OrdersDetails> ordersDetials) {
        this.ordersDetials = ordersDetials;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public Double getSubTotal() {
        double subtotal = 0;
        subtotal = this.ordersDetials.stream().map(ord -> ord.getQty() * ord.getProductId().getPrice()).reduce(subtotal, (accumulator, _item) -> accumulator + _item);
        return subtotal;
    }

    public void checkPromo() {
        try {
            Promo searchPromo = this.promoFacade.findByCode(this.promo_code);
            if (searchPromo != null) {
                this.promoObj = searchPromo;
            }
        } catch (EJBException ex) {
            this.promoObj = null;
        }
    }

    public Promo getPromoObj() {
        return promoObj;
    }

    public void setPromoObj(Promo promoObj) {
        this.promoObj = promoObj;
    }

    public Double getDiscount() {
        if (promoObj != null) {
            return promoObj.getDiscountType() == 1 ? (promoObj.getDiscountValue() / 100 * getSubTotal()) : promoObj.getDiscountValue();
        }
        return 0.0;
    }

    public void subQtyToProduct(int product) {
        entities.OrdersDetails getProduct = this.ordersDetials.stream()
                .filter(x -> x.getProductId().getId() == product).findFirst()
                .get();
        if (getProduct.getQty() <= 0) {
            this.ordersDetials.remove(getProduct);
            Ajax.oncomplete("window.location.reload()");
        } else {
            getProduct.setQty(getProduct.getQty() - 1);
        }
    }

    public void addQtyToProduct(int product) {
        entities.OrdersDetails getProduct = this.ordersDetials.stream()
                .filter(x -> x.getProductId().getId() == product).findFirst()
                .get();
        getProduct.setQty(getProduct.getQty() + 1);
    }

    private void moveToCart() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            String contextPath = origRequest.getContextPath();
            context.getExternalContext().redirect(contextPath + "/cart");
        } catch (IOException ex) {
            Logger.getLogger(ProductDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void checkOut() {
        Orders orders_new = new Orders();
        orders_new.setCusId(userManager.user);
        //orders_new.setOrdersDetailsCollection(this.ordersDetials);
        orders_new.setTotalValue(this.getSubTotal());
        orders_new.setDiscountValue(this.getDiscount());
        orders_new.setPromoId(promoObj);
        orders_new.setOrdersState(0);
        ordersFacade.create(orders_new);
        //System.out.println(orders_new.getId());
        ordersDetials.stream().map(ordDetails -> {
            ordDetails.setOrdersId(orders_new);
            return ordDetails;
        }).forEachOrdered(ordDetails -> {
            ordersDetailsFacade.create(ordDetails);
        });
        UtilsHelper helper = new UtilsHelper();
        this.ordersDetials = new ArrayList<>();
        this.promoObj = null;
        this.promo_code = "";
        helper.moveToPage("/myaccount");
    }
}
