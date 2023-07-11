package closingappealsdeadline;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Расчёт времени дедлайна закрытия обращения. Быстрая (правильная) версия.
 * <p>
 * Рабочие часы: пн - пт с 10:00 до 19:00. Обращение может быть заведено в любое время. Часы на выполнение обращения принимают
 * значение от 1 до 1000. Обращение считается принятым в работу в момент подачи. Если обращение подано в нерабочее время, начало
 * выполнения работ по нему переносится на 10:00 следующего рабочего дня. Если дедлайн выпадает на 19:00, то он не переносится на
 * 10:00 следующего рабочего дня.
 */
public class ClosingAppealsDeadlineFastVersion {

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
    var currentDateTime = LocalDateTime.parse("04.07.2023 19:03", DATE_TIME_FORMAT);
    int hourForTask = 9;

    String result = calculatingDeadlineFastVersion(currentDateTime, hourForTask).format(DATE_TIME_FORMAT);
    System.out.println(result);
  }

  /**
   * Расчёт дедлайна выполнения обращения. Быстрая (правильная) версия. Закомментированные участки кода переносят дедлайн с 19:00
   * на 10:00 следующего рабочего дня.
   *
   * @param currentDateTime - время создания обращения.
   * @param hoursForTask    - количество часов, отведённых на выполнение.
   * @return - возвращает дату и время дедлайна закрытия обращения с округлением минут до десятков в большую сторону.
   */
  public static LocalDateTime calculatingDeadlineFastVersion(LocalDateTime currentDateTime, int hoursForTask) {
    //Количество недель, которое нужно прибавить
    int hoursAsWeek = hoursForTask / WORKING_HOURS / WORKING_DAYS;

    //Количество дней, которое нужно прибавить
    int hoursAsDay = (hoursForTask - hoursAsWeek * WORKING_DAYS * WORKING_HOURS) / WORKING_HOURS;

    //Количество часов, которое нужно прибавить
    int hours = hoursForTask - hoursAsWeek * WORKING_DAYS * WORKING_HOURS - hoursAsDay * WORKING_HOURS;

    LocalDateTime deadline = currentDateTime;

    if (isWorkingDay(currentDateTime)) {

      if (isBeforeWorkingTime(currentDateTime)) {

//        if (hours == 0) {
//          deadline = deadline.with(TIME_TO);
//
//          if (hoursAsDay == 0 && hoursAsWeek > 0) {
//            hoursAsWeek--;
//            hoursAsDay = 4;
//
//          } else {
//            hoursAsDay--;
//          }

//        } else {
//          deadline = deadline.with(TIME_FROM).plusHours(hours);
//        }

        deadline = deadline.with(TIME_FROM).plusHours(hours);

      } else if (isAfterWorkingTime(currentDateTime)) {

        if (hours == 0) {
//          deadline = deadline.with(TIME_TO);
          deadline = deadline.plusDays(1).with(TIME_FROM);

        } else {
          deadline = deadline.with(TIME_FROM).plusDays(1).plusHours(hours);
        }

      } else {
        deadline = deadline.plusHours(hours);

        if (!isWorkingTime(deadline)) {
          deadline = deadline.plusDays(1).minusHours(WORKING_HOURS);
        }

      }

    } else {
//      if (hours == 0) {
//        deadline = deadline.plusDays(1).with(TIME_TO);
//
//        if (hoursAsDay == 0 && hoursAsWeek > 0) {
//          hoursAsWeek--;
//          hoursAsDay = 4;
//
//        } else {
//          hoursAsDay--;
//        }
//      } else {
//        deadline = deadline.with(TIME_FROM).plusHours(hours);
//      }

      deadline = deadline.with(TIME_FROM).plusHours(hours);
    }

    if (deadline.getDayOfWeek().getValue() == 7) {
      deadline = deadline.plusDays(1);

    } else if (deadline.getDayOfWeek().getValue() == 6) {
      deadline = deadline.plusDays(2);
    }

    if (deadline.getDayOfWeek().getValue() + hoursAsDay > 5) {
      deadline = deadline.plusDays(2);
    }

    deadline = deadline.plusWeeks(hoursAsWeek).plusDays(hoursAsDay);

    //Округление минут до десятков в большую сторону
    if (deadline.toLocalTime().getMinute() % ROUNDING_MINUTES != 0) {
      int minutes = deadline.toLocalTime().getMinute() / ROUNDING_MINUTES * ROUNDING_MINUTES;
      deadline = deadline.withMinute(minutes).plusMinutes(ROUNDING_MINUTES);

    } else {
      if (isWorkingDayAndTime(currentDateTime)) {
        deadline = deadline.plusMinutes(ROUNDING_MINUTES);
      }
    }

    return deadline;
  }

  /**
   * Расчёт дедлайна выполнения обращения. Долгая (но тоже правильная) версия.
   *
   * @param currentDateTime - время создания обращения.
   * @param hoursForTask    - количество часов, отведённых на выполнение.
   * @return - возвращает дату и время дедлайна закрытия обращения с округлением минут до десятков в большую сторону.
   */
  public static LocalDateTime calculatingDeadlineLongVersion(LocalDateTime currentDateTime, int hoursForTask) {
    LocalDateTime deadline = currentDateTime;

    if (!isWorkingDayAndTime(deadline)) {
      deadline = deadline.withMinute(0);
    }

    while (!isWorkingDayAndTime(deadline)) {
      deadline = deadline.plusHours(1);
    }

    for (int i = 1; i <= hoursForTask; i++) {
      deadline = deadline.plusHours(1);

//      if (i == hoursForTask && deadline.toLocalTime().equals(TIME_TO)) {
//        break;
//      }

      while (!isWorkingDayAndTime(deadline)) {
        deadline = deadline.plusHours(1);
      }

    }

    if (deadline.toLocalTime().getMinute() % ROUNDING_MINUTES != 0) {
      int minutes = deadline.toLocalTime().getMinute() / ROUNDING_MINUTES * ROUNDING_MINUTES;
      deadline = deadline.withMinute(minutes).plusMinutes(ROUNDING_MINUTES);

    } else {
      if (isWorkingDayAndTime(currentDateTime)) {
        deadline = deadline.plusMinutes(10);
      }
    }

    return deadline;
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

    boolean isAfter = time.isAfter(LocalTime.parse("00:00", TIME_FORMAT));
    boolean isBefore = time.isBefore(LocalTime.parse("10:00", TIME_FORMAT));
    boolean equalsMidNight = time.equals(LocalTime.parse("00:00", TIME_FORMAT));
    boolean equalsTimeFrom = time.equals(LocalTime.parse("10:00", TIME_FORMAT));

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

    boolean isAfter = time.isAfter(LocalTime.parse("19:00", TIME_FORMAT));
    boolean isBefore = time.isBefore(LocalTime.parse("23:59", TIME_FORMAT));
    boolean equalsMidNight = time.equals(LocalTime.parse("23:59", TIME_FORMAT));
    boolean equalsTimeTo = time.equals(LocalTime.parse("19:00", TIME_FORMAT));

    return (isAfter && isBefore) || equalsMidNight || equalsTimeTo;
  }

}
