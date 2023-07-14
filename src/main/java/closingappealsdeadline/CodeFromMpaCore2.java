package closingappealsdeadline;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CodeFromMpaCore2 {

  private static final int TEN_MINUTE_SHIFT = 10;
  private static final int MINUTES_IN_HOUR = 60;
  private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

  private static int startWorkDay = 10;

  private static int endWorkDay = 19;

  private static int timezone = 3;

  public static void main(String[] args) {
    long result = calculateBetweenDateMinutes(
        LocalDateTime.parse("10.07.2023 18:36", DATE_TIME_FORMAT),
        LocalDateTime.parse("24.07.2023 17:59", DATE_TIME_FORMAT)
    );

    System.out.println(result);
  }

  public static long calculateBetweenDateMinutes(LocalDateTime start, LocalDateTime end) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime startWorkDayTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), startWorkDay,
        0);
    LocalDateTime endWorkDayTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), endWorkDay, 0);
    long workDayMinutes = Duration.between(startWorkDayTime, endWorkDayTime).toMinutes();
    System.out.println("workDayMinutes = " + workDayMinutes);

    start = getStartTime(start);
    end = getEndTime(end);
    if (end.compareTo(start) < 1) {
      return 0;
    }
    long count = extracted(start, end);
    System.out.println("count = " + count);
    System.out.println("start = " + start);
    System.out.println("end = " + end);

    long countStartMin =
        Duration.between(start,
            LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), endWorkDay, 0)).toMinutes();
    System.out.println("countStartMin = " + countStartMin);
    start = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), 0, 0).plusDays(1);
    long countEndMin =
        Duration.between(LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), startWorkDay, 0), end)
            .toMinutes();
    System.out.println("countEndMin = " + countEndMin);
    end = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), 0, 0);
    long days = Duration.between(start, end).toDays();
    System.out.println("days = " + days);
    return (days - count) * workDayMinutes + countStartMin + countEndMin;
  }

  private static long extracted(LocalDateTime start, LocalDateTime end) {
    long count = 0;
    if (start.getDayOfWeek().getValue() > end.getDayOfWeek().getValue() || Duration.between(start, end).toDays() > 4) {
      int dayOfWeek = start.getDayOfWeek().getValue();
      System.out.println("dayOfWeek = " + dayOfWeek);
      long shift = 2;
      if (dayOfWeek > 5) {
        shift = dayOfWeek == 6 ? 2 : 1;
      }
      LocalDateTime startShiftDay =LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), 0, 0).plusDays(8 - dayOfWeek);

      int dayOfWeekEnd = end.getDayOfWeek().getValue();
      System.out.println("dayOfWeek = " + dayOfWeekEnd);
      long shiftEnd = 0;
      if (dayOfWeekEnd > 5) {
        shiftEnd = dayOfWeekEnd == 6 ? 1 : 2;
      }
      LocalDateTime endShiftDay = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), 0, 0).minusDays(dayOfWeekEnd - 1);
      Duration between = Duration.between(startShiftDay, endShiftDay);
      count = between.toDays() / 7;
      System.out.println("between = " + (count * 2 + shift + shiftEnd));
      count = count * 2 + shift + shiftEnd;
    }
    return count;
  }

  private static LocalDateTime getEndTime(LocalDateTime end) {
    int startDayOfWeek = end.getDayOfWeek().getValue();
    int shift;
    if (startDayOfWeek > 5) {
      shift = startDayOfWeek == 6 ? 1 : 2;
      end = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), endWorkDay, 0).minusDays(shift);
    }
    int i = end.compareTo(LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), startWorkDay, 0));
    int j = end.compareTo(LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), endWorkDay, 0));
    if (i > 0 && j > 0) {
      end = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), endWorkDay, 0);
    }
    if (i < 0 && j < 0) {
      end = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), endWorkDay, 0).minusDays(1);
    }
    startDayOfWeek = end.getDayOfWeek().getValue();
    if (startDayOfWeek > 5) {
      shift = startDayOfWeek == 6 ? 1 : 2;
      end = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), endWorkDay, 0).minusDays(shift);
    }
    System.out.println("end = " + end);
    return end;
  }

  private static LocalDateTime getStartTime(LocalDateTime start) {
    int startDayOfWeek = start.getDayOfWeek().getValue();
    int shift;
    if (startDayOfWeek > 5) {
      shift = startDayOfWeek == 6 ? 2 : 1;
      start = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), startWorkDay, 0)
          .plusDays(shift);
    }
    int i = start.compareTo(
        LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), startWorkDay, 0));
    int j = start.compareTo(LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), endWorkDay, 0));
    if (i > 0 && j > 0) {
      start = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), startWorkDay, 0).plusDays(1);
    }
    if (i < 0 && j < 0) {
      start = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), startWorkDay, 0);
    }
    startDayOfWeek = start.getDayOfWeek().getValue();
    if (startDayOfWeek > 5) {
      shift = startDayOfWeek == 6 ? 2 : 1;
      start = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), startWorkDay, 0)
          .plusDays(shift);
    }
    System.out.println("start = " + start);
    return start;
  }

}