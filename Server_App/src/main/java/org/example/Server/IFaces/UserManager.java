package org.example.Server.IFaces;

import org.example.Server.POJOS.User;

public interface UserManager {
    public User login(String username, String password);
    public boolean logout(int user_id);
    public boolean register(User user);
    public void changePassword(User user, String new_passwd);
}
