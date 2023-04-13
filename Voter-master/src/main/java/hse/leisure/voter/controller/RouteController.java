package hse.leisure.voter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hse.leisure.voter.dao.Route;
import hse.leisure.voter.model.Response;
import hse.leisure.voter.model.Views;
import hse.leisure.voter.repo.RouteRepo;
import hse.leisure.voter.tool.GetDate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;


@RestController
public class RouteController {

    /**
     * Репозиторий с маршрутами.
     */
    private final RouteRepo routeRepository;

    /**
     * Метод конструктора с ропозиторием.
     * @param routeRepository Репозиторий с маршрутами.
     */
    public RouteController(RouteRepo routeRepository) {
        this.routeRepository = routeRepository;
    }

    /**
     * Метод сохранения маршрута.
     * @param list Лист маркеров.
     * @param request Тело запроса.
     * @return Ответ с кодом.
     */
    @RequestMapping(value = "/sendRoute", method = RequestMethod.POST)
    public Response getUserRoute(@RequestBody ArrayList<String> list,
                                 HttpServletRequest request) {
        Response response = new Response();
        Route route = new Route();
        if (list.isEmpty()) {
            response.setCode("400");
            response.setMsg("Fields are not correct");
        } else {
            response.setCode("200");
            for (String s : list) {
                String[] coords = s.split(",");
                route.addMarker(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), coords[2]);
            }
            route = routeRepository.save(route);
            response.setMsg(route.getId().toString());
        }
        return response;
    }

    /**
     * Метод обновления маршрута.
     * @param route Тело обновленного маршрута.
     * @param request Тело запроса.
     * @return Ответ на запрос с кодом
     */
    @RequestMapping(value = "/updateRoute", method = RequestMethod.POST)
    public Response updateRoute(@RequestBody Route route,
                                HttpServletRequest request) {
        Response response = new Response();
        if (route.getId() == null) {
            response.setCode("400");
            response.setMsg("Fields are not correct");
        } else {
            response.setCode("200");
            response.setMsg("Correct");
        }

        routeRepository.save(route);
        return response;
    }

    /**
     * Проверка есть ли маршрут по айди.
     * @param VID Номер маршрута.
     * @return Код существования маршрута
     */
    @RequestMapping("/checkRouteID/{VID}")
    @ResponseBody
    public Integer checkVoteID(@PathVariable Integer VID) {
        try {
            routeRepository.getOne(VID);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Метод для получения маршрута.
     * @param routeId Номер маршрута.
     * @param model Модель представлеения.
     * @return Модель представления с маршрутом.
     */
    @JsonView(Views.Public.class)
    @GetMapping("/routeObserver/{routeId}")
    public ModelAndView observeRoute(@PathVariable("routeId") Integer routeId, Model model) {
        Optional<Route> optionalRoute = routeRepository.findById(routeId);
        ModelAndView modelAndView = new ModelAndView("/route/route_index");
        modelAndView.addObject("route", optionalRoute.get().getMarkers());
        modelAndView.addObject("id", optionalRoute.get().getId());
        modelAndView.addObject("markers", optionalRoute.get().getMarkers());
        modelAndView.addObject("YEAR", GetDate.year());
        model.addAttribute("route", optionalRoute.get().getMarkers());
        model.addAttribute("id", optionalRoute.get().getId());
        model.addAttribute("markers", optionalRoute.get().getMarkers());
        return modelAndView;
    }

    /**
     * Метод для редактирования маршрута.
     * @param routeId Номер маршрута для редактирования.
     * @param model Модель представления.
     * @return Перенаправление на редактирование маршрута.
     */
    @GetMapping("/editRoute/{routeId}")
    public String editPost(@PathVariable("routeId") Integer routeId, Model model) {
        model.addAttribute("route", (Route) routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid route Id:" + routeId)));
        return "editRoute";
    }
}
