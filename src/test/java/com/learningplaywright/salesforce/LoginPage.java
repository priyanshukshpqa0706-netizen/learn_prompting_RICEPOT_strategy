package com.learningplaywright.salesforce;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriverException;

public class LoginPage {
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(15);

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//input[@id='username' or @name='username']")
    private WebElement username;

    @FindBy(xpath = "//input[@id='password' or @name='pw']")
    private WebElement password;

    @FindBy(xpath = "//button[@id='Login' or normalize-space(.)='Log In'] | //input[@id='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//input[@id='rememberUn']")
    private WebElement rememberMe;

    @FindBy(xpath = "//div[@id='error']")
    private WebElement loginError;

    public LoginPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver must not be null");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        PageFactory.initElements(driver, this);
    }

    public void open(String url) {
        try {
            driver.get(url);
            wait.until(ExpectedConditions.visibilityOf(username));
        } catch (WebDriverException exception) {
            throw new IllegalStateException("Unable to open the Salesforce login page", exception);
        }
    }

    public boolean login(String user, String pass, boolean rememberUsername) {
        try {
            wait.until(ExpectedConditions.visibilityOf(username)).clear();
            username.sendKeys(user);
            setRememberMe(rememberUsername);

            boolean passwordIsVisible = driver.findElements(By.xpath("//input[@id='password' or @name='pw']"))
                    .stream()
                    .anyMatch(WebElement::isDisplayed);
            if (!passwordIsVisible) {
                wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
                wait.until(ExpectedConditions.visibilityOf(password));
            }

            password.clear();
            password.sendKeys(pass);
            boolean rememberMeSelected = rememberMe.isSelected();
            wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
            return rememberMeSelected;
        } catch (WebDriverException exception) {
            throw new IllegalStateException("Unable to submit the Salesforce login form", exception);
        }
    }

    public boolean isLoginErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(loginError)).isDisplayed();
        } catch (WebDriverException exception) {
            throw new IllegalStateException("Unable to verify the Salesforce login error", exception);
        }
    }

    public String getLoginErrorText() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(loginError)).getText().trim();
        } catch (WebDriverException exception) {
            throw new IllegalStateException("Unable to read the Salesforce login error", exception);
        }
    }

    public boolean hasRedirectedAfterLogin() {
        try {
            return wait.until(currentDriver -> !currentDriver.getCurrentUrl().contains("login.salesforce.com"));
        } catch (WebDriverException exception) {
            throw new IllegalStateException("Salesforce did not redirect after login", exception);
        }
    }

    public boolean isRememberMeSelected() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(rememberMe)).isSelected();
        } catch (WebDriverException exception) {
            throw new IllegalStateException("Unable to verify the Remember Me selection", exception);
        }
    }

    private void setRememberMe(boolean selected) {
        wait.until(ExpectedConditions.visibilityOf(rememberMe));
        if (rememberMe.isSelected() != selected) {
            rememberMe.click();
        }
    }
}