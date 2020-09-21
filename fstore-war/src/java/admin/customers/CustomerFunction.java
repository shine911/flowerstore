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

import entities.Roles;
import entities.Userinfo;
import facades.RolesFacadeLocal;
import facades.UserinfoFacadeLocal;
import helper.UtilsHelper;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author quihuynh
 */
@Named(value = "customerFunction")
@SessionScoped
public class CustomerFunction implements Serializable {

    @EJB
    private RolesFacadeLocal rolesFacade;

    @EJB
    private UserinfoFacadeLocal userinfoFacade;

    private List<Userinfo> customerList;
    
    private List<Userinfo> searchList;
    
    private Userinfo currentUser;
    private String searchString;
    private List<Roles> roles;
    private int roleId;
    
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
    
    public String viewSingleCustomer(int id){
        this.currentUser = userinfoFacade.find(id);
        return "/admin/customers/view";
    }
    public void editSingleUserFormPost(){
        userinfoFacade.edit(currentUser);
    }
    
    public String deleteSingleUserFormPost(){
        userinfoFacade.remove(currentUser);
        return "index";
    }
    
    //Create get
    public String createSingleUser(){
        this.currentUser = new Userinfo();
        return "create";
    }
    //Create form post
    public String createSingleUserFormPost(){
        UtilsHelper helper = new UtilsHelper();
        currentUser.setPassword(helper.md5Hash(currentUser.getPassword()));
        //Get roles
        for(int i = 0; i<this.roles.size(); i++){
            if(roles.get(i).getId() == this.roleId){
                this.currentUser.setRoleId(roles.get(i));
                break;
            }
        }
        this.userinfoFacade.create(this.currentUser);
        return "index";
    }
    
    public String searchCustomer(){
        this.searchList = new ArrayList<>();
        for(int i = 0; i<this.customerList.size(); i++){
            if(this.customerList.get(i).getFname().contains(searchString) || this.customerList.get(i).getLname().contains(searchString) ){
                this.searchList.add(this.customerList.get(i));
            }
        }
        return "search";
    }
    
    public Userinfo getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Userinfo currentUser) {
        this.currentUser = currentUser;
    }
    
    public String editSingleUser(){
        return "edit";
    }

    public List<Roles> getRoles() {
        this.roles = this.rolesFacade.findAll();
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public List<Userinfo> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<Userinfo> searchList) {
        this.searchList = searchList;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    

}
