package org.example.Server.IFaces;

import org.example.Server.POJOS.User;

public interface UserManager {
    User login(String username, String password);
    boolean logout(int user_id);
    boolean register(User user);
    boolean changePassword(int user_id, String new_passwd);
}
