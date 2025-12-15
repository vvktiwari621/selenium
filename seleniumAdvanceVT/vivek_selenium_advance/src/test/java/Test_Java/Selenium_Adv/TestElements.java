package Test_Java.Selenium_Adv;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import Test_Java.Selenium_Adv.LaunchTest;

public class TestElements extends LaunchTest{

    @FindBy(xpath = "//a[text() = 'Explore all Integrations']")
	public WebElement LnkExploreAllIntegration;

    @FindBy(xpath = "//a[text() = 'Codeless Automation']")
	public WebElement LnkCodelessAutomation;
    
    @FindBy(xpath = "//a[text() = 'Integrate Testing Whiz with LambdaTest']")
	public WebElement LnkIntegrateTestingWhiz; 
    
    @FindBy(xpath = "//li[contains(@id, 'menu-item')]/a[text() = 'Community']")
	public WebElement LnkCommunity;

    public TestElements(RemoteWebDriver driver) {
        super();
        // Initialize elements
        PageFactory.initElements(driver, this);
    }

       
}