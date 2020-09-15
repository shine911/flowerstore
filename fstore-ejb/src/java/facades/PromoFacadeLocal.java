/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Promo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author quihuynh
 */
@Local
public interface PromoFacadeLocal {

    void create(Promo promo);

    void edit(Promo promo);

    void remove(Promo promo);

    Promo find(Object id);

    List<Promo> findAll();

    List<Promo> findRange(int[] range);
    
    Promo findByCode(String code);
    
    int count();
    
}
