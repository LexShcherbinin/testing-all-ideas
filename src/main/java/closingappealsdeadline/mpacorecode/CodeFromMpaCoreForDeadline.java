package closingappealsdeadline.mpacorecode;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CodeFromMpaCoreForDeadline {

  private static final int TEN_MINUTE_SHIFT = 10;
  private static final int MINUTES_IN_HOUR = 60;
  private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

  private static int startWorkDay = 10;

  private static int endWorkDay = 19;

  private static int timezone = 3;

  public static void main(String[] args) {
    calculateDeadlineDate(LocalDateTime.parse("12.07.2023 10:00", DATE_TIME_FORMAT), 1);
  }

  public static LocalDateTime calculateDeadlineDate(LocalDateTime nowDate, int timeForDecide) {
    int workDay = endWorkDay - startWorkDay;
    DayOfWeek dayOfWeek = nowDate.getDayOfWeek();

    System.out.println(dayOfWeek.getValue());
    int hour = nowDate.getHour();

    int days = timeForDecide / workDay;
    int hours = timeForDecide % workDay;

    int minute = 0;

    if (startWorkDay <= hour && hour < endWorkDay) {
      hours = (hour - 1 + hours) % workDay;
      minute = getMinute(nowDate.getMinute());
      if (minute == 0) {
        ++hours;
      }
    }
    int valueDayOfWeek = nowDate.getDayOfWeek().getValue();
    if (valueDayOfWeek > 5) {
      hours = timeForDecide % workDay;
      minute = 0;
    } else {
      if ((hour + timeForDecide % workDay >= endWorkDay)) {
        ++days;
      }
    }
    days = getDays(days, valueDayOfWeek);
    LocalDateTime ldt = nowDate.plusDays(days);

    LocalDateTime deadline = LocalDateTime.of(ldt.getYear(), ldt.getMonth(), ldt.getDayOfMonth(), startWorkDay + hours, minute);
    System.out.println(deadline);
    return deadline;
  }

  private static int getMinute(int minute) {
    int minuteShift = (minute / TEN_MINUTE_SHIFT) * TEN_MINUTE_SHIFT + TEN_MINUTE_SHIFT;
    if (minuteShift >= MINUTES_IN_HOUR) {
      minuteShift = minuteShift - MINUTES_IN_HOUR;
    }
    return minuteShift;
  }

  private static int getDays(int days, int dayOfWeek) {
    int countWeek;
    int dayInWeek;
    countWeek = days / 5;
    dayInWeek = days % 5;
    days = countWeek * 7;
    int a = (dayOfWeek + dayInWeek);
    if (a > 5) {
      int shift = 2;
      if (dayOfWeek > 5) {
        shift = dayOfWeek == 6 ? 2 : 1;
      }
      dayInWeek = dayInWeek + shift;
    }
    days = days + dayInWeek;
    System.out.println("days = " + days);
    return days;
  }

}