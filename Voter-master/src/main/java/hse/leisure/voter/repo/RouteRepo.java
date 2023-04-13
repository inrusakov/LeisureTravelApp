package hse.leisure.voter.repo;

import hse.leisure.voter.dao.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepo extends JpaRepository<Route, Integer> {
    /**
     * Получить все маршруты.
     * @param pageable Pageable.
     * @return Страницу с маршрутами.
     */
    Page<Route> findAll(Pageable pageable);
}
