package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivationRequest;
import au.com.telstra.simcardactivator.SimCardActivationResponse;
import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    private String currentIccid;
    private String currentEmail;
    private ResponseEntity<Boolean> activationResponse;
    private ResponseEntity<SimCardActivationResponse> queryResponse;

    @Given("I have a valid ICCID {string}")
    public void i_have_a_valid_iccid(String iccid) {
        this.currentIccid = iccid;
    }

    @Given("I have the customer email {string}")
    public void i_have_the_customer_email(String email) {
        this.currentEmail = email;
    }

    @When("I submit an activation request")
    public void i_submit_an_activation_request() {
        SimCardActivationRequest request = new SimCardActivationRequest();
        request.setIccid(currentIccid);
        request.setCustomerEmail(currentEmail);
        activationResponse = restTemplate.postForEntity("/activate", request, Boolean.class);
    }

    @Then("the activation request should be successful")
    public void the_activation_request_should_be_successful() {
        Assert.assertTrue("Activation should be successful", activationResponse.getBody());
    }

    @Then("the activation request should be unsuccessful")
    public void the_activation_request_should_be_unsuccessful() {
        Assert.assertFalse("Activation should be unsuccessful", activationResponse.getBody());
    }

    @When("when I query the activation status with ID {long}")
    public void when_i_query_the_activation_status_with_id(Long id) {
        queryResponse = restTemplate.getForEntity("/simcard?simCardId=" + id, SimCardActivationResponse.class);
    }

    @Then("I should see the SIM card is active")
    public void i_should_see_the_sim_card_is_active() {
        Assert.assertTrue("SIM card should be active", queryResponse.getBody().isActive());
    }

    @Then("I should see the SIM card is inactive")
    public void i_should_see_the_sim_card_is_inactive() {
        Assert.assertFalse("SIM card should be inactive", queryResponse.getBody().isActive());
    }
}