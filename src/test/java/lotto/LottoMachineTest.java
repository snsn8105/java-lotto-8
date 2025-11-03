package lotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import lotto.model.Lotto;
import lotto.model.LottoMachine;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class LottoMachineTest {
    private LottoMachine lottoMachine;

    @BeforeEach
    void setUp() {
        lottoMachine = new LottoMachine();
    }

    @ParameterizedTest
    @DisplayName("구입 금액에 맞는 로또 개수를 계산한다")
    @CsvSource({
            "1000, 1",
            "8000, 8",
            "14000, 14"
    })
    void calculateLottoCount(int money, int expectedCount) {
        assertThat(lottoMachine.calculateLottoCount(money)).isEqualTo(expectedCount);
    }

    @Test
    @DisplayName("구입 금액만큼 로또를 발행한다")
    void purchaseLottos() {
        List<Lotto> lottos = lottoMachine.purchaseLottos(3000);
        
        assertThat(lottos).hasSize(3);
    }

    @Test
    @DisplayName("발행된 로또는 각각 6개의 번호를 가진다")
    void lottoHasSixNumbers() {
        List<Lotto> lottos = lottoMachine.purchaseLottos(2000);
        
        for (Lotto lotto : lottos) {
            assertThat(lotto.getNumbers()).hasSize(6);
        }
    }

    @Test
    @DisplayName("발행된 로또의 번호는 1~45 범위이다")
    void lottoNumbersInRange() {
        List<Lotto> lottos = lottoMachine.purchaseLottos(1000);
        
        lottos.get(0).getNumbers().forEach(number -> {
            assertThat(number).isBetween(1, 45);
        });
    }

    @Test
    @DisplayName("발행된 로또의 번호는 중복되지 않는다")
    void lottoNumbersNoDuplicate() {
        List<Lotto> lottos = lottoMachine.purchaseLottos(1000);
        List<Integer> numbers = lottos.get(0).getNumbers();
        
        assertThat(numbers).doesNotHaveDuplicates();
    }
}
