package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.Validator;
import lotto.constants.LottoConfig;
import lotto.constants.StaticMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static lotto.constants.LottoConfig.*;
import static lotto.domain.InputHandler.*;

public class Lotto {
    public static List<Integer> winningNumbers;
    public static int bonusNumber;
    private final List<Integer> numbers;

    public Lotto() {
        this(generateNumbers());
    }

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException();
        }
    }

    // TODO: 추가 기능 구현

    public static List<Lotto> publish(int payment) {
        return Stream.generate(Lotto::new)
                     .limit(getLottoCount(payment))
                     .collect(Collectors.toList());
    }

//    public static List<List<Integer>> publish(int payment) {
//        List<List<Integer>> lottos = new ArrayList<>();
//        while (lottos.size() < getLottoCount(payment)) {
//            lottos.add(generateNumbers());
//        }
//        return lottos;
//    }


    public List<Integer> getNumbers() {
        return numbers;
    }

    public static void setWinningNumbers() {
        // TODO 1: 당첨 번호 입력 받기
        winningNumbers = askWinningNumbers();

        // TODO 2: 보너스 번호 입력 받기
        bonusNumber = askNumber(StaticMessage.INPUT_BONUS_NUMBER);
    }

    //    private static String[] convertInputTo
    private static List<Integer> generateNumbers() {
        return Randoms.pickUniqueNumbersInRange(
                RANDOM_RANGE_MIN_NUMBER.getValue(),
                RANDOM_RANGE_MAX_NUMBER.getValue(),
                NUMBER_LENGTH.getValue()
        );
    }

    private static int getLottoCount(int payment) {
        return payment / LottoConfig.UNIT_PRICE.getValue();
    }

    public long compare(Lotto lotto) {
        return lotto.getNumbers()
                    .stream()
                    .filter(winningNumbers::contains)
                    .count();
    }

    public boolean bonusCompare(Lotto lotto) {
        return lotto.getNumbers()
                    .stream()
                    .anyMatch(number -> number == bonusNumber);
    }

}
