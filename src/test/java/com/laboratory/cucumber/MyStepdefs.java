package com.laboratory.cucumber;


import io.cucumber.java.en.Given;

public class MyStepdefs {
    @Given("I have <opening balance> pepsi cans")
    public void iHaveOpeningBalancePepsiCans() {
      System.out.println("I have 5 Pepsi");

    }
}
