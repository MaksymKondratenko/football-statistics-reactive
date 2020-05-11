package com.maksymkondratenko.footballstats.match;

import com.maksymkondratenko.footballstats.score.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static com.maksymkondratenko.footballstats.league.League.*;
import static com.maksymkondratenko.footballstats.team.Team.*;

@Component
@RequiredArgsConstructor
public class MatchInitializer implements ApplicationRunner {
    private final MatchRepository repo;

    @Override
    public void run(ApplicationArguments args) {
        repo.deleteAll()
                .thenMany(Flux.just(
                        new Match(UPL, DYNAMO, SHAKHTAR, new Score(1, 0)),
                        new Match(APL, MAN_CITY, MAN_UNITED, new Score(1, 2)),
                        new Match(BBVA, REAL_MADRID, BARCA, new Score(2, 1)),
                        new Match(SERIE_A, INTER, MILAN, new Score(1, 2))))
                .flatMap(repo::save)
                .log()
                .subscribe();
    }

    public void run() {
        run(null);
    }
}
