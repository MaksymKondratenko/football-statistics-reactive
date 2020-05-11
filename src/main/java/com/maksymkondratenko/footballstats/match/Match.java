package com.maksymkondratenko.footballstats.match;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maksymkondratenko.footballstats.league.League;
import com.maksymkondratenko.footballstats.score.Score;
import com.maksymkondratenko.footballstats.team.Team;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@EqualsAndHashCode
@Document
public class Match {
    @Id
    @Setter
    private String id;
    private final League league;
    private final Team host;
    private final Team guest;
    private final Score score;

    @JsonCreator
    public Match(@JsonProperty("league") League league,
                 @JsonProperty("host") Team host,
                 @JsonProperty("guest") Team guest,
                 @JsonProperty("score") Score score) {
        this.league = league;
        this.host = host;
        this.guest = guest;
        this.score = score;
    }
}
