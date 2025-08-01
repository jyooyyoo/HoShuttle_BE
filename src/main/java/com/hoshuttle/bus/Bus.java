package com.hoshuttle.bus;

import jakarta.persistence.*;
import lombok.*;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Bus {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String licensePlate; // 차량 번호 (예: 123가4567)

    private String model;        // 차량 모델 (예: 현대 유니버스) - 선택 사항
    private Integer capacity;    // 좌석 수 - 선택 사항
    private Boolean isActive;    // 현재 운행 중인지 여부 (활성화/비활성화)

    // 필요하다면 추가 필드:
    // private String busType;   // (예: 대형, 중형)
    // private String driverName; // 운전자 이름
    // private String contact;   // 운전자 연락처
}