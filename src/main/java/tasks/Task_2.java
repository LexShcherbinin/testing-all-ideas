package tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Написать код, который выводит числа от 0 до 1000, которые делятся на 3, но не делятся на 5, и сумма цифр в которых меньше десяти.
 */
public class Task_2 {

  public static void main(String[] args) {
    long startTime = System.nanoTime();

    List<Integer> numbersBefore = createNumberList(500000000);
    List<Integer> numbersAfter = getNumbers(numbersBefore);
    System.out.println(numbersAfter);

    long endTime = System.nanoTime();
    System.out.println("Время выполнения программы = " + (endTime - startTime));
  }

  private static List<Integer> getNumbers(List<Integer> numberList) {
    return numberList.stream()
        .filter(number -> number % 3 == 0 && number % 5 != 0 && getNumberSumVersion2(number) < 10)
        .collect(Collectors.toList());
  }

  private static double getNumberSumVersion1(int number) {
    return String.valueOf(number)
        .chars()
        .mapToObj(c -> (char) c)
        .map(String::valueOf)
        .mapToDouble(Integer::parseInt)
        .sum();
  }

  private static int getNumberSumVersion2(int number) {
    int sum = 0;

    while (number > 0) {
      sum += number % 10;
      number = number / 10;
    }

    return sum;
  }

  private static List<Integer> createNumberList(int size) {
    List<Integer> numbers = new ArrayList<>();

    for (int i = 0; i <= size; i++) {
      numbers.add(i);
    }

    return numbers;
  }

}
