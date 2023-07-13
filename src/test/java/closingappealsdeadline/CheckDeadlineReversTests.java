package closingappealsdeadline;

import static closingappealsdeadline.ClosingAppealsDeadlineFastVersion.DATE_TIME_FORMAT;
import static closingappealsdeadline.ClosingAppealsDeadlineFastVersion.calculatingDeadlineFastVersion;
import static closingappealsdeadline.ClosingAppealsDeadlineRevers.calculatingMinutes;
import static closingappealsdeadline.CreateTestDataValues.FILE_NAME_TEMPLATE;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckDeadlineReversTests {

  @DataProvider(name = "dataProvider")
  public static Object[][] dataProvider() throws IOException {
    String fileName = String.format(FILE_NAME_TEMPLATE, "Revers");

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
    long actualValue = calculatingMinutes(startDateTime, expectedEndDateTime);

    assertEquals(
        actualValue, Long.parseLong(expectedHours),
        String.format("Ошибочка с часами %s : %s : %s", start, endDay, actualValue)
    );

  }

}
