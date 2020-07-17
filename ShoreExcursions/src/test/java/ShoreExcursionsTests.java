import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import shore.Excursions.Home;

public class ShoreExcursionsTests extends Home{
    Home home;

    @BeforeMethod
    public void initElements(){
        home = PageFactory.initElements(driver, Home.class);
    }

    @Test
    public void testExcursionsSlider(){
        getExcursionUnder30();
    }
}
