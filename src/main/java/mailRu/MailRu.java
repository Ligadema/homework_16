package mailRu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MailRu {

    private WebDriver driver;
    private static final int TIMEOUT = 10;
    private static final String FIRST_MAIL_LINK = "(//div[@class='llc__item llc__item_title'])[1]";
    private static final String SPAM_BUTTON_LINK = "//span[@title='Спам']";
    private static final String ALL_FOLDERS_LINK = "//a[@title='Все папки']";
    private static final String SPAM_FOLDER_LINK = "(//a[@href='/spam/'])[2]//div[contains(text(),'Спам')]";
    private static final String LOGOUT_LINK = "//a[@id='PH_logoutLink']";

    @FindBy(id = "mailbox:login")
    private WebElement loginField;

    @FindBy(id = "mailbox:password")
    private WebElement passwordField;

    @FindBy(xpath = FIRST_MAIL_LINK)
    private WebElement firstMail;

    @FindBy(xpath = SPAM_BUTTON_LINK)
    private WebElement spamButton;

    @FindBy(xpath = ALL_FOLDERS_LINK)
    private WebElement allFoldersButton;

    @FindBy(xpath = SPAM_FOLDER_LINK)
    private WebElement spamFolderButton;

    @FindBy(xpath = "//div[@class='letter-list letter-list_has-letters']")
    private WebElement spamMail;

    @FindBy(xpath = LOGOUT_LINK)
    private WebElement logOutButton;

    public MailRu(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterLogin(String login) {
        submitText(loginField, login);
    }

    public void enterPassword(String password) {
        submitText(passwordField, password);
    }

    public void chooseFirstMail() {
        clickElement(firstMail, FIRST_MAIL_LINK);
    }

    public void moveFirstMailToSpam() {
        clickElement(spamButton, SPAM_BUTTON_LINK);
    }

    public void goToAllFoldersList() {
        clickElement(allFoldersButton, ALL_FOLDERS_LINK);
    }

    public void goToSpamFolder() {
        clickElement(spamFolderButton, SPAM_FOLDER_LINK);
    }

    public boolean isSpamFolderNotEmpty() {
        spamMail = (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.visibilityOf(spamMail));
        return spamMail.isDisplayed();
    }

    public void logOut() {
        clickElement(logOutButton, LOGOUT_LINK);
    }

    public void submitText(WebElement webElement, String text) {
        webElement = (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.visibilityOf(webElement));
        webElement.clear();
        webElement.sendKeys(text);
        webElement.submit();
    }

    public void clickElement(WebElement webElement, String xpath) {
        webElement = (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        webElement.click();
    }
}
