package edu.uiuc.cs427app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * UserModelList stores the list of UserModels.
 */
public class UserModelList implements Serializable {
    public static ArrayList<UserModel> users = new ArrayList<>();

    /**
     * Returns the user model given string email.
     * @param email as string
     * @return UserModel with given email.
     */
    public static UserModel getUserByEmail(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (email.equals(users.get(i).getEmail())) {
                return users.get(i);
            }
        }

        return null;
    }

    /**
     * Add user to list of users.
     * @param user UserModel to be added to list.
     */
    public static void addUser(UserModel user) {
        users.add(user);
    }

    /**
     * Check if the user exists given a string email.
     * @param email as string
     * @return true if the user exists in the list, false otherwise.
     */
    public static boolean userExist(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (email.equals(users.get(i).getEmail())) {
                return true;
            }
        }
        return false;
    }
}
