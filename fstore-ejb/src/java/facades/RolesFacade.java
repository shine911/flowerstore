/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Roles;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author quihuynh
 */
@Stateless
public class RolesFacade extends AbstractFacade<Roles> implements RolesFacadeLocal {

    @PersistenceContext(unitName = "fstore-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolesFacade() {
        super(Roles.class);
    }
    
}
