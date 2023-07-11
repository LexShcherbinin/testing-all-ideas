package closingappealsdeadline;

import static closingappealsdeadline.ClosingAppealsDeadlineFastVersion.calculatingDeadlineFastVersion;
import static closingappealsdeadline.ClosingAppealsDeadlineLongVersion.DATE_TIME_FORMAT;
import static closingappealsdeadline.ClosingAppealsDeadlineLongVersion.calculatingDeadlineLongVersion;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class CreateTestDataValues {
  private static final LocalDateTime START_DATE_TIME = LocalDateTime.parse("04.07.2023 00:00", DATE_TIME_FORMAT);
  private static final LocalDateTime END_DATE_TIME = LocalDateTime.parse("12.07.2023 23:59", DATE_TIME_FORMAT);
//  private static final String RESULT_TEMPLATE = "{\"%s\", %s, \"%s\"},\n";
  private static final String RESULT_TEMPLATE = "%s,%s,%s\n";

  public static final String FILE_NAME_TEMPLATE = "src/main/resources/closingappealsdeadline/Hours%s.txt";

  public static void main(String[] args) throws IOException {
//    createOneFilePerHour();
    createOneFileWithAllHours();
  }

  public static void createOneFilePerHour() throws IOException {
    for (int hour = 1; hour <= 180; hour++) {
      LocalDateTime actualDateTime = START_DATE_TIME;
      String fileName = String.format(FILE_NAME_TEMPLATE, hour);

      //Создание выходного файла с удалением содержимого
      FileWriter outputFile = new FileWriter(fileName, false);

      while (actualDateTime.getDayOfYear() <= END_DATE_TIME.getDayOfYear()) {
        actualDateTime = actualDateTime.plusMinutes(1);
        LocalDateTime expectedDateTime = calculatingDeadlineLongVersion(actualDateTime, hour);
//        LocalDateTime expectedDateTime = calculatingDeadlineFastVersion(actualDateTime, hour);

        String result = String.format(
            RESULT_TEMPLATE,
            actualDateTime.format(DATE_TIME_FORMAT), hour, expectedDateTime.format(DATE_TIME_FORMAT)
        );

        System.out.print(result);
        outputFile.write(result);
      }

      //Закрытие выходного файла
      outputFile.close();

    }
  }

  public static void createOneFileWithAllHours() throws IOException {
    String fileName = String.format(FILE_NAME_TEMPLATE, "All");

    //Создание выходного файла с удалением содержимого
    FileWriter outputFile = new FileWriter(fileName, false);

    for (int hour = 1; hour <= 1000; hour++) {
      LocalDateTime actualDateTime = START_DATE_TIME;

      while (actualDateTime.getDayOfYear() <= END_DATE_TIME.getDayOfYear()) {
        actualDateTime = actualDateTime.plusMinutes(15);
        LocalDateTime expectedDateTime = calculatingDeadlineLongVersion(actualDateTime, hour);

        String result = String.format(
            RESULT_TEMPLATE,
            actualDateTime.format(DATE_TIME_FORMAT), hour, expectedDateTime.format(DATE_TIME_FORMAT)
        );

        System.out.print(result);
        outputFile.write(result);
      }

    }

    //Закрытие выходного файла
    outputFile.close();
  }

}
