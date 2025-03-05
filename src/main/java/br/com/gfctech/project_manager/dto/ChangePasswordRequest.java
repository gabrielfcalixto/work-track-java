package br.com.gfctech.project_manager.dto;

public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;

    // Getters e Setters
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
