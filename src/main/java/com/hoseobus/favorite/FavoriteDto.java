public class FavoriteRouteDto {
    @Getter @Setter
    public static class Create {
        private Long userId;
        private Long departureStationId;
        private Long arrivalStationId;
        private LocalTime preferredTime;
    }
}

public class FavoriteStationDto {
    @Getter @Setter
    public static class Create {
        private Long userId;
        private Long stationId;
        private LocalTime preferredTime;
    }
}
