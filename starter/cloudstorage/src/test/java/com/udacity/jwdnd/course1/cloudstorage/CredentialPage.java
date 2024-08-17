package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialPage {

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "credentialSubmitModal")
    private WebElement submitButtonModal;

    @FindBy(id = "add-new-credential-button")
    private WebElement addNewCredentialButton;

    @FindBy(id = "delete-btn-credential")
    private WebElement deleteCredentialButton;

    @FindBy(id = "credential-edit-button")
    private WebElement editCredentialButton;

    @FindBy(id = "credential-url-view")
    private WebElement credentialUrlView;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getAddNewCredentialButton() {
        return addNewCredentialButton;
    }

    public WebElement getSubmitButtonModal(){
        return submitButtonModal;
    }

    public void clickAddNewCredentialButton(){
        addNewCredentialButton.click();
    }

    public void createCredential(String url, String username, String password) {
        credentialUrl.sendKeys(url);
        credentialUsername.sendKeys(username);
        credentialPassword.sendKeys(password);
        submitButtonModal.click();
    }

    public void editCredential(String credentialUrl, String credentialUsername, String credentialPassword) {
        this.credentialUrl.clear();
        this.credentialUrl.sendKeys(credentialUrl);
        this.credentialUsername.clear();
        this.credentialUsername.sendKeys(credentialUsername);
        this.credentialPassword.clear();
        this.credentialPassword.sendKeys(credentialPassword);
        submitButtonModal.click();
    }

    public void clickDeleteCredentialButton() {
        deleteCredentialButton.click();
    }

    public void clickEditCredentialButton() {
        editCredentialButton.click();
    }

    public void clickCredentialTab(){
        credentialsTab.click();
    }
    public String getCredentialUrlView(){
        return credentialUrlView.getText();
    }
}
