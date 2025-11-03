package lotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.model.Lotto;
import lotto.model.Rank;
import lotto.model.WinningLotto;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class WinningLottoTest {
    private WinningLotto winningLotto;

    @BeforeEach
    void setUp() {
        winningLotto = new WinningLotto(List.of(1, 2, 3, 4, 5, 6), 7);
    }

    @Test
    @DisplayName("6개 모두 일치하면 1등이다")
    void matchFirstRank() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        
        assertThat(winningLotto.match(lotto)).isEqualTo(Rank.FIRST);
    }

    @Test
    @DisplayName("5개 일치하고 보너스 번호가 일치하면 2등이다")
    void matchSecondRank() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 7));
        
        assertThat(winningLotto.match(lotto)).isEqualTo(Rank.SECOND);
    }

    @Test
    @DisplayName("5개만 일치하면 3등이다")
    void matchThirdRank() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 8));
        
        assertThat(winningLotto.match(lotto)).isEqualTo(Rank.THIRD);
    }

    @Test
    @DisplayName("4개 일치하면 4등이다")
    void matchFourthRank() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 8, 9));
        
        assertThat(winningLotto.match(lotto)).isEqualTo(Rank.FOURTH);
    }

    @Test
    @DisplayName("3개 일치하면 5등이다")
    void matchFifthRank() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 8, 9, 10));
        
        assertThat(winningLotto.match(lotto)).isEqualTo(Rank.FIFTH);
    }

    @Test
    @DisplayName("2개 이하 일치하면 당첨되지 않는다")
    void matchNone() {
        Lotto lotto = new Lotto(List.of(1, 2, 8, 9, 10, 11));
        
        assertThat(winningLotto.match(lotto)).isEqualTo(Rank.NONE);
    }

    @Test
    @DisplayName("보너스 번호만 일치해도 당첨되지 않는다")
    void matchOnlyBonus() {
        Lotto lotto = new Lotto(List.of(7, 8, 9, 10, 11, 12));
        
        assertThat(winningLotto.match(lotto)).isEqualTo(Rank.NONE);
    }
}
