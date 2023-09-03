package wcc.postcode.service;

import wcc.postcode.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author hamizan
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        wcc.postcode.entity.User userEntity = userRepository.findByUsername(username);
        
        if (userEntity.getUsername().equals(username)) {
            return new User(userEntity.getUsername(),
                    userEntity.getPassword(),
                    new ArrayList<>());
                    //Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
