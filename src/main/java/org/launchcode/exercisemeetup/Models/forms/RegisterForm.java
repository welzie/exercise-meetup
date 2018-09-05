package org.launchcode.exercisemeetup.Models.forms;

import javax.validation.constraints.NotNull;

public class RegisterForm extends LoginForm {

    @NotNull(message = "Passwords do not match")
    private String verifyPassword;

    private String lastBreach;

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        checkPasswordForRegistration();
    }

    public String getLastBreach(){
        return lastBreach;
    }

    public void setLastBreach(String lastBreach) {
        this.lastBreach = lastBreach;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
        checkPasswordForRegistration();

    }

    private void checkPasswordForRegistration() {
        if (!getPassword().equals(verifyPassword)) {
            verifyPassword = null;
        }
    }
}
