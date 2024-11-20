import { browser, expect } from '@wdio/globals';
import LoginPage from '../../pageobjects/loginobjects/login.page';

describe('Create Event', () => {
    it('should use valid credentials', async () => {
        await LoginPage.open()

        await LoginPage.login('joao@example.com', 'password123')
        await LoginPage.fillEventFields('Final da copa do Brasil', 'Venha compartilhar fortes emoções', '20112024', '20112024')
        
        await expect(LoginPage.logoutBtn).toBeDisplayed();

        await browser.takeScreenshot();
    })
})