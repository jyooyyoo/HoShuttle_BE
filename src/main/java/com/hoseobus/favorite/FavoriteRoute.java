@Entity
@Table(name = "favorite_route")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_station_id", nullable = false)
    private Station fromStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_station_id", nullable = false)
    private Station toStation;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long departureStationId;

    private Long arrivalStationId;

    private LocalTime preferredTime;

    private boolean isDeleted = false; // soft delete

    @Builder
    public FavoriteRoute(User user, Station fromStation, Station toStation) {
        this.user = user;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.createdAt = LocalDateTime.now();
    }
}
