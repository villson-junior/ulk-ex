package br.dev.ulk.ulkex.application.v1.controllers;

import br.dev.ulk.ulkex.application.v1.payloads.CreateTweetDTO;
import br.dev.ulk.ulkex.application.v1.payloads.FeedDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface TweetControllerV1 {

    @GetMapping("/feed")
    ResponseEntity<FeedDTO> feed(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    );

    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(
        @RequestBody CreateTweetDTO dto,
        JwtAuthenticationToken token
    );

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> deleteTweet(
        @PathVariable("id") String tweetId,
        JwtAuthenticationToken token
    );

}