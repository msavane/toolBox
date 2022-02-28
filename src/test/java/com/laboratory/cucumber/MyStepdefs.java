package com.laboratory.cucumber;


import com.laboratory.selenium.SearchGoogle;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyStepdefs {

    SearchGoogle sg = new SearchGoogle();

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
    public void iAmPointingToSearchEngineCom() throws InterruptedException {
        sg.setupApplication();


    }

    @When("I search for input")
    public void i_search_for_input() throws InterruptedException {
        sg.askGoogle();

    }

    @And("<keyword : $\\{string}>")
    public void keyword$String() {
        sg.findInGoogle("FSQS Flabola");
        System.out.println("To do make input dynamic");
    }

    @Then("SEARCH is successful!")
    public void search_is_successful() throws InterruptedException {
        sg.checkResults();
        sg.closeApplication();

    }

}
