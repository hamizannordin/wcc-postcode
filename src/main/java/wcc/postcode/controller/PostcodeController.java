
package wcc.postcode.controller;

import wcc.postcode.service.PostcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wcc.postcode.body.request.UpdatePostcodeDetailRequest;

/**
 *
 * @author hamizan
 */
@RestController
@RequestMapping("/postcode")
public class PostcodeController {

    @Autowired
    private PostcodeService postcodeService;
    
    @RequestMapping(method = RequestMethod.GET, path = "/distance")
    public ResponseEntity<Object> getDistanceBetweenPostcode (
            @RequestParam("postcode-a") String postcodeA,
            @RequestParam("postcode-b") String postcodeB) {
        return ResponseEntity.ok(postcodeService.getDistanceBetweenPostcode(postcodeA, postcodeB));
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "/detail/{postcode}")
    public ResponseEntity<Object> updatePostcodeDetail (
            @PathVariable("postcode") String postcode,
            @RequestBody UpdatePostcodeDetailRequest request) {
        return ResponseEntity.ok(postcodeService.updatePostcodeDetail(postcode, request));
    }

}
