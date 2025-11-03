package lotto.model;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;

public class LottoMachine {
    private static final int LOTTO_PRICE = 1000;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;
    private static final int LOTTO_NUMBER_COUNT = 6;

    public List<Lotto> purchaseLottos(int money) {
        int count = money / LOTTO_PRICE;
        List<Lotto> lottos = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            lottos.add(generateLotto());
        }
        
        return lottos;
    }

    private Lotto generateLotto() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(
                MIN_NUMBER, MAX_NUMBER, LOTTO_NUMBER_COUNT);
        return new Lotto(numbers);
    }

    public int calculateLottoCount(int money) {
        return money / LOTTO_PRICE;
    }
}
