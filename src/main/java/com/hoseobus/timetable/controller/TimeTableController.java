// timetable/controller/TimeTableController.java
@RestController
@RequestMapping("/api/timetable")
@RequiredArgsConstructor
public class TimeTableController {

    private final TimeTableFacadeService facade;
    private final TimeTableBusinessService service;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody TimeTableRequestDto dto) {
        facade.saveAllFromRequest(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<TimeTableResponseDto>> getTimetable(
            @RequestParam Long stationId,
            @RequestParam String day
    ) {
        return ResponseEntity.ok(service.getTimetables(stationId, day));
    }
}
