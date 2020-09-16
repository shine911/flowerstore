/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entities.Userinfo;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author quihuynh
 */
@Named(value = "userManager")
@SessionScoped
public class UserManager implements Serializable {
    
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
}
