//package closingappealsdeadline;
//
//import java.time.DayOfWeek;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
///**
// * Расчёт времени дедлайна закрытия обращения. Рабочие часы: пн - пт с 10:00 до 19:00. Обращение может быть заведено в любое
// * время. Часы на выполнение обращения принимают значение от 1 до 1000. Обращение считается принятым в работу в момент подачи.
// * Если обращение подано в нерабочее время, начало выполнения работ по нему переносится на 10:00 следующего рабочего дня. Если
// * дедлайн выпадает на 19:00, то он не переносится на 10:00 следующего рабочего дня.
// */
//public class ClosingAppealsDeadlineLongVersion {
//
//  private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
//  private static final LocalTime TIME_FROM = LocalTime.parse("09:59", TIME_FORMAT);
//  private static final LocalTime TIME_TO = LocalTime.parse("19:00", TIME_FORMAT);
//  private static final int ROUNDING_MINUTES = 10;
//
//  public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
//
//  public static void main(String[] args) {
//    var currentDateTime = LocalDateTime.parse("12.07.2023 13:00", DATE_TIME_FORMAT);
//    int hourForTask = 1;
//
//    String result = calculatingDeadlineLongVersion(currentDateTime, hourForTask).format(DATE_TIME_FORMAT);
//    System.out.println(result);
//  }
//
//  /**
//   * Расчёт дедлайна выполнения обращения. Долгая версия.
//   *
//   * @param currentDateTime - время создания обращения.
//   * @param hoursForTask    - количество часов, отведённых на выполнение.
//   * @return - возвращает дату и время дедлайна закрытия обращения с округлением минут до десятков в большую сторону.
//   */
//  public static LocalDateTime calculatingDeadlineLongVersion(LocalDateTime currentDateTime, int hoursForTask) {
//    LocalDateTime deadline = currentDateTime;
//
//    if (!isWorkingDayAndTime(deadline)) {
//      deadline = deadline.withMinute(0);
//    }
//
//    while (!isWorkingDayAndTime(deadline)) {
//      deadline = deadline.plusHours(1);
//    }
//
//    for (int i = 1; i <= hoursForTask; i++) {
//      deadline = deadline.plusHours(1);
//
////      if (i == hoursForTask && deadline.toLocalTime().equals(TIME_TO)) {
////        break;
////      }
//
//      while (!isWorkingDayAndTime(deadline)) {
//        deadline = deadline.plusHours(1);
//      }
//
//    }
//
//    if (deadline.toLocalTime().getMinute() % ROUNDING_MINUTES != 0) {
//      int minutes = deadline.toLocalTime().getMinute() / ROUNDING_MINUTES * ROUNDING_MINUTES;
//      deadline = deadline.withMinute(minutes).plusMinutes(ROUNDING_MINUTES);
//
//    } else {
//      if (isWorkingDayAndTime(currentDateTime)) {
//        deadline = deadline.plusMinutes(10);
//      }
//    }
//
//    return deadline;
//  }
//
//  /**
//   * Определяет, попадает ли текущая дата в рабочие дни.
//   *
//   * @param day - дата и время.
//   * @return - возвращает true, если дата приходится на рабочие дни, и false во всех остальных случаях.
//   */
//  private static boolean isWorkingDay(LocalDateTime day) {
//    DayOfWeek dayOfWeek = day.getDayOfWeek();
//    return dayOfWeek != DayOfWeek.SATURDAY && day.getDayOfWeek() != DayOfWeek.SUNDAY;
//  }
//
//  /**
//   * Определяет, попадает ли текущее время в рабочие часы.
//   *
//   * @param day - дата и время.
//   * @return - возвращает true, если время приходится на время обработки обращений, и false во всех остальных случаях.
//   */
//  private static boolean isWorkingTime(LocalDateTime day) {
//    LocalTime time = day.toLocalTime();
//    return time.isAfter(LocalTime.parse("09:59", TIME_FORMAT)) && time.isBefore(LocalTime.parse("19:00", TIME_FORMAT));
//  }
//
//  /**
//   * Определяет, попадает ли текущая дата и время во время обработки обращений.
//   *
//   * @param day - дата и время.
//   * @return - возвращает true, если дата и время приходятся на время обработки обращений, и false во всех остальных случаях.
//   */
//  private static boolean isWorkingDayAndTime(LocalDateTime day) {
//    return isWorkingDay(day) && isWorkingTime(day);
//  }
//
//}
