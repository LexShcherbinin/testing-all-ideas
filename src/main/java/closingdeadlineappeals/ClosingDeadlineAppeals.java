package closingdeadlineappeals;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClosingDeadlineAppeals {

  public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
  private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm");
  public static final LocalTime TIME_FROM = LocalTime.parse("10:00", TIME);
  public static final LocalTime TIME_TO = LocalTime.parse("19:00", TIME);
  public static final LocalTime MIDNIGHT = LocalTime.parse("00:00", TIME);
  public static final int WORKING_HOURS = 9;

  public static void main(String[] args) {
    var currentDateTime = LocalDateTime.parse("07.07.2023 20:00", FORMAT);
    int hourForTask = 9;

    String result = calculating(currentDateTime, hourForTask).format(FORMAT);

    System.out.println(result);
  }

  public static LocalDateTime calculating(LocalDateTime currentDateTime, int hoursForTask) {
    //Количество дней, которое нужно прибавить
    int a = hoursForTask / WORKING_HOURS;

    //Количество часов, которое нужно прибавить
    int b = hoursForTask % WORKING_HOURS;

    LocalDateTime expectedDateTime = currentDateTime;

    //Прибавление часов с учётом выходных и рабочего времени

    while (!isWorkingDay(expectedDateTime)) {
      expectedDateTime = expectedDateTime.with(TIME_FROM).plusDays(1);
    }

    if (isWorkingTime(expectedDateTime)) {
      expectedDateTime = expectedDateTime.plusHours(b);

      if (!isWorkingTime(expectedDateTime)) {
        expectedDateTime = expectedDateTime.plusDays(1).minusHours(9);
      }

    } else if (isBeforeWorkingTime(expectedDateTime)) {
      if (b == 0) {
        expectedDateTime = expectedDateTime.with(TIME_TO);
        a--;

      } else {
        expectedDateTime = expectedDateTime.with(TIME_FROM).plusHours(b);
      }

    } else {
      if (b == 0) {
        expectedDateTime = expectedDateTime.with(TIME_TO);

      } else {
        expectedDateTime = expectedDateTime.with(TIME_FROM).plusDays(1).plusHours(b);
      }

    }

    //Прибавление дней с учётом выходных
    for (int i = 1; i <= a; i++) {
      expectedDateTime = expectedDateTime.plusDays(1);

      while (!isWorkingDay(expectedDateTime)) {
        expectedDateTime = expectedDateTime.plusDays(1);
      }
    }

    return expectedDateTime;
  }

  private static boolean isWorkingDay(LocalDateTime day) {
    DayOfWeek dayOfWeek = day.getDayOfWeek();
    return dayOfWeek != DayOfWeek.SATURDAY && day.getDayOfWeek() != DayOfWeek.SUNDAY;
  }

  private static boolean isWorkingTime(LocalDateTime day) {
    LocalTime time = day.toLocalTime();
    return time.isAfter(TIME_FROM) && time.isBefore(TIME_TO);
  }

  private static boolean isBeforeWorkingTime(LocalDateTime day) {
    LocalTime time = day.toLocalTime();
    return time.isAfter(MIDNIGHT) && time.isBefore(TIME_FROM);
  }

}
