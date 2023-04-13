package hse.leisure.voter.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate {
    /**
     * Получить текущий год.
     * @return Текущий год.
     */
    public static Integer year() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return Integer.parseInt(simpleDateFormat.format(date));
    }
}
