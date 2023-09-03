package wcc.postcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wcc.postcode.body.request.AuthenticationRequest;
import wcc.postcode.service.RegistrationService;

/**
 *
 * @author hamizan
 */
@RestController
@RequestMapping("/register")
public class RegistrationController {
    
    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<?> registerNewUser(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(registrationService.registerNewUser(request));
    }
}
