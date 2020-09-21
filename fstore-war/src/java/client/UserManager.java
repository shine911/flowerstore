/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entities.Orders;
import entities.Userinfo;
import facades.OrdersDetailsFacadeLocal;
import facades.OrdersFacadeLocal;
import facades.UserinfoFacadeLocal;
import helper.UtilsHelper;
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

    public void logout() {
        this.user = null;
    }

    public List<Orders> getUserOrders() {
        List<Orders> getList = ordersFacade.findByUser(this.user);
        //Sorting
        getList.sort((Orders o1, Orders o2) -> {
            return o1.getId().compareTo(o2.getId());
        });
        return getList;
    }

    public Orders ordersDetailsList(int id) {
        Orders getCurrent = ordersFacade.findByUser(this.user).stream()
                .filter(pre -> pre.getId() == id)
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

    public void refresh() {
        this.user = userinfoFacade.find(this.user.getId());
        UtilsHelper helper = new UtilsHelper();
        moveToPage("/");
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
