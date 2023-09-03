
package wcc.postcode.repository;

import wcc.postcode.entity.Postcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hamizan
 */
@Repository
public interface PostcodeRepository extends JpaRepository<Postcode, Integer> {

    Postcode findByPostcode(String postcode);
}
