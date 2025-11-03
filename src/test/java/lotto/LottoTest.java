package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.model.Lotto;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {
    @Test
    @DisplayName("로또 번호의 개수가 6개가 아니면 예외가 발생한다")
    void createLottoByInvalidSize() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");

        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("당첨 번호와 일치하는 개수를 반환한다")
    void countMatchingNumbers() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        List<Integer> winningNumbers = List.of(1, 2, 3, 7, 8, 9);
        
        assertThat(lotto.countMatch(winningNumbers)).isEqualTo(3);
    }

    @Test
    @DisplayName("보너스 번호 포함 여부를 확인한다")
    void containsBonusNumber() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        
        assertThat(lotto.containsBonus(1)).isTrue();
        assertThat(lotto.containsBonus(7)).isFalse();
    }

    @Test
    @DisplayName("로또 번호를 반환한다")
    void getNumbers() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        Lotto lotto = new Lotto(numbers);
        
        assertThat(lotto.getNumbers()).hasSize(6);
        assertThat(lotto.getNumbers()).containsExactlyInAnyOrderElementsOf(numbers);
    }
}
