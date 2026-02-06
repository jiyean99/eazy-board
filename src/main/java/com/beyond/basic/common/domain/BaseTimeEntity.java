package com.beyond.basic.common.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

// Spring Boot 3.x(=Spring Framework 6)는 Jakarta EE로 전환되면서 JPA 같은 표준 API 패키지 네임스페이스가 **javax.* → jakarta.***로 바뀜.
// 반대로 Spring Boot 2.x(=Spring Framework 5)는 Jakarta 전환 이전 세대라서 JPA 관련 API를 javax.persistence 같은 javax.* 기반 패키지로 사용한다.
//예: 엔티티/어노테이션 import가 Boot 2.x에서는 javax.persistence.Entity, Boot 3.x에서는 jakarta.persistence.Entity
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@MappedSuperclass
@Getter
public class BaseTimeEntity {
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;
}
