package Test_Java.Selenium_Adv;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.Set;
import Test_Java.Selenium_Adv.TestElements;



public class TestCase {

	String expectedItegrationURL = "https://www.lambdatest.com/integrations";
	String expectedPageTitle = "Running Automation Tests Using TestingWhiz LambdaTest | LambdaTest";
	String expectedCommunityURL = "https://community.lambdatest.com/";
	private RemoteWebDriver driver;

	@BeforeMethod
	@Parameters(value = { "browser", "version", "platform" })
	public void setup(String browser, String version, String platform) throws MalformedURLException {
		String hub = "@hub.lambdatest.com/wd/hub";
		String username = "jayathirtha3";
		String accessKey = "LT_uuGArh2n4WJdfPQoCq8BZXmxX7dvmcvXvVL11YC6IF9Zg5Z";

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("browserVersion", version);
		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
		ltOptions.put("username", username);
		ltOptions.put("accessKey", accessKey);
		ltOptions.put("visual", true);
		ltOptions.put("video", true);
		ltOptions.put("platformName", platform);
		ltOptions.put("network", true);
		ltOptions.put("build", "Lambda_TestSeleniumAdvancedJava");
		ltOptions.put("project", "Lambda_TestSeleniumAdvancedJava");
		ltOptions.put("console", true);
		ltOptions.put("name", "Test: " + browser + " ");
		capabilities.setCapability("LT:Options", ltOptions);

		driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + hub), capabilities);

	}

	@Test(timeOut = 40000)
	@Parameters({ "platform" })
	public void lambdaTest(String platform) throws InterruptedException {

		TestElements TestElement = new TestElements(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions actions = new Actions(driver);

		// Point 1 - Navigate to the URL
		driver.get("https://www.lambdatest.com/");
		driver.manage().window().maximize();

		// Point 2 - Perform wait
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		System.out.println("User Navigated to: " + driver.getCurrentUrl());

		// Point 3 - Scroll to 'Explore all Integrations'
		js.executeScript("arguments[0].scrollIntoView(true);", TestElement.LnkExploreAllIntegration);
		System.out.println("Scrolled to the link - Explore all Integrations ");

		// Point 4 - Click link in new tab
		switch (platform) {
		case "Windows 10":
			TestElement.LnkExploreAllIntegration.sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			System.out.println("The link opened in new tab on windows");
			break;

		case "macOS Ventura":
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			TestElement.LnkExploreAllIntegration.sendKeys(Keys.chord(Keys.COMMAND, Keys.ENTER));
			System.out.println("The link opened in new tab on MAC OS");

			break;
		default:
			System.out.println("Unsupported browser");
			break;
		}
		// Point 5 - Fetch the window handles
		String parentWindow = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		if (windowHandles.size() > 1) {
			Assert.assertTrue(windowHandles.size() > 1, "The new link opened in new tab");
			for (String window : windowHandles) {
				System.out.println("The current window handle is:  " + window);
				if (!window.equals(parentWindow)) {
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					System.out.println("Switch to new window - " + window);
				}
			}
		} else
			Assert.fail("Expected a new window, but not found");

		// Point 6 - Verify current URL
		String actualIntegratioURL = driver.getCurrentUrl();
		try {
			Assert.assertEquals(actualIntegratioURL, expectedItegrationURL, "Failed: The URL is not as expected");
			System.out.println("The URL loaded as expected");
		} catch (Exception e) {
			System.out.println("The URL loaded is not as expected.");
		}
		// Point 7 - Scroll to 'Codeless Automation'
		js.executeScript("arguments[0].scrollIntoView(true);", TestElement.LnkCodelessAutomation);
		TestElement.LnkCodelessAutomation.click();
		System.out.println("Scrolled to the link");

		// Point 8 - Navigate to Integrate Testing Whiz with LambdaTest
		js.executeScript("arguments[0].scrollIntoView(true);", TestElement.LnkIntegrateTestingWhiz);
		js.executeScript("arguments[0].click();", TestElement.LnkIntegrateTestingWhiz);

		// Point 9 - Validate the page title
		String pageTitle = driver.getTitle();
		try {
			Assert.assertEquals(pageTitle, expectedPageTitle, "Failed: Page Title doesnot match");
			System.out.println("The page title is as expected");
		} catch (Exception e) {
			System.out.println("Page title is not as expected.");
		}

		// Point 10 - Close current tab
		driver.close();

		// Point 11 - Print current window count
		driver.switchTo().window(parentWindow);
		System.out.println("Current window count is: " + driver.getWindowHandles().size());

		// Point 12 - Navigate to Blog URL
		driver.get("https://www.lambdatest.com/blog/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Point13 - Click on community link
		TestElement.LnkCommunity.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String actualCommunityURL = driver.getCurrentUrl();
		Assert.assertEquals(actualCommunityURL, expectedCommunityURL, "Failed: Community URL is not as expected");
		System.out.println("The community URL matched - " + actualCommunityURL);
	}

	@AfterMethod
	public void tearDown() {
//		if (driver != null)
//			driver.quit();
		System.out.println("All the browser tabs are closed");
	}
	
	@AfterMethod
	public void updateLambdaTestStatus(ITestResult result) {

	    if (driver == null) return;

	    if (result.getStatus() == ITestResult.SUCCESS) {
	        ((JavascriptExecutor) driver)
	                .executeScript("lambda-status=passed");
	    } else if (result.getStatus() == ITestResult.FAILURE) {
	        ((JavascriptExecutor) driver)
	                .executeScript("lambda-status=failed");
	    } else {
	        ((JavascriptExecutor) driver)
	                .executeScript("lambda-status=skipped");
	    }

	    driver.quit();
	}

}
