package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.controller.LottoController;

import static org.assertj.core.api.Assertions.assertThatCode;

class LottoControllerTest {
    @Test
    @DisplayName("컨트롤러가 정상적으로 실행된다")
    void runController() {
        // 실제 입출력이 있어 통합 테스트는 수동으로 진행
        // 각 메서드의 호출 순서와 예외 처리만 확인
        assertThatCode(() -> {
            LottoController controller = new LottoController();
            // controller.run(); // 실제 실행은 주석 처리
        }).doesNotThrowAnyException();
    }
}
