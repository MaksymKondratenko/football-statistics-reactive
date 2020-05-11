package com.maksymkondratenko.footballstats.match;

import com.maksymkondratenko.footballstats.score.Score;
import com.maksymkondratenko.footballstats.team.Team;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MatchRepository extends ReactiveMongoRepository<Match, String> {
    Flux<Match> findByHost(Team host);
    Flux<Match> findByGuest(Team guest);
    Flux<Match> findByScore(Score score);
}
