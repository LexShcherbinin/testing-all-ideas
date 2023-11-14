package tasks;

import static tasks.Task_5.BanknoteDenomination.BD_10;
import static tasks.Task_5.BanknoteDenomination.BD_5000;
import static tasks.Task_5.BanknoteDenomination.getNext;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Банкомат. Имеется сумма, которую банкомат должен выдать, и набор купюр заданных номиналов: {10, 50, 100, 200, 500, 1000, 2000, 5000}.
 * <p>
 * Количество купюр каждого номинала не ограничено. Требуется получить заданную сумму минимальным количеством купюр.
 * <p>
 * Пример: 7830 рублей -> 1х 5000р. + 2х 2000р. + 1х 500р. + 3х 100р. + 3х + 10р.
 */
public class Task_5 {

  public static void main(String[] args) {
    int moneyAmount = 7830;
    List<BanknoteDenomination> banknoteMap = qqq(moneyAmount, BD_5000);

    banknoteMap.forEach(banknote -> {
      System.out.printf("%s x %s\n", banknote.getCount(), banknote.getDenomination());
    });
  }

  private static List<BanknoteDenomination> qqq(int moneyAmount, int banknote) {
    List<BanknoteDenomination> list = new ArrayList<>();

    int count = moneyAmount / banknote;
    int residual = moneyAmount % banknote;

    if (banknote == BD_10) {
      BanknoteDenomination banknoteDenomination = new BanknoteDenomination()
          .setCount(count)
          .setResidual(0)
          .setDenomination(banknote);

      return List.of(banknoteDenomination);
    }

    if (count == 0) {
      list.addAll(qqq(moneyAmount, getNext(banknote)));

    } else {
      BanknoteDenomination banknoteDenomination = new BanknoteDenomination()
          .setCount(count)
          .setResidual(residual)
          .setDenomination(banknote);

      list.add(banknoteDenomination);

      if (residual != 0) {
        list.addAll(qqq(residual, getNext(banknote)));
      }
    }

    return list;
  }

  @Getter
  @Setter
  @Accessors(chain = true)
  public static class BanknoteDenomination {

    private int count;
    private int residual;
    private int denomination;

    public static final int BD_10 = 10;
    public static final int BD_50 = 50;
    public static final int BD_100 = 100;
    public static final int BD_200 = 200;
    public static final int BD_500 = 500;
    public static final int BD_1000 = 1000;
    public static final int BD_2000 = 2000;
    public static final int BD_5000 = 5000;

    public static Integer getNext(int banknote) {
      return switch (banknote) {
        case BD_5000 -> BD_2000;
        case BD_2000 -> BD_1000;
        case BD_1000 -> BD_500;
        case BD_500 -> BD_100;
        case BD_100 -> BD_50;
        case BD_50 -> BD_10;
        case BD_10 -> null;
        default -> throw new IllegalStateException("Unexpected value: " + banknote);
      };
    }

  }

}
