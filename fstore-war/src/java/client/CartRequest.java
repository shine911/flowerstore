/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entities.OrdersDetails;
import facades.ProductFacadeLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.omnifaces.util.Faces;

/**
 *
 * @author quihuynh
 */
@Named(value = "cartRequest")
@RequestScoped
public class CartRequest {

    @EJB
    private ProductFacadeLocal productFacade;
    
    CartMB cartBean;
    
    private int qty;
    private int proid;
    
    /**
     * Creates a new instance of CartRequest
     */
    public CartRequest() {
    }

    @PostConstruct
    public void init(){
        this.cartBean = Faces.evaluateExpressionGet("#{cartMB}");
    }
    
    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }
    public void editCart(int id){
        
    }
    
    public void placeToCart(int id){
        OrdersDetails ordDetails = new OrdersDetails();
        //Add product
        ordDetails.setProductId(productFacade.find(id));
        ordDetails.setQty(this.qty);
        /*
        * If: orderDetails empty - i will add direct
        * else - I will find product in orderdetails
        * if: null - add direct
        * else - sum qty
        */
        if(!cartBean.getOrdersDetials().isEmpty()){
            OrdersDetails getOrd = cartBean.getOrdersDetials().stream()
                    .filter(prd -> prd.getProductId().getId() == id)
                    .findFirst().get();
            if(getOrd==null){
                cartBean.getOrdersDetials().add(ordDetails);
            } else {
                getOrd.setQty(getOrd.getQty() + this.qty);
            }
        } else {
            cartBean.getOrdersDetials().add(ordDetails);
        }
    }
}
