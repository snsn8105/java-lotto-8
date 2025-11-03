package lotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import lotto.utils.InputValidator;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputValidatorTest {
    private InputValidator validator;

    @BeforeEach
    void setUp() {
        validator = new InputValidator();
    }

    // 구입 금액 검증
    @Test
    @DisplayName("구입 금액이 1000원 단위가 아니면 예외가 발생한다")
    void validatePurchaseAmountNotDivisible() {
        assertThatThrownBy(() -> validator.validatePurchaseAmount(1500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("1,000원 단위");
    }

    @ParameterizedTest
    @DisplayName("구입 금액이 0원 이하면 예외가 발생한다")
    @ValueSource(ints = {0, -1000, -100})
    void validatePurchaseAmountNotPositive(int amount) {
        assertThatThrownBy(() -> validator.validatePurchaseAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @ParameterizedTest
    @DisplayName("유효한 구입 금액은 예외가 발생하지 않는다")
    @ValueSource(ints = {1000, 8000, 14000})
    void validatePurchaseAmountValid(int amount) {
        assertThatCode(() -> validator.validatePurchaseAmount(amount))
                .doesNotThrowAnyException();
    }

    // 로또 번호 검증
    @Test
    @DisplayName("로또 번호가 6개가 아니면 예외가 발생한다")
    void validateLottoNumbersInvalidCount() {
        assertThatThrownBy(() -> validator.validateLottoNumbers(List.of(1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("6개");
    }

    @ParameterizedTest
    @DisplayName("로또 번호가 1~45 범위를 벗어나면 예외가 발생한다")
    @ValueSource(ints = {0, 46, -1, 100})
    void validateLottoNumbersOutOfRange(int invalidNumber) {
        assertThatThrownBy(() -> 
                validator.validateLottoNumbers(List.of(1, 2, 3, 4, 5, invalidNumber)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("1부터 45");
    }

    @Test
    @DisplayName("로또 번호에 중복이 있으면 예외가 발생한다")
    void validateLottoNumbersDuplicate() {
        assertThatThrownBy(() -> 
                validator.validateLottoNumbers(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("중복");
    }

    @Test
    @DisplayName("유효한 로또 번호는 예외가 발생하지 않는다")
    void validateLottoNumbersValid() {
        assertThatCode(() -> validator.validateLottoNumbers(List.of(1, 2, 3, 4, 5, 6)))
                .doesNotThrowAnyException();
    }

    // 보너스 번호 검증
    @ParameterizedTest
    @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다")
    @ValueSource(ints = {0, 46, -1, 100})
    void validateBonusNumberOutOfRange(int invalidBonus) {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatThrownBy(() -> 
                validator.validateBonusNumber(invalidBonus, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("1부터 45");
    }

    @Test
    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다")
    void validateBonusNumberDuplicate() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatThrownBy(() -> validator.validateBonusNumber(6, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("중복");
    }

    @Test
    @DisplayName("유효한 보너스 번호는 예외가 발생하지 않는다")
    void validateBonusNumberValid() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatCode(() -> validator.validateBonusNumber(7, winningNumbers))
                .doesNotThrowAnyException();
    }
}
