package closingdeadlineappeals;

import static closingdeadlineappeals.ClosingDeadlineAppeals.DATE_TIME_FORMAT;
import static closingdeadlineappeals.ClosingDeadlineAppeals.calculatingDeadline;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class CreateAllValues {

  public static void main(String[] args) throws IOException {
    createAllValues();
  }

  private static void createAllValues() throws IOException {
    LocalDateTime start = LocalDateTime.parse("04.07.2023 00:00", DATE_TIME_FORMAT);
    LocalDateTime end = LocalDateTime.parse("12.07.2023 23:59", DATE_TIME_FORMAT);

    for (int hour = 1; hour <= 48; hour++) {
      LocalDateTime actualDateTime = start;
      String fileName = String.format("src/main/resources/closingdeadlineappeals/Hours%s.txt", hour);

      //Создание выходного файла с удалением содержимого
      FileWriter outputFile = new FileWriter(fileName, false);

      while (actualDateTime.getDayOfYear() <= end.getDayOfYear()) {
        actualDateTime = actualDateTime.plusMinutes(1);
        LocalDateTime expectedDateTime = calculatingDeadline(actualDateTime, hour);

        String result = String.format(
            "{\"%s\", %s, \"%s\"},\n",
            actualDateTime.format(DATE_TIME_FORMAT), hour, expectedDateTime.format(DATE_TIME_FORMAT)
        );

        System.out.print(result);

        outputFile.write(result);
      }

      //Закрытие выходного файла
      outputFile.close();

    }
  }

}
