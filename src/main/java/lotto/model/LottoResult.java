package lotto.model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoResult {
    private final Map<Rank, Integer> result;

    public LottoResult(List<Lotto> lottos, WinningLotto winningLotto) {
        this.result = new EnumMap<>(Rank.class);
        initializeResult();
        calculateResult(lottos, winningLotto);
    }

    private void initializeResult() {
        for (Rank rank : Rank.values()) {
            result.put(rank, 0);
        }
    }

    private void calculateResult(List<Lotto> lottos, WinningLotto winningLotto) {
        for (Lotto lotto : lottos) {
            Rank rank = winningLotto.match(lotto);
            result.put(rank, result.get(rank) + 1);
        }
    }

    public int getCount(Rank rank) {
        return result.get(rank);
    }

    public int getTotalPrizeMoney() {
        return result.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrizeMoney() * entry.getValue())
                .sum();
    }

    public double calculateProfitRate(int purchaseAmount) {
        return (double) getTotalPrizeMoney() / purchaseAmount * 100;
    }
}
