package edu.uiuc.cs427app;

import java.util.ArrayList;

/**
 * This class represents a user.
 */
public class UserModel {
    private String email;
    private int theme;
    private ArrayList<String> locations;

    /**
     * Constructor for UserModel
     * @param email of the user.
     */
    public UserModel(String email){
        locations = new ArrayList<>();
        this.email = email;
        this.theme = R.style.Theme_MyFirstApp;
    }

    /**
     * Get the user email.
     * @return email as string
     */
    public String getEmail(){
        return email;
    }

    /**
     * Get the user's theme preference
     * @return theme as int
     */
    public int getTheme(){
        return theme;
    }

    /**
     * Set the user's theme preference.
     * @param theme as int to be saved.
     */
    public void setTheme(int theme){
        this.theme= theme;
    }

    /**
     * Return the user's saved locations
     * @return Arraylist of type string containing locations.
     */
    public ArrayList<String> getLocations(){
        return locations;
    }

}