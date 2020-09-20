/*
 * Copyright 2020 quihuynh.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package admin.dashboard;

import entities.Orders;
import entities.Product;
import facades.CategoryFacadeLocal;
import facades.OrdersDetailsFacadeLocal;
import facades.OrdersFacadeLocal;
import facades.ProductFacadeLocal;
import facades.UserinfoFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.persistence.criteria.Order;

/**
 *
 * @author quihuynh
 */
@Named(value = "dashboardView")
@ViewScoped
public class DashboardView implements Serializable{

    @EJB
    private OrdersDetailsFacadeLocal ordersDetailsFacade;

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private OrdersFacadeLocal ordersFacade;

    @EJB
    private CategoryFacadeLocal categoryFacade;

    @EJB
    private UserinfoFacadeLocal userinfoFacade;
    
    /**
     * Creates a new instance of DashboardView
     */
    public DashboardView() {
    }
    
    public Integer countOrder(){
        return ordersFacade.count();
    }
    public Integer countProduct(){
        return productFacade.count();
    }
    public Integer countCategory(){
        return categoryFacade.count();
    }
    public Integer countUsers(){
        return userinfoFacade.count();
    }
    
    public List<Product> getTopProductsList(){
        List<Product> databaseList = productFacade.findAll();
        databaseList.sort(
                (Product p1, Product p2) -> Integer.compare(p1.getOrdersDetailsCollection().size(),p2.getOrdersDetailsCollection().size())
        );
        return databaseList.stream().limit(5).collect(Collectors.toList());
    }
    
    public long getPercentOrderState(int id){
        List<Orders> listOrders = ordersFacade.findAll();
        long currentState = listOrders.stream().filter(x->x.getOrdersState()==id).count();
        System.out.println(currentState);
        return (currentState*100/listOrders.size());
    }
}
