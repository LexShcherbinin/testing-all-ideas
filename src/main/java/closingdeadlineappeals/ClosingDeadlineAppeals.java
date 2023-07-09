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
  public static final int WORKING_HOURS = 9;

  public static void main(String[] args) {
    var currentDateTime = LocalDateTime.parse("07.07.2023 19:45", FORMAT);
    int hourForTask = 9;

    String result = calculating(currentDateTime, hourForTask).format(FORMAT);

    System.out.println(result);
  }

  public static LocalDateTime calculating(LocalDateTime currentDateTime, int hoursForTask) {
    //Количество дней, которое нужно прибавить
    int hoursAsDay = hoursForTask / WORKING_HOURS;

    //Количество часов, которое нужно прибавить
    int hours = hoursForTask % WORKING_HOURS;

    LocalDateTime expectedDateTime = currentDateTime;

//    if (isWorkingDay(currentDateTime)) {
//
//      if (isBeforeWorkingTime(currentDateTime)) {
//
//        if (hours == 0) {
//          expectedDateTime = currentDateTime.with(TIME_TO);
//          hoursAsDay--;
//
//        } else {
//          expectedDateTime = currentDateTime.with(TIME_FROM).plusHours(hours);
//        }
//
//      } else if (isAfterWorkingTime(currentDateTime)) {
//
//        if (hours == 0) {
//          expectedDateTime = currentDateTime.with(TIME_TO);
//
//        } else {
//          expectedDateTime = currentDateTime.with(TIME_FROM).plusDays(1).plusHours(hours);
//        }
//
//      } else {
//        expectedDateTime = currentDateTime.plusHours(hours);
//
//        if (!isWorkingTime(expectedDateTime)) {
//          expectedDateTime = expectedDateTime.plusDays(1).minusHours(WORKING_HOURS);
//        }
//
//      }
//
//    } else {
//      if (hours == 0) {
//        expectedDateTime = expectedDateTime.plusDays(1).with(TIME_TO);
//        hoursAsDay--;
//
//      } else {
//        expectedDateTime = expectedDateTime.with(TIME_FROM).plusHours(hours);
//      }
//    }
//
//    while (!isWorkingDay(expectedDateTime)) {
//      expectedDateTime = expectedDateTime.plusDays(1);
//    }
//
//    for (int i = 1; i <= hoursAsDay; i++) {
//      expectedDateTime = expectedDateTime.plusDays(1);
//
//      while (!isWorkingDay(expectedDateTime)) {
//        expectedDateTime = expectedDateTime.plusDays(1);
//      }
//    }

    if (!isWorkingDayAndTime(expectedDateTime)) {
      expectedDateTime = expectedDateTime.withMinute(0);
    }

    while (!isWorkingDayAndTime(expectedDateTime)) {
      expectedDateTime = expectedDateTime.plusHours(1);
    }

    for (int i = 1; i <= hoursForTask; i++) {

      expectedDateTime = expectedDateTime.plusHours(1);

      if (i == hoursForTask && expectedDateTime.toLocalTime().equals(LocalTime.parse("19:00", TIME))) {
        break;
      }

      while (!isWorkingDayAndTime(expectedDateTime)) {
        expectedDateTime = expectedDateTime.plusHours(1);
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
    return time.isAfter(LocalTime.parse("09:59", TIME)) && time.isBefore(LocalTime.parse("19:00", TIME));
  }

  private static boolean isWorkingDayAndTime(LocalDateTime day) {
    return isWorkingDay(day) && isWorkingTime(day);
  }

  private static boolean isBeforeWorkingTime(LocalDateTime day) {
    LocalTime time = day.toLocalTime();
    return time.isAfter(LocalTime.parse("00:00", TIME)) && time.isBefore(LocalTime.parse("10:00", TIME));
  }

  private static boolean isAfterWorkingTime(LocalDateTime day) {
    LocalTime time = day.toLocalTime();
    return time.isAfter(LocalTime.parse("19:00", TIME)) && time.isBefore(LocalTime.parse("23:59", TIME));
  }

}
