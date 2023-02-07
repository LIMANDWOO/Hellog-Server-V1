## 교내 기술 블로그, `Hellog` 서버 입니다.

Hellog에 대한 자세한 기획 및 설계는 [해당 문서](https://hellog.notion.site/Hellog-0a8a29fceb4d422ba793060c9e1062b5)에 기술되어 있습니다.

### Commit 규칙

#### 기본 형식

```
(gitmoji) :: 구체적인 커밋 메시지
```

#### gitmoji 타입

| icon |     code    |           description            |
|:---:|:--------:|:--------------------------------:|
| ✨   | `:sparkles:` |        새 기능 (파일 추가)            |
| 📝   |  `:memo:`    |     코드 수정 (요구사항 수정)    |
| 🎨   |   `:art:`    |     코드 구조 개선        |
| ⚡️    |    `:zap:`    |   코드 성능 개선        |
| 🔥   |    `:fire:`  |   코드 삭제 (파일 삭제)   |
| 📄 |    `:page_facing_up:`  |   문서 작성 및 변경    |
| 🔧 |  `:wrench:`      | Configuration 파일/의존성 추가 및 삭제 |
| 👷 |   `:construction_worker:`    |   CI/CD 시스템 추가/수정     |
| 🐛 |     `:bug:`    |         버그 수정               |
| ✅ |  `:white_check_mark:`   |      테스트 케이스 작성 및 수정    |
| ⏪ | `:rewind:` |            작업 되돌리기              |
| 🚑 |   `:ambulance:`    |          긴급 수정             |
| 🙈 | `:see_no_evil:`  | .gitignore 추가/수정 |

### 환경변수 설정

#### application.yml
```yaml
server:
  port: 8080

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: ${db-url}
    username: ${db-username}
    password: ${db-password}
  jpa:
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    open-in-view: true
    show-sql: false
    hibernate:
      format-sql: true
      ddl-auto: update
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
  jackson:
    property-naming-strategy: SNAKE_CASE
  servlet:
    multipart:
      max-file-size: 1000MB
      max-request-size: 1000MB
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${google-client-id}
            client-secret: ${google-client-secret}
            scope: profile, email

app:
  jwt:
    issuer: ${issuer}
    secret:
      access: ${access-secret}
      refresh: ${refresh-secret}
  end-point:
    dodam:
      auth: http://dauth.b1nd.com/api/token
      open: http://open.dodam.b1nd.com/api/user
    google:
      info: https://www.googleapis.com/oauth2/v1/userinfo

logging:
  level:
    org.springframework.boot.autoconfigure: ERROR
    com.amazonaws.util.EC2MetadataUtils: error

cloud:
  aws:
    credentials:
      access-key: ${access-key}
      secret-key: ${secret-key}
    stack:
      auto: false
    region:
      static: ap-northeast-2
    s3:
      bucket: ${bucket}
```