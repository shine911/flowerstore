/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redirect;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

/**
 *
 * @author quihuynh
 */
@Named(value = "mainRedirect")
@ConversationScoped
public class MainRedirect implements Serializable {

    /**
     * Creates a new instance of MainRedirect
     */
    public MainRedirect() {
    }
    
    @Inject
    Conversation conversation;
    
    public String toShop(int id){
        if(this.conversation.isTransient()){
            this.conversation.begin();
        }
        return "/shop/"+id+"?faces-redirect=true";
    }
}
