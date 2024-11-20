import { browser, expect } from '@wdio/globals';
import EventCrudPage from '../pageobjects/eventcrudbjects/eventcrud.page';

describe('Read Event by ID', () => {
    it('should read an event', async () => {
        await EventCrudPage.open()
        await EventCrudPage.login('joao@example.com', 'password123')
        await EventCrudPage.selectEventFour();
        await expect(EventCrudPage.btnEditEvent).toBeDisplayed();
        // await EventCrudPage.logout();
        await browser.takeScreenshot();
    })
});