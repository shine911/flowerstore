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

import facades.UserinfoFacadeLocal;
import helper.UtilsHelper;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Faces;

/**
 *
 * @author quihuynh
 */
@Named(value = "editAccountForm")
@ViewScoped
public class EditAccountForm implements Serializable{

    @EJB
    private UserinfoFacadeLocal userinfoFacade;

    private UserManager userManager;
    private String fname;
    private String lname;
    private String current_password = "";
    private String new_password;
    private String address;
    private String email;
    private String country;
    private String phone;
    private Integer zipcode;

    /**
     * Creates a new instance of EditAccountForm
     */
    public EditAccountForm() {

    }

    @PostConstruct
    public void init() {
        this.userManager = Faces.evaluateExpressionGet("#{userManager}");
        this.fname = userManager.user.getFname();
        this.lname = userManager.user.getLname();
        this.address = userManager.user.getAddress();
        this.country = userManager.user.getCountry();
        this.phone = userManager.user.getPhone();
        this.zipcode = userManager.user.getZipcode();
        this.email = userManager.user.getEmail();
    }

    public void saveInformation(){
        this.userManager.user.setFname(fname);
        this.userManager.user.setLname(lname);
        this.userManager.user.setAddress(address);
        this.userManager.user.setCountry(country);
        this.userManager.user.setPhone(phone);
        this.userManager.user.setZipcode(zipcode);
        this.userManager.user.setEmail(email);
        if(this.new_password != null){
            UtilsHelper helper = new UtilsHelper();
            this.userManager.user.setPassword(helper.md5Hash(new_password));
        }
        this.userinfoFacade.edit(this.userManager.user);
        Ajax.oncomplete("alert('Your account is updated')");
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

    public String getCurrent_password() {
        return current_password;
    }

    public void setCurrent_password(String current_password) {
        this.current_password = current_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
}
