/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entities.Images;
import entities.Product;
import facades.ImagesFacadeLocal;
import facades.ProductFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Named(value = "productDetails")
@ViewScoped
public class ProductDetails implements Serializable {

    @EJB
    private ProductFacadeLocal productFacade;

    @Inject
    @Param(pathIndex = 0, required = true, requiredMessage = "Not found product")
    private String productId;

    private Product product;
    private List<Images> imageList;

    /**
     * Creates a new instance of ProductDetails
     */
    public ProductDetails() {
    }

    public Product getProduct() {

        //If not found product then throw exception to redirect to shop
        try {
            this.product = productFacade.find(Integer.parseInt(productId));
            if (this.product == null) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            this.moveToShop();
        }
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private void moveToShop() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            String contextPath = origRequest.getContextPath();
            context.getExternalContext().redirect(contextPath + "/shop");
        } catch (IOException ex) {
            Logger.getLogger(ProductDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
