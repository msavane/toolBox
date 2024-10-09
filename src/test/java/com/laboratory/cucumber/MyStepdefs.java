package com.laboratory.cucumber;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyStepdefs {
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




}

