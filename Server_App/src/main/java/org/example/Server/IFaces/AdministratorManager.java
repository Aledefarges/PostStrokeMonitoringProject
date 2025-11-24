package org.example.Server.IFaces;

import org.example.POJOS.Administrator;

public interface AdministratorManager {
   public void addAdministrator(Administrator administrator);
   public void deleteAdministrator(String email);
   public Administrator searchAdministratorByEmail(String email);
    public void updatePassword(int admin_id, String newPassword);
}
