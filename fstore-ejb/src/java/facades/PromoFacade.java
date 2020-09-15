/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Promo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author quihuynh
 */
@Stateless
public class PromoFacade extends AbstractFacade<Promo> implements PromoFacadeLocal {

    @PersistenceContext(unitName = "fstore-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PromoFacade() {
        super(Promo.class);
    }

    @Override
    public Promo findByCode(String code) {
        TypedQuery<Promo> q = em.createNamedQuery("Promo.findByPromoCode", Promo.class);
        return q.setParameter("promoCode", code).getSingleResult();
    }
    
    
}
