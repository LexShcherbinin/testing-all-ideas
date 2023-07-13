package closingappealsdeadline;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ClosingAppealsDeadlineRevers {

  public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
  public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

  /**
   * Время начала рабочего дня.
   */
  private static final LocalTime TIME_FROM = LocalTime.parse("10:00", TIME_FORMAT);

  /**
   * Время окончания рабочего дня.
   */
  private static final LocalTime TIME_TO = LocalTime.parse("19:00", TIME_FORMAT);

  /**
   * Полночь.
   */
  private static final LocalTime MIDNIGHT = LocalTime.parse("00:00", TIME_FORMAT);

  /**
   * Количество рабочих часов в сутках.
   */
  private static final int WORKING_HOURS = 9;

  /**
   * Количество рабочих дней в неделю.
   */
  private static final int WORKING_DAYS = 5;

  /**
   * Время В минутах, до которого требуется округление деделайна.
   */
  private static final int ROUNDING_MINUTES = 10;

  public static void main(String[] args) {
    var startDateTime = LocalDateTime.parse("14.07.2023 18:00", DATE_TIME_FORMAT);
    var endDateTime = LocalDateTime.parse("17.07.2023 11:00", DATE_TIME_FORMAT);

    long result = calculatingMinutes(startDateTime, endDateTime);
    System.out.println(result);
  }

  public static long calculatingMinutes(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    LocalDateTime shiftStartDateTime = startDateTime;
    LocalDateTime shiftEndDateTime = endDateTime;

    //shiftStartDateTime
    if (isWorkingDay(shiftStartDateTime)) {

      if (isBeforeWorkingTime(shiftStartDateTime)) {
        shiftStartDateTime = shiftStartDateTime.with(TIME_FROM);

      } else if (isAfterWorkingTime(shiftStartDateTime)) {
        shiftStartDateTime = shiftStartDateTime.plusDays(1).with(TIME_FROM);

      } else {

      }

    } else {
      shiftStartDateTime = shiftStartDateTime.with(TIME_FROM);
    }

    if (shiftStartDateTime.getDayOfWeek().getValue() == 7) {
      shiftStartDateTime = shiftStartDateTime.plusDays(1);

    } else if (shiftStartDateTime.getDayOfWeek().getValue() == 6) {
      shiftStartDateTime = shiftStartDateTime.plusDays(2);
    }

    //shiftEndDateTime
    if (isWorkingDay(shiftEndDateTime)) {

      if (isBeforeWorkingTime(shiftEndDateTime)) {
        shiftEndDateTime = shiftEndDateTime.minusDays(1).with(TIME_TO);

      } else if (isAfterWorkingTime(shiftEndDateTime)) {
        shiftEndDateTime = shiftEndDateTime.with(TIME_TO);

      } else {

      }

    } else {
      shiftEndDateTime = shiftEndDateTime.with(TIME_TO);
    }

    if (shiftEndDateTime.getDayOfWeek().getValue() == 7) {
      shiftEndDateTime = shiftEndDateTime.minusDays(2);

    } else if (shiftEndDateTime.getDayOfWeek().getValue() == 6) {
      shiftEndDateTime = shiftEndDateTime.minusDays(1);
    }

    if (shiftStartDateTime.getDayOfWeek().getValue() > shiftEndDateTime.getDayOfWeek().getValue()) {
      shiftStartDateTime = shiftStartDateTime.plusDays(2);
    }


    long totalWeeks = ChronoUnit.WEEKS.between(shiftStartDateTime, shiftEndDateTime);
    shiftStartDateTime = shiftStartDateTime.plusWeeks(totalWeeks);


    if (ChronoUnit.DAYS.between(shiftStartDateTime, shiftEndDateTime) >= 5) {
      shiftStartDateTime = shiftStartDateTime.plusDays(2);
    }

    long totalDays = ChronoUnit.DAYS.between(shiftStartDateTime, shiftEndDateTime);
    shiftStartDateTime = shiftStartDateTime.plusDays(totalDays);


    long totalHours;
    if (ChronoUnit.HOURS.between(shiftStartDateTime, shiftEndDateTime) > 9) {
      shiftStartDateTime = shiftStartDateTime.plusDays(1);
      totalDays++;
      totalHours = ChronoUnit.HOURS.between(shiftStartDateTime, shiftEndDateTime);

    } else {
      totalHours = ChronoUnit.HOURS.between(shiftStartDateTime, shiftEndDateTime);
    }

    shiftStartDateTime = shiftStartDateTime.plusHours(totalHours);


    long totalMinutes;
    if (ChronoUnit.MINUTES.between(shiftStartDateTime, shiftEndDateTime) < 0 ) {
      shiftStartDateTime = shiftStartDateTime.plusHours(1);
      totalHours++;
      totalMinutes = ChronoUnit.MINUTES.between(shiftStartDateTime, shiftEndDateTime);

    } else {
      totalMinutes = ChronoUnit.MINUTES.between(shiftStartDateTime, shiftEndDateTime);
    }

    long minutesAsWeeks = totalWeeks * 5 * 9 * 60;
    long minutesAsDays = totalDays * 9 * 60;
    long minutesAsHours = totalHours * 60;
    long minutesAsMinutes = totalMinutes;

    long result = minutesAsWeeks + minutesAsDays + minutesAsHours + minutesAsMinutes;

    if (result < 0) {
      return 0;
    }

    return result;
  }

  /**
   * Определяет, попадает ли текущая дата в рабочие дни.
   *
   * @param day - дата и время.
   * @return - возвращает true, если дата приходится на рабочие дни, и false во всех остальных случаях.
   */
  private static boolean isWorkingDay(LocalDateTime day) {
    DayOfWeek dayOfWeek = day.getDayOfWeek();
    return dayOfWeek != DayOfWeek.SATURDAY && day.getDayOfWeek() != DayOfWeek.SUNDAY;
  }

  /**
   * Определяет, попадает ли текущее время в рабочие часы.
   *
   * @param day - дата и время.
   * @return - возвращает true, если время приходится на время обработки обращений, и false во всех остальных случаях.
   */
  private static boolean isWorkingTime(LocalDateTime day) {
    LocalTime time = day.toLocalTime();
    return time.isAfter(LocalTime.parse("09:59", TIME_FORMAT)) && time.isBefore(LocalTime.parse("19:00", TIME_FORMAT));
  }

  /**
   * Определяет, попадает ли текущая дата и время во время обработки обращений.
   *
   * @param day - дата и время.
   * @return - возвращает true, если дата и время приходятся на время обработки обращений, и false во всех остальных случаях.
   */
  private static boolean isWorkingDayAndTime(LocalDateTime day) {
    return isWorkingDay(day) && isWorkingTime(day);
  }

  /**
   * Определяет, попадает ли текущее время во время до рабочего дня (с 00:00 до 10:00 включительно).
   *
   * @param day - дата и время.
   * @return - возвращает true, если время приходятся на время до рабочего дня, и false во всех остальных случаях.
   */
  private static boolean isBeforeWorkingTime(LocalDateTime day) {
    LocalTime time = day.toLocalTime();

    boolean isAfter = time.isAfter(MIDNIGHT);
    boolean isBefore = time.isBefore(TIME_FROM);
    boolean equalsMidNight = time.equals(MIDNIGHT);
    boolean equalsTimeFrom = time.equals(TIME_FROM);

    return (isAfter && isBefore) || equalsMidNight || equalsTimeFrom;
  }

  /**
   * Определяет, попадает ли текущее время во время после рабочего дня (с 19:00 до 23:59 включительно).
   *
   * @param day - дата и время.
   * @return - возвращает true, если время приходятся на время после рабочего дня, и false во всех остальных случаях.
   */
  private static boolean isAfterWorkingTime(LocalDateTime day) {
    LocalTime time = day.toLocalTime();

    boolean isAfter = time.isAfter(TIME_TO);
    boolean isBefore = time.isBefore(MIDNIGHT.minusMinutes(1));
    boolean equalsMidNight = time.equals(MIDNIGHT.minusMinutes(1));
    boolean equalsTimeTo = time.equals(TIME_TO);

    return (isAfter && isBefore) || equalsMidNight || equalsTimeTo;
  }

}
