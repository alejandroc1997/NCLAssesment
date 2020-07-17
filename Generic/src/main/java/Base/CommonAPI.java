package Base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommonAPI {

    public WebDriver driver = null;

    @Parameters({"url"})
    @BeforeMethod
    public void setUp(@Optional("https://www.ncl.com/") String url) {
    System.setProperty("webdriver.chrome.driver", "../Driver/chromedriver 83.exe");
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    driver.get(url);
    }

    @AfterMethod
    public void close() {
        driver.quit();
    }

    /**
     *  Helper Methods
     */

    public Wait<WebDriver> explictlyWait(long timeout) {
        return new WebDriverWait(driver, timeout);
    }

    public void exploreButtonlistIteratior(List<WebElement> element, String text) {
        for (WebElement button :element) {
            if (button.getText().equalsIgnoreCase(text)) {
                button.click();
                break;
            }
        }
    }

    public void executeJS(String script, WebElement webElement) {
        ((JavascriptExecutor)driver).executeScript(script, webElement);
    }
}
