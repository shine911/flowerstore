/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entities.Users;
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
    Users user;
    
    public UserManager() {
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    
    public void logout(){
        this.user = null;
    }
}
