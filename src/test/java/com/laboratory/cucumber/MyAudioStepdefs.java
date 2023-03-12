package com.laboratory.cucumber;


import com.laboratory.apiBot.PostToApi;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyAudioStepdefs {
    PostToApi post = new PostToApi();

    @Given("I am pointing to url")
    public void iAmPointingToUrl() {
        post.provideEndPointUri("https://api.assemblyai.com/v2/transcript");
    }

    @When("I post audio to AssemblyAI")
    public void iPostAudioToAssemblyAI() {
        System.out.println(post.endPointUri);

    }

    @And("<audioLocation : {string}>")
    public void audiolocation(String arg0) {
        post.provideAudioLocation(arg0);
        System.out.println(post.audioProperty);
    }

    @Then("FETCH AUDIO INFO is successful!")
    public void fetchAUDIOINFOIsSuccessful() throws Exception {
        post.postTo();
        System.out.println("FETCH AUDIO INFO is successful!");
    }
}
