package com.hoshuttle.station;

import lombok.*;

/**
 * 데이터베이스 없이 메모리에서 사용할 Station 객체입니다.
 * JPA 관련 어노테이션들을 제거하고, Route와의 관계를 Long 타입 ID로 대체했습니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Station {

    private Long id;

    private String name;

    private Integer orderInRoute;

    private String routeName;

    private Double latitude;

    private Double longitude;

    private String imageUrl;

    private Long routeId; // Route 엔티티와의 연관 관계 대신 Long 타입 ID로 대체
}
