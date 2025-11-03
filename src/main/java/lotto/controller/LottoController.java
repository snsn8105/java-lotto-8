package lotto.controller;

import lotto.model.Lotto;
import lotto.model.LottoMachine;
import lotto.model.LottoResult;
import lotto.model.WinningLotto;
import lotto.view.InputView;
import lotto.view.OutputView;
import lotto.utils.InputValidator;
import java.util.List;
import java.util.function.Supplier;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;
    private final InputValidator validator;
    private final LottoMachine lottoMachine;

    public LottoController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.validator = new InputValidator();
        this.lottoMachine = new LottoMachine();
    }

    public void run() {
        int purchaseAmount = getPurchaseAmount();
        List<Lotto> lottos = purchaseLottos(purchaseAmount);
        
        WinningLotto winningLotto = getWinningLotto();
        LottoResult result = new LottoResult(lottos, winningLotto);
        
        outputView.printWinningStatistics(result, purchaseAmount);
    }

    private int getPurchaseAmount() {
        return retryOnException(() -> {
            int amount = inputView.readPurchaseAmount();
            validator.validatePurchaseAmount(amount);
            return amount;
        });
    }

    private List<Lotto> purchaseLottos(int amount) {
        List<Lotto> lottos = lottoMachine.purchaseLottos(amount);
        int count = lottoMachine.calculateLottoCount(amount);
        
        outputView.printPurchaseResult(count);
        outputView.printLottos(lottos);
        
        return lottos;
    }

    private WinningLotto getWinningLotto() {
        List<Integer> winningNumbers = getWinningNumbers();
        int bonusNumber = getBonusNumber(winningNumbers);
        return new WinningLotto(winningNumbers, bonusNumber);
    }

    private List<Integer> getWinningNumbers() {
        return retryOnException(() -> {
            List<Integer> numbers = inputView.readWinningNumbers();
            validator.validateLottoNumbers(numbers);
            return numbers;
        });
    }

    private int getBonusNumber(List<Integer> winningNumbers) {
        return retryOnException(() -> {
            int bonusNumber = inputView.readBonusNumber();
            validator.validateBonusNumber(bonusNumber, winningNumbers);
            return bonusNumber;
        });
    }

    private <T> T retryOnException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return retryOnException(supplier);
        }
    }
}
