/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entities.Orders;
import entities.OrdersDetails;
import entities.Userinfo;
import facades.OrdersDetailsFacadeLocal;
import facades.OrdersFacadeLocal;
import facades.UserinfoFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;

/**
 *
 * @author quihuynh
 */
@Named(value = "userManager")
@SessionScoped
public class UserManager implements Serializable {

    @EJB
    private OrdersDetailsFacadeLocal ordersDetailsFacade;

    @EJB
    private OrdersFacadeLocal ordersFacade;

    @EJB
    private UserinfoFacadeLocal userinfoFacade;
    
    /**
     * Creates a new instance of UserManager
     */
    Userinfo user;
    
    public UserManager() {
    }

    public Userinfo getUser() {
        return user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }
    
    public void logout(){
        this.user = null;
    }
    public List<Orders> getUserOrders(){
        return ordersFacade.findByUser(this.user);
    }
    
    public Orders ordersDetailsList(int id){
        Orders getCurrent = ordersFacade.findByUser(this.user).stream()
                .filter(pre->pre.getId() == id)
                .findFirst()
                .get();
        getCurrent.setOrdersDetailsCollection(
                ordersDetailsFacade.findAll()
                .stream()
                .filter(pre -> pre.getOrdersId().equals(getCurrent))
                .collect(Collectors.toList())
        );
        return getCurrent;
    }
    
    public void refresh(){
        this.user = userinfoFacade.find(this.user.getId());
    }
}
