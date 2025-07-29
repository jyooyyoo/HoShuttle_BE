// com.hoshuttle.station.repository.StationRepository.java

import com.hoseobus.station.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StationRepository extends JpaRepository<Station, Long> {

    @Query(value = "SELECT * FROM station " +
            "ORDER BY ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(longitude, latitude)) ASC " +
            "LIMIT 1", nativeQuery = true)
    Station findNearest(@Param("latitude") double latitude, @Param("longitude") double longitude);

    List<Station> findAllByRouteNameOrderByOrderInRoute(String routeName);

    Optional<Station> findByName(String name); // <--- 이 라인 추가
}