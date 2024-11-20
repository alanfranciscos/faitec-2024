import { $, browser } from '@wdio/globals'
import Page from '../page';

/**
 * sub page containing specific selectors and methods for a specific page
 */
class LoginPage extends Page {
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
   
    /**
     * a method to encapsule automation code to interact with the page
     * e.g. to login using username and password
     */
    public async login (username: string, password: string) {
        await this.inputUsername.setValue(username);
        await this.inputPassword.setValue(password);
        await this.btnSubmit.click();
        await browser.waitUntil(() => this.btnCreateEvent.isDisplayed(), {
            timeout: 5000,
            timeoutMsg: 'Botão "Create Event" não ficou visível após o login.',
        });
        await this.btnCreateEvent.click();
    }

    public async fillEventFields(eventName: string, eventDescription: string, startDate: string, finishDate: string) {
        await this.inputEventName.setValue(eventName);
        await this.inputEventDescription.setValue(eventDescription);
        await this.inputEventStartDate.setValue(startDate);
        await this.inputEventFinishDate.setValue(finishDate);

        await this.btnAddAddress.click();
        await this.btnAddPaymentAddress.click();
        await this.btnCreateEventConclude.click();

        // await browser.waitUntil(() => this.myEventsTitle.isDisplayed(), {
        //     timeout: 5000,
        //     timeoutMsg: 'Título "My Events" não ficou visível após criar o evento.',
        // });
    }

    /**
     * overwrite specific options to adapt it to page object
     */
    public open () {
        return super.open('account/login');
    }
}

export default new LoginPage();