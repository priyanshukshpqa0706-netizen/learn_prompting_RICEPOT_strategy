package com.learningplaywright.salesforce;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest {
    private static final String DEFAULT_LOGIN_URL = "https://login.salesforce.com/?locale=in";
    private static final String INVALID_USERNAME = "invalid.user@example.invalid";
    private static final String INVALID_PASSWORD = "InvalidPassword-NotASecret";

    private String loginUrl;
    private String validUsername;
    private String validPassword;
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeTest(alwaysRun = true)
    public void configureTestRun() {
        loginUrl = System.getProperty("salesforce.url", DEFAULT_LOGIN_URL);
        validUsername = getConfiguration("salesforce.username", "SALESFORCE_USERNAME");
        validPassword = getConfiguration("salesforce.password", "SALESFORCE_PASSWORD");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            ChromeOptions options = new ChromeOptions();
            if (Boolean.parseBoolean(System.getProperty("headless", "true"))) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--window-size=1440,1000");
            driver = new ChromeDriver(options);
            loginPage = new LoginPage(driver);
        } catch (WebDriverException exception) {
            throw new IllegalStateException("Unable to initialize Chrome WebDriver", exception);
        }
    }

    @Test(description = "Valid credentials authenticate and Remember Me can be selected")
    public void validCredentialsShouldAuthenticate() {
        if (validUsername.isBlank() || validPassword.isBlank()) {
            throw new SkipException("Set SALESFORCE_USERNAME and SALESFORCE_PASSWORD to run the valid login test");
        }

        try {
            loginPage.open(loginUrl);
                Assert.assertTrue(loginPage.login(validUsername, validPassword, true),
                    "Remember Me should be selected before submission");
            Assert.assertTrue(loginPage.hasRedirectedAfterLogin(), "Valid credentials should redirect from the login host");
        } catch (RuntimeException exception) {
            throw new AssertionError("Valid Salesforce login test failed", exception);
        }
    }

    @Test(description = "Invalid credentials display a login error")
    public void invalidCredentialsShouldDisplayError() {
        try {
            loginPage.open(loginUrl);
                Assert.assertFalse(loginPage.login(INVALID_USERNAME, INVALID_PASSWORD, false),
                    "Remember Me should not be selected for the invalid login test");
            Assert.assertTrue(loginPage.isLoginErrorDisplayed(), "An error should be displayed for invalid credentials");
            Assert.assertFalse(loginPage.getLoginErrorText().isBlank(), "The login error should contain explanatory text");
        } catch (RuntimeException exception) {
            throw new AssertionError("Invalid Salesforce login test failed", exception);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (WebDriverException exception) {
                throw new IllegalStateException("Unable to close Chrome WebDriver", exception);
            } finally {
                driver = null;
                loginPage = null;
            }
        }
    }

    private String getConfiguration(String propertyName, String environmentName) {
        String value = System.getProperty(propertyName);
        return value != null ? value : System.getenv().getOrDefault(environmentName, "");
    }
}