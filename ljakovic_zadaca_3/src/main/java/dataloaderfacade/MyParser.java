package dataloaderfacade;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Luka Jaković
 */
class MyParser {

    public Integer tryParseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            return null;
        }
    }

    public Date tryParseDate(String date) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public Date tryParseDateTime(String date) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(date);
        } catch (Exception e) {
            return null;
        }
    }

}
