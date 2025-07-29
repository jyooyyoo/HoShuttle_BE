@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite/routes")
public class FavoriteRouteController {

    private final FavoriteRouteService favoriteRouteService;

    @PostMapping
    public ResponseEntity<FavoriteRoute> createFavoriteRoute(@RequestBody FavoriteRouteDto.Create dto) {
        FavoriteRoute created = favoriteRouteService.createFavoriteRoute(
                dto.getUserId(), dto.getDepartureStationId(), dto.getArrivalStationId(), dto.getPreferredTime()
        );
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoriteRoute>> getFavoriteRoutes(@PathVariable Long userId) {
        return ResponseEntity.ok(favoriteRouteService.getFavoritesByUser(userId));
    }

    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteFavoriteRoute(@PathVariable Long routeId) {
        favoriteRouteService.deleteFavoriteRoute(routeId);
        return ResponseEntity.noContent().build();
    }
}
