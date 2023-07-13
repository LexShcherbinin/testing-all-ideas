package closingappealsdeadline;

import static closingappealsdeadline.WorkingDateTimeHelper.DATE_TIME_FORMAT;
import static closingappealsdeadline.WorkingDateTimeHelper.TIME_FROM;
import static closingappealsdeadline.WorkingDateTimeHelper.TIME_TO;
import static closingappealsdeadline.WorkingDateTimeHelper.WORKING_DAYS;
import static closingappealsdeadline.WorkingDateTimeHelper.WORKING_HOURS;
import static closingappealsdeadline.WorkingDateTimeHelper.WORKING_MINUTES;
import static closingappealsdeadline.WorkingDateTimeHelper.isAfterWorkingTime;
import static closingappealsdeadline.WorkingDateTimeHelper.isBeforeWorkingTime;
import static closingappealsdeadline.WorkingDateTimeHelper.isWorkingDay;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Расчёт количества минут, пройденных между созданием и закрытием обращения с учётом рабочих часов.
 * <p>
 * Рабочие часы: пн - пт с 10:00 до 19:00. Обращение может быть и создано и закрыто в любое время. Учитывается время
 * только в рабочие часы. Если обращение создано в пятницу в 19:01, а закрыто в воскресенье в 12:01, то затраченное
 * время будет равно 0 минут.
 */
public class MinutesBeforeDates {

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

}
