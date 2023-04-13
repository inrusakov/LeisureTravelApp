package hse.leisure.voter.utility;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class SerializationUtility {
    /**
     * Сериализация голосования.
     * @param mapList Лист с голосованием.
     * @return Сериализованное голосование.
     */
    public static String makeSerial(List<Map<String, String>> mapList) {
        String serial = "";
        for (Map<String, String> map : mapList) {
            serial += "<%" + map.get("num") + "<%" + map.get("count") + "<%" + map.get("selectionText");
        }
        return serial;
    }
}
