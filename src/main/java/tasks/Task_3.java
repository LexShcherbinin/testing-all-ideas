package tasks;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Подсчитайте сумму всех элементов на основной диагонали и всех элементов на дополнительной диагонали, которые не являются частью основной диагонали.
 */
public class Task_3 {

  public static void main(String[] args) {
    int[][] matrix = createMatrix(4);
    printMatrix(matrix);

    int mainSum = getMainDiagonalSum(matrix);
    int subSum = getSubDiagonalSum(matrix);
    int totalSum = countDiagonalSum(matrix);

    System.out.println("Сумма элементов основной диагонали = " + mainSum);
    System.out.println("Сумма элементов дополнительной диагонали = " + subSum);
    System.out.println("Общая сумма элементов диагонали = " + totalSum);
  }

  private static int[][] createMatrix(int size) {
    int[][] matrix = new int[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        matrix[i][j] = ThreadLocalRandom.current().nextInt(0, 9 + 1);
//        matrix[i][j] = (int)(Math.random() * ((9 - 0) + 1));
      }
    }

    return matrix;
  }

  private static int getMainDiagonalSum(int[][] matrix) {
    int sum = 0;

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        if (i == j) {
          sum += matrix[i][j];
        }
      }
    }

    return sum;
  }

  private static int getSubDiagonalSum(int[][] matrix) {
    int sum = 0;

    for (int i = 0; i < matrix.length; i++) {
      for (int j = matrix.length - 1; j >= 0; j--) {
        if ((j == matrix.length - i - 1) && (matrix.length % 2 == 0 || j != matrix.length / 2)) {
          sum += matrix[i][j];
        }
      }
    }

    return sum;
  }

  private static int countDiagonalSum(int[][] matrix) {
    int sum = 0;
    for (int i = 0, j = matrix.length - 1; i < matrix.length; i++, j--) {
      sum += matrix[i][i];
      if (j != i) {
        sum += matrix[i][j];
      }
    }
    return sum;
  }

  private static void printMatrix(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        System.out.print(matrix[i][j] + "\t\t");
      }
      System.out.println();
    }
  }


}
