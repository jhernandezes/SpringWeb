package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotePage {

    @FindBy(id="nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id="note-title")
    private WebElement noteTitleFieldModal;

    @FindBy(id="note-description")
    private WebElement noteDescriptionFieldModal;

    @FindBy(id="add-new-note-button")
    private WebElement addNewNoteButton;

    @FindBy(id="note-submit-button")
    private WebElement noteSubmitButtonModal;

    @FindBy(id="modal-submit-button")
    private WebElement modalSubmitButtonModal;

    @FindBy(id="note-title-view")
    private WebElement noteTitleView;

    @FindBy(id="note-description-view")
    private WebElement noteDescriptionView;

    @FindBy(id="note-edit-button")
    private WebElement editNoteButton;

    @FindBy(id="note-delete-button")
    private WebElement deleteNoteButton;

    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getNoteSubmitButtonModal() {
        return modalSubmitButtonModal;
    }

    public WebElement getAddNewNoteButton() {
        return addNewNoteButton;
    }

    public void clickNoteTab() {
        notesTab.click();
    }

    public void clickAddNewNoteButton() {
        addNewNoteButton.click();
    }

    public void clickEditNoteButton() {
        editNoteButton.click();
    }

    public void clickDeleteNoteButton() {
        deleteNoteButton.click();
    }

    public void createNote(String noteTitle, String noteDescription) {
        noteTitleFieldModal.sendKeys(noteTitle);
        noteDescriptionFieldModal.sendKeys(noteDescription);
        modalSubmitButtonModal.click();
    }

    public void editNote(String noteTitle, String noteDescription) {
        noteTitleFieldModal.clear();
        noteTitleFieldModal.sendKeys(noteTitle);
        noteDescriptionFieldModal.clear();
        noteDescriptionFieldModal.sendKeys(noteDescription);
        modalSubmitButtonModal.click();
    }

    public String getNoteTitle() {
        return noteTitleView.getText();
    }

    public String getNoteDescription() {
        return noteDescriptionView.getText();
    }

    public WebElement getNoteTitleView(){
        return noteTitleView;
    }

}
