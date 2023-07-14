package closingappealsdeadline;

import static closingappealsdeadline.CodeFromMpaCore2.calculateBetweenDateMinutes;
import static closingappealsdeadline.CreateTestDataValuesForMinuteBetween.FILE_NAME_TEMPLATE;
import static closingappealsdeadline.WorkingDateTimeHelper.DATE_TIME_FORMAT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MinutesBetweenDatesTests {

  @DataProvider(name = "dataProvider")
  public static Object[][] dataProvider() throws IOException {
//    String fileName = String.format(FILE_NAME_TEMPLATE, "Liza");
    String fileName = String.format(FILE_NAME_TEMPLATE, "All");

    List<Object[]> list = Files.readAllLines(Paths.get(fileName))
        .stream()
        .map(row -> row.split(","))
        .collect(Collectors.toList());

    Object[][] matrix = new Object[list.size()][];
    return list.toArray(matrix);
  }

  @Test(dataProvider = "dataProvider")
  public void testTest(String start, String endDay, String expectedHours) {
    LocalDateTime startDateTime = LocalDateTime.parse(start, DATE_TIME_FORMAT);
    LocalDateTime expectedEndDateTime = LocalDateTime.parse(endDay, DATE_TIME_FORMAT);
//    long actualValue = calculatingMinutes(startDateTime, expectedEndDateTime);
    long actualValue = calculateBetweenDateMinutes(startDateTime, expectedEndDateTime);

    assertEquals(
        actualValue, Long.parseLong(expectedHours),
        String.format("Ошибочка с часами %s : %s : %s", start, endDay, actualValue)
    );

  }

}
