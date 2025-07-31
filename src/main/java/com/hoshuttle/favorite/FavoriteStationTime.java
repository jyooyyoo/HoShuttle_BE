package com.hoshuttle.favorite;

@Entity
@Table(name = "favorite_station_time")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteStationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    private String time;      // HH:mm 형식
    private String dayType;   // WEEKDAY, WEEKEND 등

    private LocalDateTime createdAt;

    @Builder
    public FavoriteStationTime(User user, Station station, String time, String dayType) {
        this.user = user;
        this.station = station;
        this.time = time;
        this.dayType = dayType;
        this.createdAt = LocalDateTime.now();
    }
}
