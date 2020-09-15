/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entities.Category;
import facades.CategoryFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author quihuynh
 */
@Named(value = "homeMB")
@SessionScoped
public class HomeMB implements Serializable {

    @EJB
    private CategoryFacadeLocal categoryFacade;
    
    

    /**
     * Creates a new instance of ProductViewMB
     */
    public HomeMB() {
    }

    public List<Category> getCategoryList() {
        return categoryFacade.findAll();
    }
    
    
}
