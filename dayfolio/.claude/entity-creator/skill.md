# 엔티티 생성 스킬

사용자가 JPA 엔티티 생성을 요청하면 아래 규칙에 따라 작성한다.

## 규칙

### 1. 클래스 어노테이션

```java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class {엔티티명} { ... }
```

- `@Entity` 필수
- `@Getter`만 붙임 — setter 없음
- `@NoArgsConstructor(access = AccessLevel.PROTECTED)` — JPA 기본 생성자를 외부에서 호출하지 못하도록 제한

### 2. PK 필드

```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
@SequenceGenerator(name = "member_seq", sequenceName = "MEMBER_SEQ", allocationSize = 1)
@Column(name = "member_seq")
private Long memberSeq;
```

- DB: Oracle → `GenerationType.SEQUENCE` 전략 사용
- 필드명: `{엔티티명소문자}Seq` (예: `memberSeq`)
- `@Column(name = ...)`: 스네이크 케이스로 컬럼명 명시 (예: `member_seq`)
- `sequenceName`: 대문자 스네이크 케이스 (예: `MEMBER_SEQ`)
- `generator` / `name`: 소문자 스네이크 케이스 (예: `member_seq`)
- `allocationSize = 1` 고정

### 3. 일반 필드

```java
private String name;
private String city;
private String street;
private String zipcode;
```

- `private` 선언, 별도 어노테이션 없이 선언
- setter 작성 금지

### 4. 생성자

```java
@Builder
public {엔티티명}({필드타입} {필드명}, ...) {
    this.{필드명} = {필드명};
    ...
}
```

- `@Builder`는 클래스가 아닌 **생성자**에 붙임
- 접근제어자 `public`
- PK 포함 모든 필드를 파라미터로 받고, 파라미터명과 필드명을 **동일하게** 맞춤 (`this.memberSeq = memberSeq`)

### 5. 임포트

```java
import jakarta.persistence.*;
import lombok.*;
```

## 전체 템플릿

```java
package jgj.web.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class {엔티티명} {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "{엔티티명소문자}_seq")
    @SequenceGenerator(name = "{엔티티명소문자}_seq", sequenceName = "{엔티티명대문자}_SEQ", allocationSize = 1)
    @Column(name = "{엔티티명소문자}_seq")
    private Long {엔티티명소문자}Seq;

    private String field1;
    private String field2;

    @Builder
    public {엔티티명}(Long {엔티티명소문자}Seq, String field1, String field2) {
        this.{엔티티명소문자}Seq = {엔티티명소문자}Seq;
        this.field1 = field1;
        this.field2 = field2;
    }
}
```

## 체크리스트

- [ ] `@Entity`, `@Getter`, `@NoArgsConstructor(access = AccessLevel.PROTECTED)` 모두 있는가
- [ ] PK 필드명이 `{엔티티명소문자}Seq` 형태인가
- [ ] `GenerationType.SEQUENCE` + `@SequenceGenerator(allocationSize = 1)` 조합인가
- [ ] setter가 없는가
- [ ] `@Builder`가 생성자에 붙어 있는가
- [ ] 생성자 파라미터명과 필드명이 일치하는가 (`this.memberSeq = memberSeq`)
