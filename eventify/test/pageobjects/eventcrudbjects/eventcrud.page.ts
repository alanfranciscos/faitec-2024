import { $, browser } from '@wdio/globals'
import Page from '../page';

/**
 * sub page containing specific selectors and methods for a specific page
 */
class EventCrudPage extends Page {
    /**
     * define selectors using getter methods
     */
    public get inputUsername () {
        return $('app-primary-input#username input');
    }

    public get inputPassword () {
        return $('app-primary-input#password input');
    }

    public get btnSubmit () {
        return $('[data-testid="submit"]');
    }

    public get btnCreateEvent() {
        return $('#create-event-btn'); // Substitua pelo seletor correto do seu HTML
    }

    public get inputEventName () {
        return $('#event-name');
    }

    public get inputEventDescription () {
        return $('#event-description');
    }

    public get inputEventStartDate () {
        return $('#start-date');
    }

    public get inputEventFinishDate () {
        return $('#finish-date');
    }

    public get btnAddAddress () {
        return $('#add-address-btn');
    }

    public get btnAddPaymentAddress () {
        return $('#add-payment-btn-address');
    }

    public get btnCreateEventConclude () {
        return $('#event-create-conclude-btn');
    }

    public get logoutBtn() {
        return $('#logout-btn');
    }

    public get homeBtn() {
        return $('//button[span="Home"]');
    }

    public get firstEvent() {
        return $('(//app-card)[1]');
    }

    public get secondEvent() {
        return $('(//app-card)[2]');
    }

    public get fourEvent() {
        return $('(//app-card)[4]');
    }

    public get btnEditEvent(){
        return $('#edit-info-btn');
    }

    public get btnDeleteEvent(){
        return $('//button[contains(@class, "add-expense-button") and contains(text(), "Delete event")]');
    }

    public get confirmDeletEvent(){
        return $('//button[contains(@class, "confirm-button") and text()="Yes"]');
    }
   
    public get editInfoAddAddressBtn () {
        return $('#edit-info-add-address-btn');
    }
    
    public get btnEditPaymentAddress () {
        return $('button[ng-reflect-router-link="/event/1/edit-event/payment-in"]');
    }

    public get btnEditEventConclude () {
        return $('//button[contains(@class, "btn-next") and normalize-space(text())="Conclude"]');
    }

    /**
     * a method to encapsule automation code to interact with the page
     * e.g. to login using username and password
     */
    public async login (username: string, password: string) {
        await this.inputUsername.setValue(username);
        await this.inputPassword.setValue(password);
        await this.btnSubmit.click();
    }

    public async fillEventFields(eventName: string, eventDescription: string, startDate: string, finishDate: string) {
        await this.btnCreateEvent.click();
        await this.inputEventName.setValue(eventName);
        await this.inputEventDescription.setValue(eventDescription);
        await this.inputEventStartDate.setValue(startDate);
        await this.inputEventFinishDate.setValue(finishDate);

        await this.btnAddAddress.click();
        await this.btnAddPaymentAddress.click();
        await this.btnCreateEventConclude.click();

    }

    public async selectEventToEdit() {
        await this.firstEvent.click();
        await this.btnEditEvent.click();
    }

    public async fillEventFieldsToEdit(eventName: string, eventDescription: string, startDate: string, finishDate: string) {
        await this.inputEventName.setValue(eventName);
        await this.inputEventDescription.setValue(eventDescription);
        await this.inputEventStartDate.setValue(startDate);
        await this.inputEventFinishDate.setValue(finishDate);

        await this.editInfoAddAddressBtn.click();
        await this.btnEditPaymentAddress.click();
        await this.btnEditEventConclude.click();

    }

    public async selectEventToDelete() {
        await this.secondEvent.click();
        await this.btnDeleteEvent.click();
        await this.confirmDeletEvent.click();
    }

    public async selectEventFour() {
        await this.fourEvent.click();
    }
    
    public async logout() {
        await this.logoutBtn.click();
    }

    public async clickHomeButton() {
        await this.homeBtn.click();
    }

    /**
     * overwrite specific options to adapt it to page object
     */
    public open () {
        return super.open('account/login');
    }
}

export default new EventCrudPage();