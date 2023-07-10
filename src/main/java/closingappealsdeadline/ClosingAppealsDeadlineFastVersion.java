package closingappealsdeadline;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Расчёт времени дедлайна закрытия обращения. Рабочие часы: пн - пт с 10:00 до 19:00. Обращение может быть заведено в любое
 * время. Часы на выполнение обращения принимают значение от 1 до 1000. Обращение считается принятым в работу в момент подачи.
 * Если обращение подано в нерабочее время, начало выполнения работ по нему переносится на 10:00 следующего рабочего дня. Если
 * дедлайн выпадает на 19:00, то он не переносится на 10:00 следующего рабочего дня.
 */
public class ClosingAppealsDeadlineFastVersion {

  private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
  private static final LocalTime TIME_FROM = LocalTime.parse("10:00", TIME_FORMAT);
  private static final LocalTime TIME_TO = LocalTime.parse("19:00", TIME_FORMAT);
  private static final int ROUNDING_MINUTES = 10;

  public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
  public static final int WORKING_HOURS = 9;

  public static void main(String[] args) {
    var currentDateTime = LocalDateTime.parse("06.08.2023 01:00", DATE_TIME_FORMAT);
    int hourForTask = 45;

    String result = calculatingDeadlineFastVersion(currentDateTime, hourForTask).format(DATE_TIME_FORMAT);
    System.out.println(result);
  }

  /**
   * Расчёт дедлайна выполнения обращения. Долгая версия.
   *
   * @param currentDateTime - время создания обращения.
   * @param hoursForTask    - количество часов, отведённых на выполнение.
   * @return - возвращает дату и время дедлайна закрытия обращения с округлением минут до десятков в большую сторону.
   */
  public static LocalDateTime calculatingDeadlineFastVersion(LocalDateTime currentDateTime, int hoursForTask) {
    //Количество недель, которое нужно прибавить
    int hoursAsWeek = hoursForTask / WORKING_HOURS / 5;

    //Количество дней, которое нужно прибавить
    int hoursAsDay = (hoursForTask - hoursAsWeek * 5 * WORKING_HOURS) / WORKING_HOURS;

    //Количество часов, которое нужно прибавить
    int hours = hoursForTask - hoursAsWeek * 5 * WORKING_HOURS - hoursAsDay * WORKING_HOURS;

    LocalDateTime expectedDateTime = currentDateTime;

//    if (expectedDateTime.getDayOfWeek().getValue() == 7) {
//      expectedDateTime = expectedDateTime.plusDays(1);
//
//    } else if (expectedDateTime.getDayOfWeek().getValue() == 6) {
//      expectedDateTime = expectedDateTime.plusDays(2);
//    }

//    if (expectedDateTime.getDayOfWeek().getValue() + hoursAsDay > 5) {
//      expectedDateTime = expectedDateTime.plusDays(2);
//    }

    if (isWorkingDay(currentDateTime)) {

      if (isBeforeWorkingTime(currentDateTime)) {

        if (hours == 0) {
          expectedDateTime = expectedDateTime.with(TIME_TO);

          if (hoursAsDay == 0 && hoursAsWeek > 0) {
            hoursAsWeek--;
            hoursAsDay = 4;

          } else {
            hoursAsDay--;
          }

        } else {
          expectedDateTime = expectedDateTime.with(TIME_FROM).plusHours(hours);
        }

      } else if (isAfterWorkingTime(currentDateTime)) {

        if (hours == 0) {
          expectedDateTime = expectedDateTime.with(TIME_TO);

        } else {
          expectedDateTime = expectedDateTime.with(TIME_FROM).plusDays(1).plusHours(hours);
        }

      } else {
        expectedDateTime = expectedDateTime.plusHours(hours);

        if (!isWorkingTime(expectedDateTime)) {
          expectedDateTime = expectedDateTime.plusDays(1).minusHours(WORKING_HOURS);
        }

      }

    } else {
      if (hours == 0) {
        expectedDateTime = expectedDateTime.plusDays(1).with(TIME_TO);

        if (hoursAsDay == 0 && hoursAsWeek > 0) {
          hoursAsWeek--;
          hoursAsDay = 4;

        } else {
          hoursAsDay--;
        }

      } else {
        expectedDateTime = expectedDateTime.with(TIME_FROM).plusHours(hours);
      }
    }

    if (expectedDateTime.getDayOfWeek().getValue() == 7) {
      expectedDateTime = expectedDateTime.plusDays(1);

    } else if (expectedDateTime.getDayOfWeek().getValue() == 6) {
      expectedDateTime = expectedDateTime.plusDays(2);
    }

    if (expectedDateTime.getDayOfWeek().getValue() + hoursAsDay > 5) {
      expectedDateTime = expectedDateTime.plusDays(2);
    }

    expectedDateTime = expectedDateTime.plusWeeks(hoursAsWeek).plusDays(hoursAsDay);

//    if (expectedDateTime.getDayOfWeek().getValue() > 5) {
//      expectedDateTime = expectedDateTime.plusDays(2);
//    }

//    if (expectedDateTime.getDayOfWeek().getValue() == 7) {
//      expectedDateTime = expectedDateTime.plusDays(1);
//
//    } else if (expectedDateTime.getDayOfWeek().getValue() == 6) {
//      expectedDateTime = expectedDateTime.plusDays(2);
//    }



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

    return expectedDateTime;
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

  private static boolean isBeforeWorkingTime(LocalDateTime day) {
    LocalTime time = day.toLocalTime();
    return time.isAfter(LocalTime.parse("00:00", TIME_FORMAT)) && time.isBefore(LocalTime.parse("10:00", TIME_FORMAT));
  }

  private static boolean isAfterWorkingTime(LocalDateTime day) {
    LocalTime time = day.toLocalTime();
    return time.isAfter(LocalTime.parse("19:00", TIME_FORMAT)) && time.isBefore(LocalTime.parse("23:59", TIME_FORMAT));
  }

}
