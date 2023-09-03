
package wcc.postcode.service;

import java.util.ArrayList;
import wcc.postcode.entity.PostcodeDetail;
import wcc.postcode.exception.BadRequestException;
import wcc.postcode.exception.NotFoundException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wcc.postcode.body.request.UpdatePostcodeDetailRequest;
import wcc.postcode.body.response.DistanceBetweenPostcodeResponse;
import wcc.postcode.body.response.PostcodeDetail;
import wcc.postcode.repository.PostcodeRepository;
import wcc.postcode.util.DistanceCalculatorUtil;

/**
 *
 * @author hamizan
 */
@Service
public class PostcodeService {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String unit = "km";
    private final double latitudeBoundValue = 90;
    private final double longitudeBoundValue = 180;
    
    @Autowired
    private PostcodeRepository postcodeRepository;
    @Autowired
    private DistanceCalculatorUtil distanceCalculatorUtil;

    public DistanceBetweenPostcodeResponse getDistanceBetweenPostcode (
            String queryPostcodeA, String queryPostcodeB) {
        
        if(queryPostcodeA == null || queryPostcodeA.isEmpty() 
                || queryPostcodeB == null || queryPostcodeB.isEmpty())
            throw new BadRequestException("Requires two postcode");
        
        PostcodeDetail postcodeA = postcodeRepository.findByPostcode(queryPostcodeA);
        PostcodeDetail postcodeB = postcodeRepository.findByPostcode(queryPostcodeB);
        
        if(postcodeA == null)
            throw new NotFoundException("Postcode not found: " + queryPostcodeA);
        
        if(postcodeB == null)
            throw new NotFoundException("Postcode not found: " + queryPostcodeB);
        
        double distance = distanceCalculatorUtil.calculateDistance(
                postcodeA.getLatitude(), postcodeA.getLongitude(), 
                postcodeB.getLatitude(), postcodeB.getLongitude());
        
        log.info("distance between {} to {} : {}", 
                queryPostcodeA, queryPostcodeB, distance);
        
        DistanceBetweenPostcodeResponse postcodeResponse = 
                new DistanceBetweenPostcodeResponse();
        
        postcodeResponse.setDistance(distance);
        postcodeResponse.setPostcodeList(createPostcodeDetailList(
                postcodeA, postcodeB));
        postcodeResponse.setUnit(unit);
        
        return postcodeResponse;
    }
    
    public PostcodeDetail updatePostcodeDetail (
            String postcodeString, 
            UpdatePostcodeDetailRequest request) {
        
        if(postcodeString.isBlank())
            throw new BadRequestException("Postcode contains no value");
        
        double latitude = request.getLatitude();
        
        if(latitude > latitudeBoundValue || latitude < -latitudeBoundValue)
            throw new BadRequestException("Invalid latitude value");
        
        double longitude = request.getLongitude();
        
        if(longitude > longitudeBoundValue || longitude < -longitudeBoundValue)
            throw new BadRequestException("Invalid longitude value");
        
        PostcodeDetail postcode = postcodeRepository.findByPostcode(postcodeString);
        
        if(postcode == null)
            throw new NotFoundException("Postcode not found");
        
        log.info("updating postcode {} coordinates...", postcodeString);
        
        postcode.setLatitude(latitude);
        postcode.setLongitude(longitude);
        
        postcode = postcodeRepository.save(postcode);
        
        return postcode;
    }
    
    private List<PostcodeDetail> createPostcodeDetailList (
            PostcodeDetail postcodeA, PostcodeDetail postcodeB) {
        List<PostcodeDetail> postcodeDetailList = new ArrayList<>();
        postcodeDetailList.add(createPostcodeDetail(postcodeA));
        postcodeDetailList.add(createPostcodeDetail(postcodeB));
        return postcodeDetailList;
    }
    
    private PostcodeDetail createPostcodeDetail (PostcodeDetail postcode) {
        PostcodeDetail postcodeDetail = new PostcodeDetail();
        postcodeDetail.setPostcode(postcode.getPostcode());
        postcodeDetail.setLatitude(Double.toString(postcode.getLatitude()));
        postcodeDetail.setLongitude(Double.toString(postcode.getLongitude()));
        return postcodeDetail;
    }
}
