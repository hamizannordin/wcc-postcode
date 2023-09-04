
package wcc.postcode.body.response;

import java.util.List;
import wcc.postcode.entity.PostcodeDetail;

/**
 *
 * @author hamizan
 */
public class DistanceBetweenPostcodeResponse {

    private List<PostcodeDetail> postcodeList;
    private double distance;
    private String unit;

    public DistanceBetweenPostcodeResponse() {
    }

    public List<PostcodeDetail> getPostcodeList() {
        return postcodeList;
    }

    public void setPostcodeList(List<PostcodeDetail> postcodeList) {
        this.postcodeList = postcodeList;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
