package br.dev.ulk.ulkex.application.v1.controllers.implementations;


import br.dev.ulk.ulkex.application.v1.controllers.TweetControllerV1;
import br.dev.ulk.ulkex.application.v1.payloads.CreateTweetDTO;
import br.dev.ulk.ulkex.application.v1.payloads.FeedDTO;
import br.dev.ulk.ulkex.application.v1.payloads.FeedItemDTO;
import br.dev.ulk.ulkex.core.domain.entities.Tweet;
import br.dev.ulk.ulkex.core.domain.entities.User;
import br.dev.ulk.ulkex.core.domain.enumerations.RoleEnum;
import br.dev.ulk.ulkex.infraestructure.repositories.TweetRepository;
import br.dev.ulk.ulkex.infraestructure.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TweetControllerV1Impl implements TweetControllerV1 {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetControllerV1Impl(
        TweetRepository tweetRepository,
        UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<FeedDTO> feed(int page, int pageSize) {

        Page<FeedItemDTO> tweets = tweetRepository.findAll(
                PageRequest.of(page, pageSize, Direction.DESC, "createdAt"))
            .map(tweet ->
                new FeedItemDTO(
                    tweet.getId().toString(),
                    tweet.getContent(),
                    tweet.getUser().getUsername())
            );

        return ResponseEntity.ok(
            new FeedDTO(tweets.getContent(), page, pageSize, tweets.getTotalPages(),
                tweets.getTotalElements()));
    }

    @Override
    public ResponseEntity<Void> createTweet(CreateTweetDTO dto, JwtAuthenticationToken token) {

        Optional<User> optUser = userRepository.findById(UUID.fromString(token.getName()));

        Tweet tweet = Tweet.builder()
            .user(optUser.get())
            .content(dto.getContent())
            .build();

        tweetRepository.save(tweet);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteTweet(String tweetId, JwtAuthenticationToken token) {

        Optional<User> optUser = userRepository.findById(UUID.fromString(token.getName()));
        Tweet tweet = tweetRepository.findById(UUID.fromString(tweetId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean isAdmin = optUser.get().getRoles()
            .stream()
            .anyMatch(role -> role.getName().getDescription()
                .equalsIgnoreCase(RoleEnum.ADMIN.getDescription()));

        if (isAdmin || tweet.getUser().getId().equals(UUID.fromString(token.getName()))) {
            tweetRepository.deleteById(UUID.fromString(tweetId));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok().build();
    }
}