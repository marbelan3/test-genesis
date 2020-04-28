package pages;

import helpers.RandomData;
import mailsac.BaseMailSacData;

public class UserForPromUaTests {
    private String email = generateRandomEmail();
    private String password = BaseMailSacData.PASSWORD;


    public String generateRandomEmail() {
        String domain = BaseMailSacData.DOMAIN;
        return RandomData.generateRandomString().toLowerCase() + "@" + domain;
    }

    @Override
    public String toString() {
        return "UserForTest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserForPromUaTests() {
    }
}
