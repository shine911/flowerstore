/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author quihuynh
 */
@Entity
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByOrdersState", query = "SELECT o FROM Orders o WHERE o.ordersState = :ordersState"),
    @NamedQuery(name = "Orders.findByDiscountValue", query = "SELECT o FROM Orders o WHERE o.discountValue = :discountValue"),
    @NamedQuery(name = "Orders.findByTotalValue", query = "SELECT o FROM Orders o WHERE o.totalValue = :totalValue")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "orders_state")
    private Integer ordersState;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "discount_value")
    private Double discountValue;
    @Column(name = "total_value")
    private Double totalValue;
    @OneToMany(mappedBy = "ordersId")
    private Collection<OrdersDetails> ordersDetailsCollection;
    @JoinColumn(name = "promo_id", referencedColumnName = "id")
    @ManyToOne
    private Promo promoId;
    @JoinColumn(name = "cus_id", referencedColumnName = "id")
    @ManyToOne
    private Userinfo cusId;

    public Orders() {
    }

    public Orders(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdersState() {
        return ordersState;
    }

    public void setOrdersState(Integer ordersState) {
        this.ordersState = ordersState;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    @XmlTransient
    public Collection<OrdersDetails> getOrdersDetailsCollection() {
        return ordersDetailsCollection;
    }

    public void setOrdersDetailsCollection(Collection<OrdersDetails> ordersDetailsCollection) {
        this.ordersDetailsCollection = ordersDetailsCollection;
    }

    public Promo getPromoId() {
        return promoId;
    }

    public void setPromoId(Promo promoId) {
        this.promoId = promoId;
    }

    public Userinfo getCusId() {
        return cusId;
    }

    public void setCusId(Userinfo cusId) {
        this.cusId = cusId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Orders[ id=" + id + " ]";
    }
    
}
