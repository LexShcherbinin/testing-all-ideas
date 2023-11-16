package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Написать метод, который будет составлять список дней, расположенных между двумя датами.
 */
public class Task_6 {

  public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

  public static void main(String[] args) {
    LocalDateTime from = LocalDateTime.parse("15.11.2023 00:00", FORMAT);
    LocalDateTime to = LocalDateTime.parse("01.02.2024 00:00", FORMAT);

    List<LocalDateTime> dateTimeList = getDateTimeList(from, to);
    printLocalDateTime(dateTimeList);
    System.out.println("Количество дней = " + dateTimeList.size());

    Collections.sort(dateTimeList);
    printLocalDateTime(dateTimeList);

    Collections.reverse(dateTimeList);
    printLocalDateTime(dateTimeList);
  }

  private static List<LocalDateTime> getDateTimeList(LocalDateTime from, LocalDateTime to) {
    List<LocalDateTime> list = new ArrayList<>();

    while (from.isBefore(to)) {
      list.add(from);
      from = from.plusDays(1);
    }

    list.add(to);

    return list;
  }

  private static void printLocalDateTime(List<LocalDateTime> list) {
    for (LocalDateTime date : list) {
      System.out.print(date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "\n");
    }
    System.out.println();
  }

}
