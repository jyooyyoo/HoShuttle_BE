package com.hoshuttle.timetable.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 셔틀 시간표의 전체 구조를 나타내는 DTO 클래스입니다.
 * shuttleScheduleDB.json 파일의 최상위 객체에 해당합니다.
 * 각 노선별 셔틀 정류장 목록을 포함합니다.
 */
@Getter // 모든 필드에 대한 getter 메서드를 자동으로 생성합니다.
@Setter // 모든 필드에 대한 setter 메서드를 자동으로 생성합니다.
@NoArgsConstructor // 기본 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동으로 생성합니다.
public class ShuttleSchedule {
    // "cheonan_to_asan" 노선의 셔틀 정류장 목록
    private List<ShuttleStop> cheonan_to_asan;
    // 만약 다른 노선이 있다면 여기에 추가합니다. (예: "asan_to_cheonan")
    // private List<ShuttleStop> asan_to_cheonan;

    // 참고: Lombok 어노테이션을 사용하면 코드를 간결하게 유지할 수 있습니다.
    // Lombok을 사용하지 않는다면, 직접 getter/setter 및 생성자를 작성해야 합니다.
}
