
package wcc.postcode.repository;

import wcc.postcode.entity.PostcodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hamizan
 */
@Repository
public interface PostcodeRepository extends JpaRepository<PostcodeDetail, Integer> {

    PostcodeDetail findByPostcode(String postcode);
}
