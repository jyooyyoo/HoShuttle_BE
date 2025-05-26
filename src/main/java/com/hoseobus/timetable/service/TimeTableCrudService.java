// timetable/service/TimeTableCrudService.java
@Service
@RequiredArgsConstructor
public class TimeTableCrudService {

    private final TimeTableRepository repository;

    public TimeTable save(TimeTable timeTable) {
        return repository.save(timeTable);
    }

    public List<TimeTable> findByStationAndDay(Long stationId, TimeTable.DayType dayType) {
        return repository.findByStationIdAndDayType(stationId, dayType);
    }

    public TimeTable findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TimeTableNotFoundException("com.hoseobus.timetable.domain.TimeTable not found: " + id));
    }
}
