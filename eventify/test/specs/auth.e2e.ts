import { $, browser, expect } from '@wdio/globals';

describe('Login Page', () => {
    it('should allow a user to log in', async () => {
        await browser.url('http://localhost:4200/account/login');

        const usernameInput = await $('#username');
        const passwordInput = await $('#password');
        const loginButton = await $("//button[contains(text(), 'Sign In')]");

        await usernameInput.waitForDisplayed();
        await usernameInput.setValue('joao@example.com');

        await passwordInput.waitForDisplayed();
        await passwordInput.setValue('password123');

        await loginButton.waitForClickable();
        await loginButton.click();

        const userProfile = await $('#user-profile');
        await expect(userProfile).toBeDisplayed();
    });
});