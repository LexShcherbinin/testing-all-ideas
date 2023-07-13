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
   * Количество рабочих минут в часе.
   */
  private static final int WORKING_MINUTES = 60;

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
    var startDateTime = LocalDateTime.parse("04.07.2023 18:00", DATE_TIME_FORMAT);
    var endDateTime = LocalDateTime.parse("18.07.2023 18:00", DATE_TIME_FORMAT);

    long result = calculatingMinutes(startDateTime, endDateTime);
    System.out.println(result);
  }

  public static long calculatingMinutes(LocalDateTime createDateTime, LocalDateTime closeDateTime) {
    LocalDateTime startDateTime = createDateTime;
    LocalDateTime endDateTime = closeDateTime;

    //startDateTime
    if (isWorkingDay(startDateTime)) {

      if (isBeforeWorkingTime(startDateTime)) {
        startDateTime = startDateTime.with(TIME_FROM);

      } else if (isAfterWorkingTime(startDateTime)) {
        startDateTime = startDateTime.plusDays(1).with(TIME_FROM);
      }

    } else {
      startDateTime = startDateTime.with(TIME_FROM);
    }

    if (startDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
      startDateTime = startDateTime.plusDays(1);

    } else if (startDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
      startDateTime = startDateTime.plusDays(2);
    }

    //endDateTime
    if (isWorkingDay(endDateTime)) {

      if (isBeforeWorkingTime(endDateTime)) {
        endDateTime = endDateTime.minusDays(1).with(TIME_TO);

      } else if (isAfterWorkingTime(endDateTime)) {
        endDateTime = endDateTime.with(TIME_TO);

      }

    } else {
      endDateTime = endDateTime.with(TIME_TO);
    }


    if (endDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
      endDateTime = endDateTime.minusDays(2);

    } else if (endDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
      endDateTime = endDateTime.minusDays(1);
    }


    if (startDateTime.getDayOfWeek().getValue() > endDateTime.getDayOfWeek().getValue()) {
      startDateTime = startDateTime.plusDays(2);
    }

    long totalWeeks = ChronoUnit.WEEKS.between(startDateTime, endDateTime);
    startDateTime = startDateTime.plusWeeks(totalWeeks);

    if (ChronoUnit.DAYS.between(startDateTime, endDateTime) >= WORKING_DAYS) {
      startDateTime = startDateTime.plusDays(2);
    }

    long totalDays = ChronoUnit.DAYS.between(startDateTime, endDateTime);
    startDateTime = startDateTime.plusDays(totalDays);

    if (ChronoUnit.HOURS.between(startDateTime, endDateTime) > WORKING_HOURS) {
      startDateTime = startDateTime.plusDays(1);
      totalDays++;
    }

    long totalHours = ChronoUnit.HOURS.between(startDateTime, endDateTime);
    startDateTime = startDateTime.plusHours(totalHours);

    if (ChronoUnit.MINUTES.between(startDateTime, endDateTime) < 0) {
      startDateTime = startDateTime.plusHours(1);
      totalHours++;
    }

    long totalMinutes = ChronoUnit.MINUTES.between(startDateTime, endDateTime);

    long minutesAsWeeks = totalWeeks * WORKING_DAYS * WORKING_HOURS * WORKING_MINUTES;
    long minutesAsDays = totalDays * WORKING_HOURS * WORKING_MINUTES;
    long minutesAsHours = totalHours * WORKING_MINUTES;
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
   * @return - возвращает true, если дата и время приходятся на время обработки обращений, и false во всех остальных
   * случаях.
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
