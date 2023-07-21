package closingappealsdeadline;

import static closingappealsdeadline.WorkingDateTimeHelper.DATE_TIME_FORMAT;
import static closingappealsdeadline.WorkingDateTimeHelper.ROUNDING_MINUTES;
import static closingappealsdeadline.WorkingDateTimeHelper.TIME_FROM;
import static closingappealsdeadline.WorkingDateTimeHelper.WORKING_DAYS;
import static closingappealsdeadline.WorkingDateTimeHelper.WORKING_HOURS;
import static closingappealsdeadline.WorkingDateTimeHelper.isAfterWorkingTime;
import static closingappealsdeadline.WorkingDateTimeHelper.isBeforeWorkingTime;
import static closingappealsdeadline.WorkingDateTimeHelper.isWorkingDay;
import static closingappealsdeadline.WorkingDateTimeHelper.isWorkingDayAndTime;
import static closingappealsdeadline.WorkingDateTimeHelper.isWorkingTime;

import java.time.LocalDateTime;

/**
 * Расчёт времени дедлайна закрытия обращения.
 * <p>
 * Рабочие часы: пн - пт с 10:00 до 19:00. Обращение может быть заведено в любое время. Часы на выполнение обращения
 * принимают значение от 1 до 1000. Обращение считается принятым в работу в момент подачи. Если обращение подано в
 * нерабочее время, начало выполнения работ по нему переносится на 10:00 следующего рабочего дня. Если дедлайн выпадает
 * на 19:00, то он переносится на 10:00 следующего рабочего дня.
 */
public class ClosingAppealsDeadline {

  public static void main(String[] args) {
    var currentDateTime = LocalDateTime.parse("04.07.2023 19:03", DATE_TIME_FORMAT);
    int hourForTask = 9;

    String result = calculatingDeadlineFastVersion(currentDateTime, hourForTask).format(DATE_TIME_FORMAT);
    System.out.println(result);
  }

  /**
   * Расчёт дедлайна выполнения обращения. Быстрая (правильная) версия. Закомментированные участки кода переносят
   * дедлайн с 19:00 на 10:00 следующего рабочего дня.
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

//    if (deadline.getDayOfWeek().getValue() == 7) {
//      deadline = deadline.plusDays(1);
//
//    } else if (deadline.getDayOfWeek().getValue() == 6) {
//      deadline = deadline.plusDays(2);
//    }

    while (!isWorkingDay(deadline)) {
      deadline = deadline.plusDays(1);
    }

    if (deadline.getDayOfWeek().getValue() + hoursAsDay > WORKING_DAYS) {
      deadline = deadline.plusDays(2);
    }

    deadline = deadline.plusWeeks(hoursAsWeek).plusDays(hoursAsDay);

//    Возможно, не нужное условие. Требуется для учёта праздничных выходных дней.
//    while (!isWorkingDay(deadline)) {
//      deadline = deadline.plusDays(1);
//    }

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

}
