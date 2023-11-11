package tasks;

import java.util.concurrent.ThreadLocalRandom;

/**
 * В целочисленном массиве переместите все 0 в конец, сохраняя относительный порядок ненулевых элементов.
 */
public class Task_4 {

  public static void main(String[] args) {
    int[] array = createIntArray(20);
    printArray(array);
    long startTime = System.nanoTime();
    moveZeroesVersion1(array);
    long endTime = System.nanoTime();
    printArray(array);
    System.out.println("Время выполнения программы = " + (endTime - startTime));
  }

  private static void moveZeroesVersion1(int[] array) {
    int zeroCount = 0;
    int i = 0;

    while (i < array.length - zeroCount) {
      if (array[i + zeroCount] != 0) {
        array[i] = array[i + zeroCount];
        i++;

      } else {
        zeroCount++;
      }
    }

    while (zeroCount > 0) {
      array[array.length - zeroCount] = 0;
      zeroCount--;
    }
  }

  public static void moveZeroesVersion2(int[] nums) {
    int counterWithoutNulls = 0;
    int counterWithNulls = 0;
    int length = nums.length;
    while (counterWithNulls < length) {
      if (nums[counterWithNulls] == 0) {// находим нулевые элементы и увеличиваем счётчик
        counterWithNulls++;
      } else { // сдвигаем элементы на количество найденных нулевых элементов слева
        nums[counterWithoutNulls++] = nums[counterWithNulls++];
      }
    }
    while (counterWithoutNulls < length) {
      nums[counterWithoutNulls++] = 0;// заполняем последние элементы массива нулями согласно счётчику нулей
    }
  }

  private static void printArray(int[] array) {
    for (Object o : array) {
      System.out.print(o + "\t");
    }
    System.out.println();
  }

  private static int[] createIntArray(int size) {
    int[] array = new int[size];

    for (int i = 0; i < size; i++) {
      array[i] = ThreadLocalRandom.current().nextInt(0, 3 + 1);
    }

    return array;
  }

}
