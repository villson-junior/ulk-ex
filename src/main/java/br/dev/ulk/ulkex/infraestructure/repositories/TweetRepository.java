package br.dev.ulk.ulkex.infraestructure.repositories;

import br.dev.ulk.ulkex.core.domain.entities.Tweet;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, UUID> {

}