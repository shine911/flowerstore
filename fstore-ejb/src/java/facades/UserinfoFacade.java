/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Userinfo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author quihuynh
 */
@Stateless
public class UserinfoFacade extends AbstractFacade<Userinfo> implements UserinfoFacadeLocal {

    @PersistenceContext(unitName = "fstore-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserinfoFacade() {
        super(Userinfo.class);
    }

    @Override
    public Userinfo findByUsername(String username) {
        TypedQuery<Userinfo> user = em.createNamedQuery("Userinfo.findByUsername", Userinfo.class);
        return user.setParameter("username", username).getSingleResult();
    }
    
}
