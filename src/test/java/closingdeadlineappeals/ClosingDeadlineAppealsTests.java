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
        {"07.07.2023 19:15", 2, "10.07.2023 12:00"},
        {"07.07.2023 19:30", 8, "10.07.2023 18:00"},
        {"07.07.2023 19:45", 9, "10.07.2023 19:00"},
        {"07.07.2023 20:00", 12, "11.07.2023 13:00"},
        {"07.07.2023 20:00", 18, "11.07.2023 19:00"},

        {"08.07.2023 19:15", 2, "10.07.2023 12:00"},
        {"08.07.2023 19:30", 8, "10.07.2023 18:00"},
        {"08.07.2023 19:45", 9, "10.07.2023 19:00"},
        {"08.07.2023 20:00", 12, "11.07.2023 13:00"},
        {"08.07.2023 20:00", 18, "11.07.2023 19:00"},

        {"09.07.2023 19:15", 2, "10.07.2023 12:00"},
        {"09.07.2023 19:30", 8, "10.07.2023 18:00"},
        {"09.07.2023 19:45", 9, "10.07.2023 19:00"},
        {"09.07.2023 20:00", 12, "11.07.2023 13:00"},
        {"09.07.2023 20:00", 18, "11.07.2023 19:00"},

        {"07.07.2023 01:15", 2, "07.07.2023 12:00"},
        {"07.07.2023 02:30", 8, "07.07.2023 18:00"},
        {"07.07.2023 03:45", 9, "07.07.2023 19:00"},
        {"07.07.2023 04:00", 12, "10.07.2023 13:00"},
        {"07.07.2023 05:00", 18, "10.07.2023 19:00"},

        {"08.07.2023 01:15", 2, "10.07.2023 12:00"},
        {"08.07.2023 02:30", 8, "10.07.2023 18:00"},
        {"08.07.2023 03:45", 9, "10.07.2023 19:00"},
        {"08.07.2023 04:00", 12, "11.07.2023 13:00"},
        {"08.07.2023 05:00", 18, "11.07.2023 19:00"},

        {"09.07.2023 01:15", 2, "10.07.2023 12:00"},
        {"09.07.2023 02:30", 8, "10.07.2023 18:00"},
        {"09.07.2023 03:45", 9, "10.07.2023 19:00"},
        {"09.07.2023 04:00", 12, "11.07.2023 13:00"},
        {"09.07.2023 05:00", 18, "11.07.2023 19:00"},

        {"07.07.2023 12:15", 2, "07.07.2023 14:15"},
        {"07.07.2023 12:30", 8, "10.07.2023 11:30"},
        {"07.07.2023 12:45", 9, "10.07.2023 12:45"},
        {"07.07.2023 17:30", 12, "11.07.2023 11:30"},
        {"07.07.2023 18:00", 18, "11.07.2023 18:00"},

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
