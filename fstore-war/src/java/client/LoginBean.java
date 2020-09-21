/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entities.Userinfo;
import facades.UserinfoFacadeLocal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import helper.UtilsHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.omnifaces.util.Faces;

/**
 *
 * @author quihuynh
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean {

    @EJB
    private UserinfoFacadeLocal usersFacade;

    private String username;
    private String password;

    private UserManager userManager;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    @PostConstruct
    public void init() {
        this.userManager = Faces.evaluateExpressionGet("#{userManager}");
    }

    public void login() {
        try {
            Userinfo user = usersFacade.findByUsername(this.username);
            UtilsHelper helper = new UtilsHelper();
            if (user.getPassword().equals(helper.md5Hash(this.password))) {
                this.userManager.user = user;
                this.moveToPage("/");
            }
        } catch (EJBException ex) {
            FacesMessage msg = new FacesMessage("login failed", "Username or password not correct!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        //this.moveToPage("/login");
    }

    public void logout() {
        this.userManager.user = null;
    }

    private void moveToPage(String page) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            String contextPath = origRequest.getContextPath();
            context.getExternalContext().redirect(contextPath + page);
        } catch (IOException ex) {
            Logger.getLogger(ProductDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
