package closingappealsdeadline.createtestdata;

import static closingappealsdeadline.MinutesBetweenDates.calculatingMinutes;
import static closingappealsdeadline.WorkingDateTimeHelper.DATE_TIME_FORMAT;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Вспомогательный класс для создания тестовых данных для проверки расчёта дедлайна.
 */
public class CreateTestDataValuesForMinuteBetween {

  private static final LocalDateTime START_DATE_TIME = LocalDateTime.parse("05.07.2023 00:00", DATE_TIME_FORMAT);
  private static final LocalDateTime END_DATE_TIME = LocalDateTime.parse("24.07.2023 23:59", DATE_TIME_FORMAT);
  private static final String RESULT_TEMPLATE = "%s,%s,%s\n";

  public static final String FILE_NAME_TEMPLATE = "src/main/resources/minutebetween/MinuteBetween%s.txt";

  public static void main(String[] args) throws IOException {
    createOneFileWithAllValues();
  }

  public static void createOneFileWithAllValues() throws IOException {
    LocalDateTime createDateTime = START_DATE_TIME;
    LocalDateTime closeDateTime = END_DATE_TIME;

    String fileName = String.format(FILE_NAME_TEMPLATE, "All");

    //Создание выходного файла с удалением содержимого
    FileWriter outputFile = new FileWriter(fileName, false);

    while (createDateTime.isBefore(closeDateTime)) {
      while (createDateTime.isBefore(closeDateTime)) {
        createDateTime = createDateTime.plusHours(1).plusMinutes(3);
        long minutes = calculatingMinutes(createDateTime, closeDateTime);

        String result = String.format(
            RESULT_TEMPLATE,
            createDateTime.format(DATE_TIME_FORMAT), closeDateTime.format(DATE_TIME_FORMAT), minutes
        );

        System.out.print(result);
        outputFile.write(result);
      }

      createDateTime = START_DATE_TIME;
      closeDateTime = closeDateTime.minusHours(1);
    }

    //Закрытие выходного файла
    outputFile.close();
  }

}
