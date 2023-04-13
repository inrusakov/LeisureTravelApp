package hse.leisure.voter.controller;

import com.google.common.util.concurrent.RateLimiter;
import hse.leisure.voter.dao.Vote;
import hse.leisure.voter.limit.IpUtilityTool;
import hse.leisure.voter.limit.TimeIntervalService;
import hse.leisure.voter.mapper.VoteMapper;
import hse.leisure.voter.tool.GetDate;
import hse.leisure.voter.utility.VotingUtility;
import hse.leisure.voter.utility.SerializationUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Controller
public class VoteController {
    /**
     * Хранилище голосований.
     */
    final VoteMapper voteMapper;

    /**
     * Сервис для обработки таймаута при голосовании.
     */
    @Resource
    TimeIntervalService loadingCacheService;

    /**
     * Конструктор контроллера.
     * @param voteMapper Хранилище для голосований.
     */
    public VoteController(VoteMapper voteMapper) {
        this.voteMapper = voteMapper;
    }

    /**
     * Получение голосования по номеру.
     * @param VID Номер голосования.
     * @return Модель представления с голосованием.
     */
    @RequestMapping("/vote/en/{VID}")
    public ModelAndView getVotingByID(@PathVariable Integer VID) {
        Vote vote = voteMapper.getVote(VID);
        ModelAndView modelAndView = new ModelAndView("/vote/index_en");
        modelAndView.addObject("VoteID", vote.getVID());
        modelAndView.addObject("Title", vote.getTitle());
        modelAndView.addObject("Describe", vote.getDescribe());
        modelAndView.addObject("Type", vote.getType());
        modelAndView.addObject("Limit", vote.getLimit());
        List<Map<String, String>> votes = VotingUtility.analyze(vote.getSelection());
        modelAndView.addObject("Selection", votes);
        modelAndView.addObject("YEAR", GetDate.year());
        return modelAndView;
    }

    /**
     * Проверка существования голосования.
     * @param VID Номер голосования.
     * @return Код проверки на существование.
     */
    @RequestMapping("/checkVoteID/{VID}")
    @ResponseBody
    public Integer checkVoteID(@PathVariable Integer VID) {
        try {
            voteMapper.checkVote(VID).getVID();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Метод создания голосования.
     * @param title Название.
     * @param describe Описание.
     * @param selection Режим выбора.
     * @param type Тип голосования.
     * @param limit Литим.
     * @return Код проверки.
     */
    @RequestMapping("/voterSubmit")
    @ResponseBody
    public Integer voterSubmit(String title, String describe, String selection, Integer type, Integer limit) {
        //Verify
        if (Objects.equals(title, "")) {
            return -400;
        }
        if (Objects.equals(describe, "")) {
            return -400;
        }
        if (limit < -1 || limit == 0) {
            return -400;
        }
        try {
            voteMapper.insertVote(title, describe, selection, type, limit);
            Integer VID = voteMapper.queryVoteID(title, describe, selection, type, limit);
            return VID;
        } catch (Exception E) {
            E.printStackTrace();
            return -1;
        }
    }

    /**
     * Метод отправки голоса в голосовании.
     * @param request Запрос голосования.
     * @param VID Номер голосования.
     * @param selected Выбор.
     * @return Код проверки.
     */
    @RequestMapping("/submitVote")
    @ResponseBody
    public String submitVote(HttpServletRequest request, Integer VID, String selected) {
        String ipAddress = IpUtilityTool.getIpAddress(request);
        String ipWithVID = ipAddress + ":" + VID;
        try {
            RateLimiter limiter = loadingCacheService.getRateLimiter(ipWithVID);
            boolean localAccess = ipAddress.equals("0:0:0:0:0:0:0:1");
            // Check ip address for access.
            if (limiter.tryAcquire() || localAccess) {
                // Checking information about voting.
                Vote vote = voteMapper.getVote(VID);
                String selectionSerial = vote.getSelection();
                List<Map<String, String>> selects = VotingUtility.analyze(selectionSerial);
                String[] selectedList = selected.split(",");
                Integer[] selList = new Integer[selectedList.length];
                int i = 0;
                for (String sel : selectedList) {
                    selList[i] = Integer.parseInt(sel);
                    ++i;
                }

                // Inserting changes.
                List<Map<String, String>> newList = new ArrayList<Map<String, String>>();
                for (Map<String, String> stringMap : selects) {
                    boolean flag = false;
                    for (Integer sel : selList) {
                        if (Integer.parseInt(stringMap.get("num")) == sel) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        int before = Integer.parseInt(stringMap.get("count"));
                        Integer after = before + 1;
                        stringMap.put("count", String.valueOf(after));
                    }
                    newList.add(stringMap);
                }

                // Serialization.
                String serial = SerializationUtility.makeSerial(newList);

                // Db Update.
                voteMapper.vote(serial, VID);
                return "1";
            } else {
                return "0";
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "0";
    }
}
