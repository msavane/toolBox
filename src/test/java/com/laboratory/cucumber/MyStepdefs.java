package com.laboratory.cucumber;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyStepdefs  {

    WebDriver driver; //= new ChromeDriver();

    @Given("I have <opening balance> pepsi cans")
    public void iHaveOpeningBalancePepsiCans() {
      System.out.println("I have 5 Pepsi");

    }

    @And("I have drunk <processed> pepsi cans")
    public void iHaveDrunkProcessedPepsiCans() {
        System.out.println("I drunk 1 Pepsi");
    }

    @When("I go to my fridge")
    public void iGoToMyFridge() {
        System.out.println("I walked to the fridge, to grab another");
    }

    @Then("I should have <in stock> pepsi cans")
    public void iShouldHaveInStockPepsiCans() {
        System.out.println("I had 4 Pepsi left");
    }


    @Given("I am pointing to searchEngine.com")
    public void iAmPointingToSearchEngineCom() {
        driver = new ChromeDriver();
        driver.get("http://google.com");

    }
    @When("I search for input")
    public void i_search_for_input() {
        // Write code here that turns the phrase above into concrete actions

        WebElement searchbox;
        searchbox = driver.findElement(By.name("q"));
        searchbox.click();

    }

    @And("<keyword : $\\{string}>")
    public void keyword$String() {

        WebElement searchbox;
        searchbox = driver.findElement(By.name("q"));
        searchbox.sendKeys("Flabola FSQS");
        searchbox.submit();
    }

    @Then("SEARCH is successful!")
    public void search_is_successful() {
        // Write code here that turns the phrase above into concrete actions
        driver.quit();

    }

}
