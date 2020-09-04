/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.OrdersDetails;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author quihuynh
 */
@Local
public interface OrdersDetailsFacadeLocal {

    void create(OrdersDetails ordersDetails);

    void edit(OrdersDetails ordersDetails);

    void remove(OrdersDetails ordersDetails);

    OrdersDetails find(Object id);

    List<OrdersDetails> findAll();

    List<OrdersDetails> findRange(int[] range);

    int count();
    
}
