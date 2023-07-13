package closingappealsdeadline;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс для хранения констант и методов для работы с заведением/закрытием обращений.
 */
public class WorkingDateTimeHelper {

  public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
  public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

  /**
   * Время начала рабочего дня.
   */
  public static final LocalTime TIME_FROM = LocalTime.parse("10:00", TIME_FORMAT);

  /**
   * Время окончания рабочего дня.
   */
  public static final LocalTime TIME_TO = LocalTime.parse("19:00", TIME_FORMAT);

  /**
   * Полночь.
   */
  public static final LocalTime MIDNIGHT = LocalTime.parse("00:00", TIME_FORMAT);

  /**
   * Количество рабочих минут в часе.
   */
  public static final int WORKING_MINUTES = 60;

  /**
   * Количество рабочих часов в сутках.
   */
  public static final int WORKING_HOURS = 9;

  /**
   * Количество рабочих дней в неделю.
   */
  public static final int WORKING_DAYS = 5;

  /**
   * Время В минутах, до которого требуется округление деделайна.
   */
  public static final int ROUNDING_MINUTES = 10;

  /**
   * Определяет, попадает ли текущая дата в рабочие дни.
   *
   * @param day - дата и время.
   * @return - возвращает true, если дата приходится на рабочие дни, и false во всех остальных случаях.
   */
  public static boolean isWorkingDay(LocalDateTime day) {
    DayOfWeek dayOfWeek = day.getDayOfWeek();
    return dayOfWeek != DayOfWeek.SATURDAY && day.getDayOfWeek() != DayOfWeek.SUNDAY;
  }

  /**
   * Определяет, попадает ли текущее время в рабочие часы.
   *
   * @param day - дата и время.
   * @return - возвращает true, если время приходится на время обработки обращений, и false во всех остальных случаях.
   */
  public static boolean isWorkingTime(LocalDateTime day) {
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
  public static boolean isWorkingDayAndTime(LocalDateTime day) {
    return isWorkingDay(day) && isWorkingTime(day);
  }

  /**
   * Определяет, попадает ли текущее время во время до рабочего дня (с 00:00 до 10:00 включительно).
   *
   * @param day - дата и время.
   * @return - возвращает true, если время приходятся на время до рабочего дня, и false во всех остальных случаях.
   */
  public static boolean isBeforeWorkingTime(LocalDateTime day) {
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
  public static boolean isAfterWorkingTime(LocalDateTime day) {
    LocalTime time = day.toLocalTime();

    boolean isAfter = time.isAfter(TIME_TO);
    boolean isBefore = time.isBefore(MIDNIGHT.minusMinutes(1));
    boolean equalsMidNight = time.equals(MIDNIGHT.minusMinutes(1));
    boolean equalsTimeTo = time.equals(TIME_TO);

    return (isAfter && isBefore) || equalsMidNight || equalsTimeTo;
  }

}
