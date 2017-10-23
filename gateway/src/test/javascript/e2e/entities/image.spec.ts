import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Image e2e test', () => {

    let navBarPage: NavBarPage;
    let imageDialogPage: ImageDialogPage;
    let imageComponentsPage: ImageComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Images', () => {
        navBarPage.goToEntity('image');
        imageComponentsPage = new ImageComponentsPage();
        expect(imageComponentsPage.getTitle()).toMatch(/gatewayApp.image.home.title/);

    });

    it('should load create Image dialog', () => {
        imageComponentsPage.clickOnCreateButton();
        imageDialogPage = new ImageDialogPage();
        expect(imageDialogPage.getModalTitle()).toMatch(/gatewayApp.image.home.createOrEditLabel/);
        imageDialogPage.close();
    });

    it('should create and save Images', () => {
        imageComponentsPage.clickOnCreateButton();
        imageDialogPage.setNameInput('name');
        expect(imageDialogPage.getNameInput()).toMatch('name');
        imageDialogPage.setDescriptionInput('description');
        expect(imageDialogPage.getDescriptionInput()).toMatch('description');
        imageDialogPage.setFileInput(absolutePath);
        imageDialogPage.save();
        expect(imageDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ImageComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-image div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ImageDialogPage {
    modalTitle = element(by.css('h4#myImageLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    descriptionInput = element(by.css('input#field_description'));
    fileInput = element(by.css('input#file_file'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
    }

    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
    }

    setFileInput = function (file) {
        this.fileInput.sendKeys(file);
    }

    getFileInput = function () {
        return this.fileInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
