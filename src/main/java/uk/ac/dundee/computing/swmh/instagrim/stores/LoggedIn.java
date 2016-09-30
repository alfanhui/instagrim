/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.swmh.instagrim.stores;


/**
 *
 * @author Administrator
 */
public class LoggedIn {
    private boolean logedin=false;
    private String Username=null;
    private String Email=null;
    private java.util.UUID uuid=null;
    
    public void LogedIn(){
        
    }
    
    public void setUsername(String name){
        this.Username=name;
    }
    public String getUsername(){
        return Username;
    }
    public void setEmail(String email){
        this.Email=email;
    }
    public String getEmail(){
        return Email;
    }
    public String getProfileUUID(){
        return uuid.toString();
    }
    public void setProfileUUID(java.util.UUID uuid){
        this.uuid=uuid;
    }
    public void setLogedin(){
        logedin=true;
    }
    public boolean getlogedin(){
        return logedin;
    }
    public void setLogedout(){
        logedin=false;
    }
    public boolean hasProfilePic(){
        if (this.uuid == null){
            return false;
        }else
            return true;
    }
    public void setLoginState(boolean logedin){
        this.logedin=logedin;
    }

}
