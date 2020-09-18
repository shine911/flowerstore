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
package admin.customers;

import entities.Userinfo;
import facades.UserinfoFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.omnifaces.cdi.Param;

/**
 *
 * @author quihuynh
 */
@Named(value = "customerFunction")
@SessionScoped
public class CustomerFunction implements Serializable {

    @EJB
    private UserinfoFacadeLocal userinfoFacade;

    private List<Userinfo> customerList;
    
    /**
     * Creates a new instance of CustomerFunction
     */
    public CustomerFunction() {
    }

    public List<Userinfo> getCustomerList() {
        this.customerList = userinfoFacade.findAll();
        return customerList;
    }

    public void setCustomerList(List<Userinfo> customerList) {
        this.customerList = customerList;
    }
    
    public Userinfo getSingleCustomer(@Param(name = "id") int id){
        return userinfoFacade.find(id);
    }
    
    
}
