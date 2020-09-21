/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entities.Category;
import entities.Product;
import facades.CategoryFacadeLocal;
import facades.ProductFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.omnifaces.cdi.Param;

/**
 *
 * @author quihuynh
 */
@Named(value = "shopMB")
@ViewScoped
public class ShopMB implements Serializable {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private CategoryFacadeLocal categoryFacade;
    private String search;
    @Inject
    @Param(pathIndex = 0)
    private String categoryid;

    private List<Category> categoryList;
    private List<Product> productList;

    /**
     * Creates a new instance of ShopMB
     */
    public ShopMB() {
    }

    public List<Category> getCategoryList() {
        this.categoryList = categoryFacade.findAll();
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String selectCategoryId() {
        return "shop";
    }

    public void goSearch() {
        this.moveToPage("/search/"+this.search);
    }

    public List<Product> getSearchProductList() {
        List<Product> result = productFacade.findAll().stream()
                .filter(x -> x.getName().contains(categoryid))
                .collect(Collectors.toList());
        return result == null ? new ArrayList<>() : result;
    }

    public List<Product> getProductList() {
        /**
         * FacesContext context = FacesContext.getCurrentInstance();
         * Map<String, String> paramMap =
         * context.getExternalContext().getRequestParameterMap(); String
         * projectId = paramMap.getOrDefault("CategoryId", "0");
         */

        if (this.categoryid == null) {
            this.categoryid = "0";
        }
        int num = Integer.parseInt(this.categoryid);
        //Find category id by user input
        Category cate = this.getCategoryList().stream()
                .filter(c -> c.getId() == num).findFirst().get();
        //Collection to List
        this.productList = cate.getProductCollection().stream().collect(toList());
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

}
