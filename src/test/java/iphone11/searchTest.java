package iphone11;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class searchTest {
    final double VAT = 0.0123;
    private static WebDriver driver;
    final private String URL = "https://www.allegro.pl";

    @BeforeMethod
    public void setup() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    @Test(description = "search: iphone 11, black ")
    public void search() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        boolean cookies= wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-labelledby='dialog-title']"))).isDisplayed();
        if (cookies)
            try {
                //driver.findElement((By.cssSelector(("[alt='zamknij']")))).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(("[alt='zamknij']")))).click();

            } catch (Exception e) {
                e.printStackTrace();
            }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("string"))).sendKeys("Iphone 11");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-role='search-button']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'czarny')]/../.."))).click();

        int allOffers = driver.findElements(By.cssSelector("[data-role='offer']")).size();

        List<Integer> prices = new ArrayList<>();
        List<WebElement> index = driver.findElements(By.cssSelector("[data-box-name='items-v3'] [class='_1svub _lf05o']"));
        for (WebElement i : index) {
            prices.add(Integer.parseInt(i.getText().replaceAll("[ ,zł]", "")));
        }
        Integer maxPrice = Collections.max(prices);
        double maxPriceVatIncluded = maxPrice * VAT;
        String maxPriceStr= maxPrice.toString();
        maxPriceStr = new StringBuilder(maxPriceStr).insert(maxPriceStr.length() - 2, ".").toString();
        System.out.println("All offers at the first page: " + allOffers);
        System.out.println("maximum price : " + maxPriceStr + " zł");
        System.out.println("maximum price with 'vat' included: " + maxPriceVatIncluded + " zł");
    }

    @AfterMethod
    public static void after() {
        driver.close();
        driver.quit();
    }

}
