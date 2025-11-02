package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import lotto.model.Rank;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {
    @ParameterizedTest
    @DisplayName("일치 개수와 보너스 일치 여부로 등수를 반환한다.")
    @CsvSource({
            "6, false, FIRST",
            "5, true, SECOND",
            "5, false, THIRD",
            "4, false, FOURTH",
            "3, false, FIFTH",
            "2, false, NONE",
            "1, false, NONE",
            "0, false, NONE"
    })
    void getRankByMatchCount(int matchCount, boolean matchBonus, Rank expectedRank) {
        Rank rank = Rank.valueOf(matchCount, matchBonus);
        assertThat(rank).isEqualTo(expectedRank);
    }

    @Test
    @DisplayName("1등의 상금은 2,000,000,000원이다.")
    void firstPrizeMoney() {
        assertThat(Rank.FIRST.getPrizeMoney()).isEqualTo(2_000_000_000);
    }

    @Test
    @DisplayName("2등의 상금은 30,000,000원이다.")
    void secondPrizeMoney() {
        assertThat(Rank.SECOND.getPrizeMoney()).isEqualTo(30_000_000);
    }

    @Test
    @DisplayName("5등의 상금은 5,000원이다.")
    void fifthPrizeMoney() {
        assertThat(Rank.FIFTH.getPrizeMoney()).isEqualTo(5_000);
    }

    @Test
    @DisplayName("NONE은 당첨이 아니다.")
    void noneIsNotWinning() {
        assertThat(Rank.NONE.isWinning()).isFalse();
    }

    @Test
    @DisplayName("1등은 당첨이다.")
    void firstIsWinning() {
        assertThat(Rank.FIRST.isWinning()).isTrue();
    }
}
