package laboratory.com.laboratory.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestWikipedia {

    public static void main(String[]args) throws InterruptedException{

        WebDriver driver = new ChromeDriver();
        driver.get("http://wikipedia.com");
        WebElement link;
        link = driver.findElement(By.id("js-link-box-en"));
        link.click();
        Thread.sleep(5000);
        WebElement searchbox;
        searchbox = driver.findElement(By.id("searchInput"));
        searchbox.sendKeys("software");
         Thread.sleep(5000);
         //driver.quit();

    }


}
