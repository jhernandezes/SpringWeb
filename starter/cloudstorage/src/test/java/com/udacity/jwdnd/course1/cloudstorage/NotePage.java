package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class NotePage {
    @FindBy(id = "nav-notes-tab")
    private WebElement navNoteTab;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;

    @FindBy(id = "deleteNoteButton")
    private WebElement deleteNoteButton;

    @FindBy(id = "noteTitleList")
    private List<WebElement> noteTitleList;

    @FindBy(id = "noteDescriptionList")
    private List<WebElement> noteDescriptionList;

    @FindBy(id = "note-title")
    private WebElement titleInputField;

    @FindBy(id = "note-description")
    private WebElement descriptionInputField;

    @FindBy(id = "saveNoteButton")
    private WebElement saveNoteButton;


    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addNewNoteAction(WebDriver driver, String title, String description, WebElement webElement){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(navNoteTab)).click();
        } catch (TimeoutException ex){
            System.out.println("Timeout exception");
            webElement.click();
            wait.until(ExpectedConditions.elementToBeClickable(navNoteTab)).click();
        }

        wait.until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(titleInputField)).sendKeys(title);
        wait.until(ExpectedConditions.elementToBeClickable(descriptionInputField)).sendKeys(description);
        wait.until(ExpectedConditions.elementToBeClickable(this.saveNoteButton)).click();
    }

    public List<String> getList(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 7);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.elementToBeClickable(navNoteTab)).click();
        navNoteTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(addNoteButton));
        List<String> listOfNoteElements = new ArrayList<>(List.of(noteTitleList.get(0).getText(), noteDescriptionList.get(0).getText()));
        return listOfNoteElements;
    }

    public void editNoteAction(WebDriver driver, String title, String description, WebElement nav){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(navNoteTab)).click();
        } catch (TimeoutException ex){
            System.out.println("Timeout exception");
            nav.click();
            wait.until(ExpectedConditions.elementToBeClickable(navNoteTab)).click();
        }

        wait.until(ExpectedConditions.elementToBeClickable(editNoteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(titleInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(titleInputField)).sendKeys(title);
        wait.until(ExpectedConditions.elementToBeClickable(descriptionInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(descriptionInputField)).sendKeys(description);
        wait.until(ExpectedConditions.elementToBeClickable(this.saveNoteButton)).click();
    }

    public void deleteNoteAction(WebDriver driver, WebElement nav){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(navNoteTab)).click();
        } catch (TimeoutException ex){
            System.out.println("Timeout exception");
            nav.click();
            wait.until(ExpectedConditions.elementToBeClickable(navNoteTab)).click();
        }

        wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButton)).click();
    }

    public int getSizeOfNoteList(){
        return this.noteTitleList.size();
    }

    public List<WebElement> getNoteTitleList(){
        return this.noteTitleList;
    }

    public List<WebElement> getNoteDescriptionList(){
        return this.noteDescriptionList;
    }
}
