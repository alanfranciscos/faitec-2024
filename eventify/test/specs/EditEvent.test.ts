import { browser, expect } from '@wdio/globals';
import EventCrudPage from '../pageobjects/eventcrudbjects/eventcrud.page';

describe('Edit Event', () => {
    it('should edit an event', async () => {
        await EventCrudPage.open()
        await EventCrudPage.login('joao@example.com', 'password123')
        await EventCrudPage.selectEventToEdit();
        await EventCrudPage.fillEventFieldsToEdit('Nome do evento editado', 'Descrição do evento editado', '20112024', '20112024')
        await expect(EventCrudPage.logoutBtn).toBeDisplayed();
        await EventCrudPage.logout();
        await browser.takeScreenshot();
    })
});
