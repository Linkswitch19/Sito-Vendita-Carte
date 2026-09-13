package ch.supsi.cardgames.service;

import ch.supsi.cardgames.models.User;
import ch.supsi.cardgames.models.UserRole;
import ch.supsi.cardgames.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder BCPasswordEncoder;
    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder BCPasswordEncoder) {
        this.userRepository = userRepository;
        this.BCPasswordEncoder =BCPasswordEncoder;
    }

    public User getUser(int id){
       return this.userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUSer(){
        return this.userRepository.findAll();
    }

    public User createUser(User user) throws BadRequestException {

        checkUserValidity(user);
        user.setRole(UserRole.USER);
        String encodedPw = BCPasswordEncoder.encode(user.getPassword()); // senza pure a mano non funziona
        user.setPassword(encodedPw); //password
        return userRepository.save(user); //salva
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    private void checkUserValidity(User user) throws BadRequestException { //controllo parametri vuoti
        if (user == null) {
            throw new BadRequestException("User cannot be null");
        }

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new BadRequestException("First name cannot be empty");
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new BadRequestException("Last name cannot be empty");
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new BadRequestException("Username cannot be empty");
        }

        // should also check password rules
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new BadRequestException("Password cannot be empty");
        }

        // check if the username already exists
        if (this.findUserByUsername(user.getUsername()) != null) {
            throw new BadRequestException("Username already exists");
        }

    }
}
