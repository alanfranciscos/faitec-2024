import { browser, expect } from '@wdio/globals';
import EventCrudPage from '../pageobjects/eventcrudbjects/eventcrud.page';

describe('Delete Event', () => {
    it('should delete an event', async () => {
        await EventCrudPage.open()
        await EventCrudPage.login('joao@example.com', 'password123')
        await EventCrudPage.selectEventToDelete();
        await expect(EventCrudPage.logoutBtn).toBeDisplayed();
        await EventCrudPage.logout();
        await browser.takeScreenshot();
    })
});