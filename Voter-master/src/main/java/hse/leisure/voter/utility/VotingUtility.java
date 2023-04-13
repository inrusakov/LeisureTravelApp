package hse.leisure.voter.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotingUtility {
    /**
     * Проверка пунктов голосования.
     * @param words Пункты голосования.
     * @return Сериализованное голосование.
     */
    public static List<Map<String, String>> analyze(String words) {
        String[] votingList = words.replaceAll("\n", "").split("<%");

        // Vote counting.
        List<Map<String, String>> selectionList = new ArrayList<Map<String, String>>();
        float countVotes = 0;
        countVotes = getCountVotes(votingList, selectionList, countVotes);

        // Result counting.
        extractionCheck(selectionList, countVotes);
        return selectionList;
    }

    /**
     * Получение списка голосования.
     * @param selectionList Лист с голосованиями.
     * @param countVotes Число голосов.
     */
    private static void extractionCheck(List<Map<String, String>> selectionList, float countVotes) {
        for (Map<String, String> entry : selectionList) {
            // 0 amount check.
            if (0 == countVotes) {
                countVotes = 100;
            }

            // Border values check.
            float percent = Integer.parseInt(entry.get("count")) / countVotes;
            if (percent > 0.85F) {
                percent = 0.85F;
            }
            if (percent < 0.05F) {
                percent = 0.05F;
            }
            StringBuilder percentStr = new StringBuilder(String.valueOf(percent));
            percentStr = new StringBuilder(percentStr.toString().split("\\.")[1]);
            if (percentStr.length() != 2) {
                int add = 2 - percentStr.length();
                for (int i = 0; i < add; ++i) {
                    percentStr.append("0");
                }
            }
            entry.put("percent", percentStr.toString());
        }
    }

    /**
     * Посчет голосов.
     * @param votingList Список пунктов с голосами.
     * @param selectionList Лист выборов.
     * @param countVotes Число кандидатов.
     * @return Число голосов.
     */
    private static float getCountVotes(String[] votingList, List<Map<String, String>> selectionList, float countVotes) {
        for (int i = 1; i < votingList.length; i++) {
            Map<String, String> selectMap = new HashMap<String, String>();
            selectMap.put("num", votingList[i]);
            ++i;
            selectMap.put("count", votingList[i]);
            countVotes = countVotes + Integer.parseInt(votingList[i]);
            ++i;
            selectMap.put("selectionText", votingList[i]);
            selectionList.add(selectMap);
        }
        return countVotes;
    }
}
