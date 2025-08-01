package com.hoshuttle.route;

import com.hoshuttle.station.Station; // Station 클래스 import
import lombok.*;

import java.util.List;

/**
 * 데이터베이스 없이 메모리에서 사용할 Route 객체입니다.
 * JPA 관련 어노테이션들을 제거했습니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {

    private Long id;

    private String name; // 노선 이름

    private List<Station> stations; // 연관된 Station 목록
}
