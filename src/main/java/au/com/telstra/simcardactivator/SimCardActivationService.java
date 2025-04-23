package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class SimCardActivationService {
    private final RestTemplate restTemplate;
    private final SimCardActivationRepository repository;
    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";

    @Autowired
    public SimCardActivationService(RestTemplate restTemplate, SimCardActivationRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public boolean activateSimCard(String iccid, String customerEmail) {
        ActuatorRequest request = new ActuatorRequest(iccid);
        ActuatorResponse response = restTemplate.postForObject(ACTUATOR_URL, request, ActuatorResponse.class);

        boolean success = response != null && response.isSuccess();

        // Save activation record to database
        SimCardActivation activation = new SimCardActivation(iccid, customerEmail, success);
        repository.save(activation);

        System.out.println("Activation result for ICCID " + iccid + ": " + success);
        return success;
    }

    public SimCardActivationResponse getActivation(Long id) {
        Optional<SimCardActivation> activation = repository.findById(id);
        return activation.map(a -> new SimCardActivationResponse(
                a.getIccid(),
                a.getCustomerEmail(),
                a.isActive())).orElse(null);
    }
}