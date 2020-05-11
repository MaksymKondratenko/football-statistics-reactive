package com.maksymkondratenko.footballstats.score;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private Integer hostScore;
    private Integer guestScore;
    private Result result;

    public Score(Integer hostScore, Integer guestScore) {
        this.hostScore = hostScore;
        this.guestScore = guestScore;
        this.result = resolveResult();
    }

    public Result getResult() {
        return Optional.ofNullable(result).orElseGet(this::resolveResult);
    }

    private Result resolveResult() {
        if (hostScore > guestScore)
            return Result.WIN;
        else if (hostScore < guestScore)
            return Result.LOSS;
        else return Result.DRAW;
    }

    enum Result {
        WIN, LOSS, DRAW
    }
}
