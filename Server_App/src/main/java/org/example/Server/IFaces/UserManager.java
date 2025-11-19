package org.example.Server.IFaces;

import org.example.Server.POJOS.User;

public interface UserManager {
    public void addUser(User user);
    public void deleteUser(String email);
    public void changePassword(User user, String new_password);
    public boolean checkPassword(String username, String password);
    public void changeEmail(int user_id, String email);
    public User getUserByEmail(String email);
}
