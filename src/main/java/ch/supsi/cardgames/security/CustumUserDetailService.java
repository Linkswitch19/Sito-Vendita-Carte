package ch.supsi.cardgames.security;

import ch.supsi.cardgames.models.User;
import ch.supsi.cardgames.repository.UserRepository;
import ch.supsi.cardgames.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustumUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public CustumUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= this.userRepository.findUserByUsername(username); //collegamento e qui decido in base a cosa voglio fare il login
        if (user==null) throw  new UsernameNotFoundException("Non trovato");
        List<GrantedAuthority> auth= AuthorityUtils.createAuthorityList(user.getRole().getName()); //ruoli del utente
        return  new org.springframework.security.core.userdetails.User(username,user.getPassword(),true,true,true,true,auth); //login
    }


}
