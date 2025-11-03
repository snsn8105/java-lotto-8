package lotto.view;

import lotto.model.Lotto;
import lotto.model.LottoResult;
import lotto.model.Rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OutputView {
    public void printPurchaseResult(int count) {
        System.out.println();
        System.out.println(count + "개를 구매했습니다.");
    }

    public void printLottos(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            printLotto(lotto);
        }
    }

    private void printLotto(Lotto lotto) {
        List<Integer> sortedNumbers = new ArrayList<>(lotto.getNumbers());
        Collections.sort(sortedNumbers);
        System.out.println(sortedNumbers);
    }

    public void printWinningStatistics(LottoResult result, int purchaseAmount) {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---");
        
        printRankStatistics(result);
        printProfitRate(result, purchaseAmount);
    }

    private void printRankStatistics(LottoResult result) {
        printRank(Rank.FIFTH, result);
        printRank(Rank.FOURTH, result);
        printRank(Rank.THIRD, result);
        printRank(Rank.SECOND, result);
        printRank(Rank.FIRST, result);
    }

    private void printRank(Rank rank, LottoResult result) {
        System.out.printf("%s (%,d원) - %d개%n",
                rank.getDescription(),
                rank.getPrizeMoney(),
                result.getCount(rank));
    }

    private void printProfitRate(LottoResult result, int purchaseAmount) {
        double profitRate = result.calculateProfitRate(purchaseAmount);
        System.out.printf("총 수익률은 %.1f%%입니다.%n", profitRate);
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }
}
