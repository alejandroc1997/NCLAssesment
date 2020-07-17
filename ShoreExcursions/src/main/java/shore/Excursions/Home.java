package shore.Excursions;

import Base.CommonAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Home extends CommonAPI {

    @FindBy(xpath = "//a[@title = 'Explore']") public static WebElement exploreButton;
    @FindBy(xpath = "//li/a[@href = '/excursions']") public static WebElement shoreExcursionButton;
    @FindBy(xpath = "//button[text() = 'FIND EXCURSIONS']") public static WebElement findExcursionsButton;
    @FindBy(xpath = "//h2[text() = 'Shore Excursions']") public static WebElement header;
    @FindBy(xpath = "//div[@class = 'modal-ajax']//div[text() = 'Updating...']")public static WebElement loadingIcon;
    @FindBy(className = "c1_link") public static WebElement nclLogo;

    public WebElement getSlider(By locator){
        WebElement slider = driver.findElement(locator);
        return slider;
    }

    public void setSliderValue(int offSet) {
        By sliderLocator = By.xpath("(//span[@class = 'ui-slider-handle ui-corner-all ui-state-default'])[2]");
        WebElement slider = getSlider(sliderLocator);
        executeJS("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'})", slider);
        new Actions(driver).clickAndHold(slider)
                .moveByOffset(offSet, 0)
                .build()
                .perform()
        ;

        //interact with the page so that it triggers the loading
        executeJS("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'})", header);
    }

    public void getExcursionUnder30(){
        //click on Excursions
        new Actions(driver).moveToElement(exploreButton);
        executeJS("arguments[0].click();", shoreExcursionButton);
        //click on Find Excursions
        executeJS("arguments[0].click();", findExcursionsButton);
        //set slider to find excursion with prices between $0-$30
        setSliderValue(-184);
        // Using sleep as a workaround for the test, the page doesn't always update for some reason after
        // Filtering the prices so this will "ensure" that the page loads
        //TODO: Optimize.
        try{
            Thread.sleep(5000);
        } catch(InterruptedException e){
        }
        explictlyWait(20).until(ExpectedConditions.invisibilityOf(loadingIcon));
        explictlyWait(20).until(d -> {
            return ((JavascriptExecutor)driver)
                    .executeScript("return document.readyState")
                    .equals("complete");
        });
        List<WebElement> excursionPrices = driver.findElements(By.xpath("//ul[@class = 'price']/li"));
        for (WebElement elem : excursionPrices
             ) {
            String text = elem.getText();
            String integers = text.replaceAll("[^0-9]", "");
            Assert.assertTrue(Integer.parseInt(integers) <= 3000);
        }
    }
}
