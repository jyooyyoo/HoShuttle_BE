package com.hoshuttle.favorite;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite/stations")
public class FavoriteStationController {

    private final FavoriteStationService favoriteStationService;

    @PostMapping
    public ResponseEntity<FavoriteStation> createFavoriteStation(@RequestBody FavoriteStationDto.Create dto) {
        FavoriteStation created = favoriteStationService.createFavoriteStation(
                dto.getUserId(), dto.getStationId(), dto.getPreferredTime()
        );
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoriteStation>> getFavoriteStations(@PathVariable Long userId) {
        return ResponseEntity.ok(favoriteStationService.getFavoritesByUser(userId));
    }

    @DeleteMapping("/{stationId}")
    public ResponseEntity<Void> deleteFavoriteStation(@PathVariable Long stationId) {
        favoriteStationService.deleteFavoriteStation(stationId);
        return ResponseEntity.noContent().build();
    }
}
