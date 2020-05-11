package com.maksymkondratenko.footballstats.match

import com.maksymkondratenko.footballstats.score.Score
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Unroll

import static com.maksymkondratenko.footballstats.league.League.BUNDESLIGA
import static com.maksymkondratenko.footballstats.match.MatchController.BASE_URL
import static com.maksymkondratenko.footballstats.team.Team.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MatchControllerTest extends Specification {
    @Autowired
    WebTestClient client
    @Autowired
    MatchInitializer initializer

    def setup() { initializer.run() }

    def save() {
        given:
        def matchToSave = new Match(BUNDESLIGA, BAYERN, RED_BULL, new Score(3, 3))
        expect:
        client.put()
                .uri(BASE_URL)
                .body(Mono.just(matchToSave), Match.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath('$.id').isNotEmpty()
        cleanup:
        client.delete()
                .uri(BASE_URL + matchToSave.id)
                .exchange()
    }


    def 'find all'() {
        expect:
        client.get()
                .uri("$BASE_URL/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Match.class)
                .hasSize(4)
    }

    @Unroll
    def "find by #resource team when team exists"() {
        expect:
        client.get()
                .uri("$BASE_URL/$resource/$team")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath('$').isArray()
                .jsonPath('$[0]').exists()
                .jsonPath('$[0].id').isNotEmpty()
                .jsonPath('$[0].' + resource).isEqualTo(team)
        where:
        resource | team
        'host'   | DYNAMO.name()
        'guest'  | MAN_UNITED.name()

    }

    def 'should find by score'() {
        expect:
        client.get()
                .uri("$BASE_URL/score?hostScore=1&guestScore=0")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath('$').isArray()
                .jsonPath('$[0]').exists()
    }
}