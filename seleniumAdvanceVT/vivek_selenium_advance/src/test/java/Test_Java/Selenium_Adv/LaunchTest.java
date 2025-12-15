package Test_Java.Selenium_Adv;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class LaunchTest {


	public static String username = "jayathirtha3";
    public static String access_key = "LT_uuGArh2n4WJdfPQoCq8BZXmxX7dvmcvXvVL11YC6IF9Zg5Z";
    public RemoteWebDriver driver = null;
    String hub = "@hub.lambdatest.com/wd/hub";
    protected WebDriverWait wait;
        
    @BeforeMethod()
    @Parameters(value={"browser","version", "resolution", "platform"})
    public void testSetUp(String browser, String version,String resolution,  String platform) throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("build", "[HyperExecute - 1] Certification");
        capabilities.setCapability("Project", "Selenium Certification");
        capabilities.setCapability("name", "Test Execution for selenium certification");
        capabilities.setCapability("resolution", resolution);
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("version", version);
        capabilities.setCapability("platform", platform);
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", username);
        ltOptions.put("accessKey", access_key);
        ltOptions.put("video",true);
        ltOptions.put("visual",true);
        ltOptions.put("network",true);
        ltOptions.put("console","true");
        ltOptions.put("accessibility", true);      
        ltOptions.put("tunnel",false);
        capabilities.setCapability("LT:Options", ltOptions);

        try
        {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + access_key + hub), capabilities);
       // wait = new WebDriverWait(driver, 0);
        }
        catch (MalformedURLException e)
        {
            System.out.println("Invalid grid URL");
        }
        System.out.println("Started session");
    }

}
