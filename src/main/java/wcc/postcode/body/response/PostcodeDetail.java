
package wcc.postcode.body.response;

/**
 *
 * @author hamizan
 */
public class PostcodeDetail {

    private String postcode;
    private String latitude;
    private String longitude;

    public PostcodeDetail() {
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
