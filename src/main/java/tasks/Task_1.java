package tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Дан массив А, состоящий из целых чисел. напишите функцию, которая принимает на вход массив А и возвращает другой массив, состоящий из неуникальных элементов А.
 * <p>
 * Пример:
 * <p>
 * А                      ->  Результат
 * <p>
 * [1, 3, 1, 3]           ->  [1, 3, 1, 3]
 * <p>
 * [1, 2, 3, 4, 5]        ->  []
 * <p>
 * [7, 7, 7, 7]           ->  [7, 7, 7, 7]
 * <p>
 * [10, 9, 10, 10, 9, 8]  ->  [10, 9, 10, 10, 9]
 */
public class Task_1 {

  public static void main(String[] args) {
    Integer[] arrayBefore1 = new Integer[]{1, 3, 1, 3};
    Integer[] arrayAfter1 = returnNotUniqueElementsArray(arrayBefore1);
    printArray(arrayAfter1);

    Integer[] arrayBefore2 = new Integer[]{1, 2, 3, 4, 5};
    Integer[] arrayAfter2 = returnNotUniqueElementsArray(arrayBefore2);
    printArray(arrayAfter2);

    Integer[] arrayBefore3 = new Integer[]{7, 7, 7, 7};
    Integer[] arrayAfter3 = returnNotUniqueElementsArray(arrayBefore3);
    printArray(arrayAfter3);

    Integer[] arrayBefore4 = new Integer[]{10, 9, 10, 10, 9, 8};
    Integer[] arrayAfter4 = returnNotUniqueElementsArray(arrayBefore4);
    printArray(arrayAfter4);
  }

  private static Integer[] returnNotUniqueElementsArray(Integer[] array) {
    List<Integer> list = Arrays.asList(array);

    return list
        .stream()
        .filter(a -> {
          List<Integer> newList = new ArrayList<>(list);
          newList.removeAll(List.of(a));
          return list.size() - newList.size() > 1;
        }).toArray(Integer[]::new);
  }

  private static void printArray(Integer[] array) {
    for (Integer o : array) {
      System.out.print(o + "\t");
    }
    System.out.println();
  }

}
