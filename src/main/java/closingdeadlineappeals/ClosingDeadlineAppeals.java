package closingdeadlineappeals;

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
public class ClosingDeadlineAppeals {

  public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
  private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm");
  public static final LocalTime TIME_FROM = LocalTime.parse("09:59", TIME);
  public static final LocalTime TIME_TO = LocalTime.parse("19:00", TIME);

  public static void main(String[] args) {
    var currentDateTime = LocalDateTime.parse("07.07.2023 19:45", FORMAT);
    int hourForTask = 9;

    String result = calculatingDeadline(currentDateTime, hourForTask).format(FORMAT);
    System.out.println(result);
  }

  /**
   * Расчёт дедлайна выполнения обращения.
   *
   * @param currentDateTime - время создания обращения.
   * @param hoursForTask    - количество часов, отведённых на выполнение.
   * @return - возвращает дату и время дедлайна закрытия обращения.
   */
  public static LocalDateTime calculatingDeadline(LocalDateTime currentDateTime, int hoursForTask) {
    LocalDateTime expectedDateTime = currentDateTime;

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
    return time.isAfter(TIME_FROM) && time.isBefore(TIME_TO);
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

}
