import com.backend.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * @author Skynet
 * @date 2017年04月26日 14:36
 */
public class MainTest {

    public static void main(String[] args) throws ParseException {
        String s = UUID.randomUUID().toString();
        String menu_code = s.substring(0, 8) + s.substring(9, 13)
                + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
        System.out.println(menu_code);
    }

}
