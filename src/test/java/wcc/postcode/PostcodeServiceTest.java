package wcc.postcode;

import wcc.postcode.entity.Postcode;
import wcc.postcode.exception.BadRequestException;
import wcc.postcode.exception.NotFoundException;
import wcc.postcode.service.PostcodeService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import wcc.postcode.body.request.UpdatePostcodeDetailRequest;
import wcc.postcode.body.response.DistanceBetweenPostcodeResponse;
import wcc.postcode.repository.PostcodeRepository;
import wcc.postcode.util.DistanceCalculatorUtil;

/**
 * 
 * @author hamizan
 */
@ExtendWith(MockitoExtension.class)
public class PostcodeServiceTest {
    
    @InjectMocks
    private PostcodeService postcodeService;
    @Mock
    private PostcodeRepository postcodeRepository;
    @Mock
    private DistanceCalculatorUtil distanceCalculatorUtil;

    /**
     * test-case 00 : getDistanceBetweenPostcodeFailParamIncomplete
     */
    @Test
    public void getDistanceBetweenPostcodeFailParamIncomplete() {
        
        String postcodeA = "";
        String postcodeB = "ABC 00";
        String message = "Requires two postcode";
        
        assertTrue(assertThrows(BadRequestException.class,() -> 
                postcodeService.getDistanceBetweenPostcode(postcodeA, postcodeB)
        ).getMessage().equals(message));
    }

    /**
     * test-case 01 : getDistanceBetweenPostcodeFailPostcodeNotFound
     */
    @Test
    public void getDistanceBetweenPostcodeFailPostcodeNotFound() {
        
        String postcodeA = "ABC 01a";
        String postcodeB = "ABC 01b";
        String message = "Postcode not found";
        
        assertTrue(assertThrows(NotFoundException.class,() -> 
                postcodeService.getDistanceBetweenPostcode(postcodeA, postcodeB)
        ).getMessage().startsWith(message));
    }

    /**
     * test-case 02 : getDistanceBetweenPostcodeSuccess
     */
    @Test
    public void getDistanceBetweenPostcodeSuccess() {
        
        String postcodeA = "ABC 02a";
        String postcodeB = "ABC 02b";
        double latitudeA = 0.12;
        double longitudeA = 1.23;
        double latitudeB = 0.22;
        double longitudeB = 1.33;
        String responseUnit = "km";
        Postcode postcodeEntityA = new Postcode(postcodeA, latitudeA, longitudeA);
        Postcode postcodeEntityB = new Postcode(postcodeB, latitudeB, longitudeB);
        
        when(postcodeRepository.findByPostcode(postcodeA))
                .thenReturn(postcodeEntityA);
        when(postcodeRepository.findByPostcode(postcodeB))
                .thenReturn(postcodeEntityB);
        
        when(distanceCalculatorUtil.calculateDistance(
                any(Double.class), any(Double.class), 
                any(Double.class), any(Double.class))).thenCallRealMethod();
        
        DistanceBetweenPostcodeResponse response = postcodeService
                .getDistanceBetweenPostcode(postcodeA, postcodeB);
        
        assertTrue(response.getDistance() >= 0);
        assertTrue(response.getPostcodeList().size() == 2);
        assertTrue(response.getPostcodeList().get(0).getPostcode().equals(postcodeA));
        assertTrue(response.getPostcodeList().get(0).getLatitude().equals(Double.toString(latitudeA)));
        assertTrue(response.getPostcodeList().get(0).getLongitude().equals(Double.toString(longitudeA)));
        assertTrue(response.getPostcodeList().get(1).getPostcode().equals(postcodeB));
        assertTrue(response.getPostcodeList().get(1).getLatitude().equals(Double.toString(latitudeB)));
        assertTrue(response.getPostcodeList().get(1).getLongitude().equals(Double.toString(longitudeB)));
        assertTrue(response.getUnit().equals(responseUnit));
    }

    /**
     * test-case 03 : updatePostcodeDetailFailPostcodeBlank
     */
    @Test
    public void updatePostcodeDetailFailPostcodeBlank() {
        
        String postcode = " ";
        String message = "Postcode contains no value";
        UpdatePostcodeDetailRequest request = new UpdatePostcodeDetailRequest();
        
        assertTrue(assertThrows(BadRequestException.class,()->{
            postcodeService.updatePostcodeDetail(postcode, request);
        }).getMessage().equals(message));
    }

    /**
     * test-case 04 : updatePostcodeDetailFailInvalidLatitudeValue
     */
    @Test
    public void updatePostcodeDetailFailInvalidLatitudeValue() {
        
        String postcode = "ABC 04";
        String message = "Invalid latitude value";
        UpdatePostcodeDetailRequest request = new UpdatePostcodeDetailRequest();
        request.setLatitude(999999);
        request.setLongitude(0.12345678);
        
        assertTrue(assertThrows(BadRequestException.class,()->{
            postcodeService.updatePostcodeDetail(postcode, request);
        }).getMessage().equals(message));
    }

    /**
     * test-case 05 : updatePostcodeDetailFailInvalidLongitudeValue
     */
    @Test
    public void updatePostcodeDetailFailInvalidLongitudeValue() {
        
        String postcode = "ABC 05";
        String message = "Invalid longitude value";
        UpdatePostcodeDetailRequest request = new UpdatePostcodeDetailRequest();
        request.setLatitude(1.234);
        request.setLongitude(181.78);
        
        assertTrue(assertThrows(BadRequestException.class,()->{
            postcodeService.updatePostcodeDetail(postcode, request);
        }).getMessage().equals(message));
    }

    /**
     * test-case 06 : updatePostcodeDetailFailPostcodeNotFound
     */
    @Test
    public void updatePostcodeDetailFailPostcodeNotFound() {
        
        String postcode = "ABC 06";
        String message = "Postcode not found";
        UpdatePostcodeDetailRequest request = new UpdatePostcodeDetailRequest();
        
        assertTrue(assertThrows(NotFoundException.class,()->{
            postcodeService.updatePostcodeDetail(postcode, request);
        }).getMessage().equals(message));
    }

    /**
     * test-case 07 : updatePostcodeDetailSuccess
     */
    @Test
    public void updatePostcodeDetailSuccess() {
        
        String postcode = "ABC 05";
        double latitude = 1.234;
        double longitude = 0.1234567;
        UpdatePostcodeDetailRequest request = new UpdatePostcodeDetailRequest();
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        
        Postcode postcodeEntity = new Postcode(postcode, latitude, longitude);
        
        when(postcodeRepository.findByPostcode(postcode)).thenReturn(postcodeEntity);
        
        when(postcodeRepository.save(postcodeEntity)).thenReturn(postcodeEntity);
        
        Postcode postcodeResponse = postcodeService.updatePostcodeDetail(postcode, request);
        
        assertTrue(postcodeResponse.getLatitude() == latitude);
        assertTrue(postcodeResponse.getLongitude()== longitude);
        assertTrue(postcodeResponse.getPostcode().equals(postcode));
    }
}
