package tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Банкомат. Имеется сумма, которую банкомат должен выдать, и набор купюр заданных номиналов: {10, 50, 100, 200, 500, 1000, 2000, 5000}.
 * <p>
 * Количество купюр каждого номинала не ограничено. Требуется получить заданную сумму набором купюр большего номинала.
 * <p>
 * Пример: 7830 рублей -> 1х 5000р. + 2х 2000р. + 1х 500р. + 3х 100р. + 3х + 10р.
 */
public class Task_5 {

  public static void main(String[] args) {
    int moneyAmount = 17850;
    List<Map<Integer, Integer>> banknoteMap = getBanknotes(moneyAmount, BanknoteDenomination.BD_5000);

    banknoteMap.forEach(banknote -> {
      for (int denomination : banknote.keySet()) {
        System.out.printf("%s x %s\n", banknote.get(denomination), denomination);
      }
    });
  }

  private static List<Map<Integer, Integer>> getBanknotes(int moneyAmount, int banknoteDenomination) {
    if (moneyAmount % 10 != 0) {
      throw new IllegalArgumentException("Данную сумму банкомат выдать не может");
    }

    int count = moneyAmount / banknoteDenomination;
    int residual = moneyAmount % banknoteDenomination;

    if (banknoteDenomination == BanknoteDenomination.BD_10) {
      return List.of(Map.of(banknoteDenomination, count));
    }

    List<Map<Integer, Integer>> list = new ArrayList<>();

    if (count == 0) {
      list.addAll(getBanknotes(moneyAmount, BanknoteDenomination.getNextDenominate(banknoteDenomination)));

    } else {
      list.add(Map.of(banknoteDenomination, count));

      if (residual != 0) {
        list.addAll(getBanknotes(residual, BanknoteDenomination.getNextDenominate(banknoteDenomination)));
      }
    }

    return list;
  }

  private static final class BanknoteDenomination {

    private static final int BD_10 = 10;
    private static final int BD_50 = 50;
    private static final int BD_100 = 100;
    private static final int BD_200 = 200;
    private static final int BD_500 = 500;
    private static final int BD_1000 = 1000;
    private static final int BD_2000 = 2000;
    private static final int BD_5000 = 5000;

    public static Integer getNextDenominate(int banknote) {
      return switch (banknote) {
        case BD_5000 -> BD_2000;
        case BD_2000 -> BD_1000;
        case BD_1000 -> BD_500;
        case BD_500 -> BD_200;
        case BD_200 -> BD_100;
        case BD_100 -> BD_50;
        case BD_50 -> BD_10;
        case BD_10 -> null;
        default -> throw new IllegalStateException("Unexpected value: " + banknote);
      };
    }

  }

}
