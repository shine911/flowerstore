/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Images;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author quihuynh
 */
@Local
public interface ImagesFacadeLocal {

    void create(Images images);

    void edit(Images images);

    void remove(Images images);

    Images find(Object id);

    List<Images> findAll();

    List<Images> findRange(int[] range);

    int count();
    
}
