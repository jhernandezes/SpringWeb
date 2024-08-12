package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class CredentialPage {

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialTab;

    @FindBy(id = "newCredentialButton")
    private WebElement newCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement urlInput;

    @FindBy(id = "credential-username")
    private WebElement usernameInput;

    @FindBy(id = "credential-password")
    private WebElement passwordInput;

    @FindBy(id = "credentialSubmitModal")
    private WebElement saveChangesButton;

    @FindBy(id = "credential-url-text")
    private List<WebElement> urlText;

    @FindBy(id = "credential-password-text")
    private List<WebElement> passwordText;

    @FindBy(id = "credential-username-text")
    private List<WebElement> usernameText;

    @FindBy(id = "editButton")
    private List<WebElement> editButton;

    @FindBy(id = "deleteButton")
    private List<WebElement> deleteButton;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addNewCredentialAction(WebDriver driver, String url, String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(ExpectedConditions.elementToBeClickable(navCredentialTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(newCredentialButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(urlInput)).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(usernameInput)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(saveChangesButton)).click();
    }

    public List<WebElement> getUrlText(){
        return this.urlText;
    }

    public List<WebElement> getPasswordText(){
        return this.passwordText;
    }

    public List<WebElement> getUsernameText(){
        return this.usernameText;
    }

    public List<String> getAttributesOfCredentialInstance(WebDriver driver, int index){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(navCredentialTab));
        wait.until(ExpectedConditions.visibilityOf(newCredentialButton));
        List<String> attributesOfCredInstance = new ArrayList<>
                (List.of(urlText.get(index).getText(),
                        usernameText.get(index).getText(),
                        passwordText.get(index).getText()));
        return attributesOfCredInstance;
    }

    public void editCredential(WebDriver driver, int index, String url, String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(navCredentialTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(editButton.get(index))).click();

        wait.until(ExpectedConditions.elementToBeClickable(urlInput)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(urlInput)).sendKeys(url);

        wait.until(ExpectedConditions.elementToBeClickable(usernameInput)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(usernameInput)).sendKeys(username);

        wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(saveChangesButton)).click();
    }

    public void deleteCredentialAction(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton.get(0))).click();
    }

    public WebElement getPasswordInput(){
        return this.passwordInput;
    }

    public List<WebElement> getEditButton(){
        return this.editButton;
    }
}
