package hse.leisure.voter.controller;

import hse.leisure.voter.tool.GetDate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    /**
     * Метод для обработки перехода по ссылке для пустого аргумента.
     * @return Модель представления для основной страницы
     */
    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("YEAR", GetDate.year());
        return modelAndView;
    }

    /**
     * Метод для обработки запроса к /map.
     * @return Модель представления для запроса к map.
     */
    @RequestMapping("/map")
    public ModelAndView map() {
        ModelAndView modelAndView = new ModelAndView("/route/map");
        modelAndView.addObject("YEAR", GetDate.year());
        return modelAndView;
    }

    /**
     * Метод для обработки запроса к созданию голосования.
     * @return Модель представления для голосования.
     */
    @RequestMapping("/public/en")
    public ModelAndView voting() {
        ModelAndView modelAndView = new ModelAndView("/public/public_en");
        modelAndView.addObject("YEAR", GetDate.year());
        return modelAndView;
    }
}
