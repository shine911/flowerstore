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
package client;

import entities.Userinfo;
import facades.RolesFacadeLocal;
import facades.UserinfoFacadeLocal;
import helper.UtilsHelper;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import org.omnifaces.util.Ajax;

/**
 *
 * @author quihuynh
 */
@Named(value = "registryForm")
@ViewScoped
public class RegistryForm implements Serializable {

    @EJB
    private RolesFacadeLocal rolesFacade;

    @EJB
    private UserinfoFacadeLocal userinfoFacade;
    private String fname;
    private String lname;
    private String username;
    private String password;
    private String address;
    private String email;
    private String country;
    private String phone;
    private Integer zipcode;

    /**
     * Creates a new instance of RegistryForm
     */
    public RegistryForm() {
    }

    public void registry() {
        Userinfo user = new Userinfo();
        UtilsHelper helper = new UtilsHelper();
        user.setFname(this.fname);
        user.setLname(this.lname);
        user.setAddress(this.address);
        user.setCountry(this.country);
        user.setPhone(this.phone);
        user.setZipcode(this.zipcode);
        user.setEmail(this.email);
        user.setUsername(this.username);
        if(this.username!=null){
            System.out.println(this.username);
        }
        System.out.println(this.password);
        user.setPassword(helper.md5Hash(this.password));
        //set roles
        user.setRoleId(this.rolesFacade.find(1));
        if (user.getUsername()!=null) {
            this.userinfoFacade.create(user);
            Ajax.oncomplete("alert('Your account is created!');");
        } else {
            Ajax.oncomplete("alert('Errors');");
        }
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
