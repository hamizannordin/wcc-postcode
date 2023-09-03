
package wcc.postcode.repository;

import wcc.postcode.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hamizan
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByUsername(String username);
}
