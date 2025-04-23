package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimCardActivationController {
    private final SimCardActivationService activationService;

    @Autowired
    public SimCardActivationController(SimCardActivationService activationService) {
        this.activationService = activationService;
    }

    @PostMapping("/activate")
    public ResponseEntity<Boolean> activateSimCard(@RequestBody SimCardActivationRequest request) {
        boolean result = activationService.activateSimCard(request.getIccid(), request.getCustomerEmail());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/simcard")
    public ResponseEntity<SimCardActivationResponse> getSimCardActivation(@RequestParam Long simCardId) {
        SimCardActivationResponse activation = activationService.getActivation(simCardId);
        if (activation != null) {
            return ResponseEntity.ok(activation);
        }
        return ResponseEntity.notFound().build();
    }
}