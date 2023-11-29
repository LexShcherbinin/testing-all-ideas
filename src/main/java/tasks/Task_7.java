package tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Требуется написать метод, который принимает на вход два списка элементов, и возвращает третий, являющийся пересечением этих двух списков. Порядок не важен.
 * <p>
 * Пример:
 * <p>
 * List<Integer> list1 = List.of(1, 2, 3, 2, 0, 2);
 * <p>
 * List<Integer> list2 = List.of(5, 1, 2, 7, 3, 2);
 * <p>
 * Результат: (1,2,3,2)
 */
public class Task_7 {

  public static void main(String[] args) {
    List<Integer> list1 = List.of(1, 2, 3, 2, 0, 2);
    List<Integer> list2 = List.of(5, 1, 2, 7, 3, 2);

    List<Integer> result = mergePair(list1, list2);
    System.out.println(result);
  }

  public static List<Integer> mergePair(List<Integer> list1, List<Integer> list2) {
    List<Integer> result = new ArrayList<>();
    List<Integer> copyList1 = new ArrayList<>(list1);
    List<Integer> copyList2 = new ArrayList<>(list2);

    for (Integer element : copyList1) {
      if (copyList2.contains(element)) {
        result.add(element);
        copyList2.remove(element);
      }
    }

    return result;
  }

}
