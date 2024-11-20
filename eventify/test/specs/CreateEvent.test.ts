import { browser, expect } from '@wdio/globals';
import EventCrudPage from '../pageobjects/eventcrudbjects/eventcrud.page';

describe('Create Event', () => {
    it('should create an event', async () => {
        await EventCrudPage.open()
        await EventCrudPage.login('joao@example.com', 'password123')
        await EventCrudPage.fillEventFields('Final da copa do Brasil', 'Venha compartilhar fortes emoções', '20112024', '20112024')
        await expect(EventCrudPage.logoutBtn).toBeDisplayed();
        await EventCrudPage.logout();
        await browser.takeScreenshot();
    })
});

