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
    
    public void LogedIn(){
        
    }
    
    public void setUsername(String name){
        this.Username=name;
    }
    public void setEmail(String email){
        this.Email=email;
    }
    public String getUsername(){
        return Username;
    }
    public String getEmail(){
        return Email;
    }
    public void setLogedin(){
        logedin=true;
    }
    public void setLogedout(){
        logedin=false;
    }
    
    public void setLoginState(boolean logedin){
        this.logedin=logedin;
    }
    public boolean getlogedin(){
        return logedin;
    }
}
