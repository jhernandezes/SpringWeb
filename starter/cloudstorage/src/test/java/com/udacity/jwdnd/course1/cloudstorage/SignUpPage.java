package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(id="inputFirstName")
    private WebElement firstName;

    @FindBy(id="inputLastName")
    private WebElement lastName;

    @FindBy(id="inputUsername")
    private WebElement username;

    @FindBy(id="inputPassword")
    private WebElement password;

    @FindBy(id="buttonSignUp")
    private WebElement signUpButton;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signUp(String firstName, String lastName, String username, String password) {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        signUpButton.click();
    }
}
