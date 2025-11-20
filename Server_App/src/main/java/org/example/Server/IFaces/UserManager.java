package org.example.Server.IFaces;

import org.example.POJOS.User;

public interface UserManager {
    public void addUser(User user);
    public void deleteUserByEmail(String email);
    public void deleteUserById(int user_id);
    public void changePassword(int user_id, String new_password);
    public boolean checkPassword(String username, String password);
    public void changeEmail(int user_id, String email);
    public User getUserByEmail(String email);
}
