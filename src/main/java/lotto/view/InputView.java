package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputView {
    private static final String DELIMITER = ",";

    public int readPurchaseAmount() {
        System.out.println("구입금액을 입력해 주세요.");
        return parseInteger(Console.readLine());
    }

    public List<Integer> readWinningNumbers() {
        System.out.println();
        System.out.println("당첨 번호를 입력해 주세요.");
        String input = Console.readLine();
        return parseNumbers(input);
    }

    public int readBonusNumber() {
        System.out.println();
        System.out.println("보너스 번호를 입력해 주세요.");
        return parseInteger(Console.readLine());
    }

    private int parseInteger(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해야 합니다.");
        }
    }

    private List<Integer> parseNumbers(String input) {
        try {
            return Arrays.stream(input.split(DELIMITER))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해야 합니다.");
        }
    }
}
