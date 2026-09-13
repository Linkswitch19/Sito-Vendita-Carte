package ch.supsi.cardgames.repository;

import ch.supsi.cardgames.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {


    User findUserByUsername(String username);
}


