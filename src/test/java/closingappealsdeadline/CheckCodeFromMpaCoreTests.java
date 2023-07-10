package closingappealsdeadline;

import static closingappealsdeadline.ClosingAppealsDeadlineFastVersion.calculatingDeadlineFastVersion;
import static closingappealsdeadline.ClosingAppealsDeadlineLongVersion.DATE_TIME_FORMAT;
import static closingappealsdeadline.CodeFromMpaCore.calculateDeadlineDate;
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

public class CheckCodeFromMpaCoreTests {

  @DataProvider(name = "dataProvider", parallel = true)
  public static Object[][] dataProvider() throws IOException {
//    CreateAllValues.createAllValues();

    int hour = 9;
    String fileName = String.format(FILE_NAME_TEMPLATE, hour);
//    String fileName = String.format(FILE_NAME_TEMPLATE, "All");

    List<Object[]> list = Files.readAllLines(Paths.get(fileName))
        .stream()
        .map(row -> row.split(","))
        .collect(Collectors.toList());

    Object[][] matrix = new Object[list.size()][];
    return list.toArray(matrix);
  }

  @Test(dataProvider = "dataProvider")
  public void testTest(String createDateTime, String hours, String expectedValue) {
    LocalDateTime currentDateTime = LocalDateTime.parse(createDateTime, DATE_TIME_FORMAT);
//    String actualValue = calculateDeadlineDate(currentDateTime, Integer.parseInt(hours)).format(DATE_TIME_FORMAT);
    String actualValue = calculatingDeadlineFastVersion(currentDateTime, Integer.parseInt(hours)).format(DATE_TIME_FORMAT);

    assertEquals(
        actualValue, expectedValue,
        String.format("Ошибочка с датами %s : %s : %s", createDateTime, hours, actualValue)
    );

  }

}
