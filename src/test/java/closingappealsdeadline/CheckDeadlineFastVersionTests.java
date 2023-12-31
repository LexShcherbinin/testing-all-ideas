package closingappealsdeadline;

import static closingappealsdeadline.ClosingAppealsDeadline.calculatingDeadlineFastVersion;
import static closingappealsdeadline.WorkingDateTimeHelper.DATE_TIME_FORMAT;
import static org.testng.Assert.assertEquals;

import java.time.LocalDateTime;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckDeadlineFastVersionTests {

  @DataProvider(name = "dataProvider")
  public static Object[][] dataProvider() {
    return new Object[][]{
        {"07.07.2023 19:15", 2, "10.07.2023 12:00"},
        {"07.07.2023 19:30", 8, "10.07.2023 18:00"},
        {"07.07.2023 19:45", 9, "10.07.2023 19:00"},
        {"07.07.2023 20:00", 12, "11.07.2023 13:00"},
        {"07.07.2023 20:00", 18, "11.07.2023 19:00"},
//        {"07.07.2023 19:15", 0, "07.07.2023 19:15"},

        {"08.07.2023 19:15", 2, "10.07.2023 12:00"},
        {"08.07.2023 19:30", 8, "10.07.2023 18:00"},
        {"08.07.2023 19:45", 9, "10.07.2023 19:00"},
        {"08.07.2023 20:00", 12, "11.07.2023 13:00"},
        {"08.07.2023 20:00", 18, "11.07.2023 19:00"},
//        {"08.07.2023 19:15", 0, "10.07.2023 10:00"},

        {"09.07.2023 19:15", 2, "10.07.2023 12:00"},
        {"09.07.2023 19:30", 8, "10.07.2023 18:00"},
        {"09.07.2023 19:45", 9, "10.07.2023 19:00"},
        {"09.07.2023 20:00", 12, "11.07.2023 13:00"},
        {"09.07.2023 20:00", 18, "11.07.2023 19:00"},
//        {"09.07.2023 19:15", 0, "10.07.2023 10:00"},

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

        //Создание обращения в рабочий день и дедлайн выпадает на рабочий день (дедлайн до 9 часов)
        {"07.07.2023 11:00", 1, "07.07.2023 12:00"},
        {"07.07.2023 11:00", 2, "07.07.2023 13:00"},
        {"07.07.2023 11:00", 4, "07.07.2023 15:00"},
        {"07.07.2023 10:00", 8, "07.07.2023 18:00"},
        {"07.07.2023 10:00", 9, "07.07.2023 19:00"},
        //Создание обращения в рабочий день (понедельник - четверг) с переходом дат (дедлайн 9 и более часов)
        {"04.07.2023 12:00", 9, "05.07.2023 12:00"},
        {"04.07.2023 12:00", 10, "05.07.2023 13:00"},
        {"04.07.2023 12:00", 18, "06.07.2023 12:00"},
        {"04.07.2023 12:00", 27, "07.07.2023 12:00"},
        {"04.07.2023 12:00", 45, "11.07.2023 12:00"},
        {"04.07.2023 12:00", 180, "01.08.2023 12:00"},
        {"04.07.2023 12:00", 360, "29.08.2023 12:00"},
        //Создание обращения в рабочий день, деделайн которых получается на выходной. Проверка учета выходных и перехода дат
        {"07.07.2023 18:30", 1, "10.07.2023 10:30"},
        {"07.07.2023 18:00", 2, "10.07.2023 11:00"},
        {"07.07.2023 18:00", 4, "10.07.2023 13:00"},
        {"07.07.2023 18:00", 8, "10.07.2023 17:00"},
        {"07.07.2023 18:00", 9, "10.07.2023 18:00"},
        {"07.07.2023 18:00", 10, "10.07.2023 19:00"},
        {"06.07.2023 18:00", 10, "07.07.2023 19:00"},
        {"07.07.2023 18:00", 18, "11.07.2023 18:00"},
        {"06.07.2023 18:00", 27, "11.07.2023 18:00"},
        {"07.07.2023 18:00", 27, "12.07.2023 18:00"},
        {"07.07.2023 18:00", 45, "14.07.2023 18:00"},
        {"07.07.2023 18:00", 180, "04.08.2023 18:00"},
        {"07.07.2023 18:00", 360, "01.09.2023 18:00"},
        //Создание обращения в нерабочее время (до 12:00 ночи). Проверка учета выходных
        {"07.07.2023 20:00", 1, "10.07.2023 11:00"},
        {"07.07.2023 20:00", 2, "10.07.2023 12:00"},
        {"07.07.2023 20:00", 4, "10.07.2023 14:00"},
        {"07.07.2023 20:00", 8, "10.07.2023 18:00"},
        {"07.07.2023 20:00", 9, "10.07.2023 19:00"},
        {"07.07.2023 20:00", 10, "11.07.2023 11:00"},
        {"07.07.2023 20:00", 18, "11.07.2023 19:00"},
        {"07.07.2023 20:00", 27, "12.07.2023 19:00"},
        {"07.07.2023 20:00", 45, "14.07.2023 19:00"},
        {"07.07.2023 20:00", 180, "04.08.2023 19:00"},
        {"07.07.2023 20:00", 360, "01.09.2023 19:00"},
        //Создание обращения в нерабочее время (после 12:00 ночи).
        {"06.08.2023 01:00", 1, "07.08.2023 11:00"},
        {"06.08.2023 01:00", 2, "07.08.2023 12:00"},
        {"06.08.2023 01:00", 4, "07.08.2023 14:00"},
        {"06.08.2023 01:00", 8, "07.08.2023 18:00"},
        {"06.08.2023 01:00", 9, "07.08.2023 19:00"},
        {"06.08.2023 01:00", 10, "08.08.2023 11:00"},
        {"06.08.2023 01:00", 18, "08.08.2023 19:00"},
        {"06.08.2023 01:00", 27, "09.08.2023 19:00"},
        {"06.08.2023 01:00", 45, "11.08.2023 19:00"},
        {"06.08.2023 01:00", 180, "01.09.2023 19:00"},
        {"06.08.2023 01:00", 360, "29.09.2023 19:00"},
        //Граничные значения
        {"06.07.2023 19:00", 1, "07.07.2023 11:00"},
        {"07.07.2023 19:00", 1, "10.07.2023 11:00"},
        {"08.07.2023 19:00", 1, "10.07.2023 11:00"},
    };
  }

  @Test(dataProvider = "dataProvider")
  public void testTest(String createDateTime, int hours, String expectedValue) {
    LocalDateTime currentDateTime = LocalDateTime.parse(createDateTime, DATE_TIME_FORMAT);
    String actualValue = calculatingDeadlineFastVersion(currentDateTime, hours).format(DATE_TIME_FORMAT);

    assertEquals(
        actualValue, expectedValue,
        String.format("Ошибочка с датами %s : %s : %s", createDateTime, hours, actualValue)
    );

  }

}
