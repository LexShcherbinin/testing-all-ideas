package tasks;

import java.util.concurrent.ThreadLocalRandom;

/**
 * В целочисленном массиве переместите все 0 в конец, сохраняя относительный порядок ненулевых элементов.
 */
public class Task_4 {

  public static void main(String[] args) {
    int[] array = createIntArray(20);
    printArray(array);
    moveZero(array);
    printArray(array);
  }

  private static void moveZero(int[] array) {
    int zeroCount = 0;
    int i = 0;

    while (i < array.length - zeroCount) {
      if (array[i] != 0) {
        i++;

      } else {
        zeroCount++;

        for (int j = i; j < array.length - zeroCount; j++) {
          array[j] = array[j + 1];
        }

        array[array.length - zeroCount] = 0;
      }
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
