package ch.supsi.cardgames.repository;

import ch.supsi.cardgames.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {

    List<Card> findCardsByNomeContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrAuthorContainingIgnoreCase(String nome, String description, String author);

    String nome(String nome);
}
