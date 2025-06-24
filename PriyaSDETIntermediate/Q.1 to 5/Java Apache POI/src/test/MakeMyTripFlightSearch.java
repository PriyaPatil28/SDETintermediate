import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

import java.time.Duration;

public class MakeMyTripFlightTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void launchBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }

    @Test
    public void testFlightSearch() {
        driver.get("https://www.makemytrip.com/");
        try {
            driver.findElement(By.tagName("body")).click(); // Close popup

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Flights']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@data-cy='roundTrip']"))).click();

            driver.findElement(By.xpath("//label[@for='fromCity']")).click();
            WebElement from = driver.findElement(By.xpath("//input[@placeholder='From']"));
            from.sendKeys("HYD");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(),'Hyderabad')]"))).click();

            WebElement to = driver.findElement(By.xpath("//input[@placeholder='To']"));
            to.sendKeys("MAA");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(),'Chennai')]"))).click();

            driver.findElement(By.xpath("//label[@for='departure']")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-label and not(contains(@aria-label,'disabled'))]"))).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@aria-label and not(contains(@aria-label,'disabled'))])[14]"))).click();

            driver.findElement(By.xpath("//a[text()='Search']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Flights from')]")));
            System.out.println("✅ Flight search results displayed.");
        } catch (Exception e) {
            System.err.println("❌ Test Failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void closeBrowser() {
        if (driver != null) driver.quit();
    }
}
