/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Userinfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author quihuynh
 */
@Local
public interface UserinfoFacadeLocal {

    void create(Userinfo userinfo);

    void edit(Userinfo userinfo);

    void remove(Userinfo userinfo);

    Userinfo find(Object id);
    
    Userinfo findByUsername(String username);

    List<Userinfo> findAll();

    List<Userinfo> findRange(int[] range);

    int count();
    
}
