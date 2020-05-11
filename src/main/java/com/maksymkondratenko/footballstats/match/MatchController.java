package com.maksymkondratenko.footballstats.match;

import com.maksymkondratenko.footballstats.score.Score;
import com.maksymkondratenko.footballstats.team.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping(MatchController.BASE_URL)
@RequiredArgsConstructor
public class MatchController {
    public static final String BASE_URL = "/matches";
    private final MatchRepository repo;

    @PutMapping
    public Mono<ResponseEntity<Match>> save(@RequestBody Match match) {
        return repo.save(match)
                .map(savedMatch -> ResponseEntity.status(HttpStatus.CREATED).body(savedMatch));
    }

    @GetMapping("/all")
    public Flux<Match> findAll() {
        return repo.findAll();
    }

    @GetMapping("/host/{team}")
    public Flux<Match> findByHostTeam(@PathVariable Team team) {
        return repo.findByHost(team);
    }

    @GetMapping("/guest/{team}")
    public Flux<Match> findByGuestTeam(@PathVariable Team team) {
        return repo.findByGuest(team);
    }

    @GetMapping("/score")
    public Flux<Match> findByScore(@RequestParam("hostScore") Integer hostScore,
                                   @RequestParam("guestScore") Integer guestScore) {
        return repo.findByScore(new Score(hostScore, guestScore));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return repo.deleteById(id);
    }
}
