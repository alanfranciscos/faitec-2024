import { browser, expect } from '@wdio/globals';
import LoginPage from '../../pageobjects/loginobjects/login.page';

describe('My Login application', () => {
    it('should login with valid credentials', async () => {
        await LoginPage.open()

        await LoginPage.login('joao@example.com', 'password123')
        await browser.takeScreenshot();
    })
})