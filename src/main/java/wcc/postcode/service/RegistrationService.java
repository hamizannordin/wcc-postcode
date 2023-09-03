
package wcc.postcode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import wcc.postcode.body.request.AuthenticationRequest;
import wcc.postcode.entity.User;
import wcc.postcode.exception.BadRequestException;
import wcc.postcode.repository.UserRepository;

/**
 *
 * @author hamizan
 */
@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;
    
    public String registerNewUser (AuthenticationRequest request) {
        
        if(request.getUsername() == null || request.getUsername().isEmpty() ||
                request.getUsername().isBlank())
            throw new BadRequestException("Username cannot be null/empty");
        
        if(request.getPassword() == null || request.getUsername().isEmpty() ||
                request.getUsername().isBlank())
            throw new BadRequestException("Password cannot be null/empty");
        
        if(request.getPassword().length() < 8)
            throw new BadRequestException("Password must be at least 8 characters");
        
        User user = userRepository.findByUsername(request.getUsername());
        
        if(user != null)
            throw new BadRequestException("Username already taken");
        
        user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        
        userRepository.save(user);
        
        return user.getUsername();
    }
}
