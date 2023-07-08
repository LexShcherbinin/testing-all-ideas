package closingdeadlineappeals;

import static closingdeadlineappeals.ClosingDeadlineAppeals.FORMAT;
import static closingdeadlineappeals.ClosingDeadlineAppeals.calculating;
import static org.testng.Assert.assertEquals;

import java.time.LocalDateTime;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ClosingDeadlineAppealsTests {

  @DataProvider(name = "dataProvider")
  public static Object[][] dataProvider() {
    return new Object[][] {
        {"07.07.2023 20:00", 8, "10.07.2023 18:00"},
        {"07.07.2023 20:00", 9, "10.07.2023 19:00"},
        {"07.07.2023 20:00", 10, "11.07.2023 11:00"},
    };
  }

  @Test(dataProvider = "dataProvider")
  public void testTest(String createDateTime, int hours, String expectedValue) {
    LocalDateTime currentDateTime = LocalDateTime.parse(createDateTime, FORMAT);
    String actualValue = calculating(currentDateTime, hours).format(FORMAT);

    assertEquals(
        actualValue, expectedValue,
        String.format("Ошибочка с датами %s : %s : %s", createDateTime, hours, actualValue)
    );

  }

}
