package lotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.model.Lotto;
import lotto.model.LottoResult;
import lotto.model.Rank;
import lotto.model.WinningLotto;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class LottoResultTest {
    private WinningLotto winningLotto;

    @BeforeEach
    void setUp() {
        winningLotto = new WinningLotto(List.of(1, 2, 3, 4, 5, 6), 7);
    }

    @Test
    @DisplayName("당첨 통계를 정확하게 집계한다")
    void calculateResult() {
        List<Lotto> lottos = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),  // 1등
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),  // 2등
                new Lotto(List.of(1, 2, 3, 4, 5, 8)),  // 3등
                new Lotto(List.of(1, 2, 3, 4, 8, 9)),  // 4등
                new Lotto(List.of(1, 2, 3, 8, 9, 10))  // 5등
        );

        LottoResult result = new LottoResult(lottos, winningLotto);

        assertThat(result.getCount(Rank.FIRST)).isEqualTo(1);
        assertThat(result.getCount(Rank.SECOND)).isEqualTo(1);
        assertThat(result.getCount(Rank.THIRD)).isEqualTo(1);
        assertThat(result.getCount(Rank.FOURTH)).isEqualTo(1);
        assertThat(result.getCount(Rank.FIFTH)).isEqualTo(1);
    }

    @Test
    @DisplayName("당첨되지 않은 로또도 집계한다")
    void calculateNoneRank() {
        List<Lotto> lottos = List.of(
                new Lotto(List.of(11, 12, 13, 14, 15, 16)),
                new Lotto(List.of(21, 22, 23, 24, 25, 26))
        );

        LottoResult result = new LottoResult(lottos, winningLotto);

        assertThat(result.getCount(Rank.NONE)).isEqualTo(2);
    }

    @Test
    @DisplayName("총 당첨 금액을 계산한다")
    void calculateTotalPrizeMoney() {
        List<Lotto> lottos = List.of(
                new Lotto(List.of(1, 2, 3, 8, 9, 10)),  // 5등: 5,000원
                new Lotto(List.of(1, 2, 3, 4, 8, 9))    // 4등: 50,000원
        );

        LottoResult result = new LottoResult(lottos, winningLotto);
        
        assertThat(result.getTotalPrizeMoney()).isEqualTo(55_000);
    }

    @Test
    @DisplayName("당첨되지 않으면 총 당첨 금액은 0원이다")
    void calculateTotalPrizeMoneyWithNoWin() {
        List<Lotto> lottos = List.of(
                new Lotto(List.of(11, 12, 13, 14, 15, 16))
        );

        LottoResult result = new LottoResult(lottos, winningLotto);
        
        assertThat(result.getTotalPrizeMoney()).isEqualTo(0);
    }

    @Test
    @DisplayName("수익률을 계산한다 - 예제 케이스")
    void calculateProfitRate() {
        List<Lotto> lottos = List.of(
                new Lotto(List.of(1, 2, 3, 8, 9, 10))  // 5등: 5,000원
        );

        LottoResult result = new LottoResult(lottos, winningLotto);
        double profitRate = result.calculateProfitRate(8000);
        
        assertThat(profitRate).isEqualTo(62.5);
    }

    @Test
    @DisplayName("수익률을 계산한다 - 100% 케이스")
    void calculateProfitRate100Percent() {
        List<Lotto> lottos = List.of(
                new Lotto(List.of(1, 2, 3, 8, 9, 10))  // 5등: 5,000원
        );

        LottoResult result = new LottoResult(lottos, winningLotto);
        double profitRate = result.calculateProfitRate(5000);
        
        assertThat(profitRate).isEqualTo(100.0);
    }

    @Test
    @DisplayName("수익률을 계산한다 - 손실 케이스")
    void calculateProfitRateLoss() {
        List<Lotto> lottos = List.of(
                new Lotto(List.of(11, 12, 13, 14, 15, 16))  // 당첨 없음
        );

        LottoResult result = new LottoResult(lottos, winningLotto);
        double profitRate = result.calculateProfitRate(1000);
        
        assertThat(profitRate).isEqualTo(0.0);
    }
}
