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
package validation;

import entities.Userinfo;
import facades.UserinfoFacadeLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

/**
 *
 * @author quihuynh
 */
@ManagedBean(name = "userExistsValidator")
public class UserExistsValidator implements Validator {

    @EJB
    UserinfoFacadeLocal userinfoFacade;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String username = o.toString();
        try{
            Userinfo user = userinfoFacade.findByUsername(username);
            FacesMessage msg = new FacesMessage("username", "you cannot use this username");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        } catch (EJBException ex){
            FacesMessage msg = new FacesMessage("username", "You can use this username");
            FacesContext.getCurrentInstance().addMessage("myForm:username", msg);
        }
    }

}
