// com.hoshuttle.route.repository.RouteController.java

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes")
public class RouteController {

    @GetMapping
    public String getAllRoutes() {
        return "GET /routes";
    }

    @PostMapping
    public String createRoute() {
        return "POST /routes";
    }

    @PutMapping("/{id}")
    public String updateRoute(@PathVariable Long id) {
        return "PUT /routes/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteRoute(@PathVariable Long id) {
        return "DELETE /routes/" + id;
    }
}
