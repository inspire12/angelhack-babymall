# BabyCareMall

육아에 대한 조언이 필요한 부모들을 대상으로 RAG(Retrieval-Augmented Generation) 기반의 정확한 정보를 제공하고, 대화 내용과 다이어리 정보를 통한 개인화된 데이터로 사용자를 유지하며 커머스와의 연계를 통해 상승효과를 가져오는 프로젝트입니다.

## 프로젝트 개요

- **목적**: 예비/초보 부모를 위한 육아 가이드와 육아 용품 추천 쇼핑몰
- **핵심 기능**: 
  - RAG 기반 AI 챗봇을 통한 정확한 육아 조언 제공
  - 대화 내용과 다이어리 정보를 통한 개인화된 데이터 수집
  - 아이 맞춤 제품 추천 및 커머스 연계

FE Repository: https://github.com/inspire12/angelhack-babymall-frontend

## 기술 스택

### Backend
- **언어**: Kotlin
- **프레임워크**: Spring Boot 3.5.7
- **Java**: JDK 21

### AI & ML
- **Spring AI**: 1.0.0-M4
- **OpenAI**: GPT-3.5-turbo
- **Vector Store**: PostgreSQL + pgvector (HNSW 인덱스, COSINE_DISTANCE)
- **Embedding**: OpenAI Embedding Model (1536 dimensions)

### 데이터베이스
- **MongoDB**: 세션, 메시지, 아기 정보, 다이어리 데이터 저장
- **PostgreSQL**: 벡터 스토어 (pgvector)

### 기타
- **Thymeleaf**: 템플릿 엔진
- **Spring Actuator**: 모니터링

## 프로젝트 구조

```
src/main/kotlin/com/angelhack/babycaremall/
├── backend/
│   └── module/
│       ├── ai/                    # AI 챗봇 모듈
│       │   ├── controller/        # AI 채팅 컨트롤러
│       │   ├── service/           # AI 서비스 로직
│       │   ├── dto/              # 데이터 전송 객체
│       │   └── tools/            # AI 도구 (카탈로그, 가격 등)
│       ├── baby/                  # 아기 정보 관리
│       │   ├── controller/
│       │   ├── service/
│       │   ├── repository/
│       │   └── dto/
│       ├── babydiary/             # 육아 다이어리
│       │   ├── controller/
│       │   ├── service/
│       │   ├── repository/
│       │   └── dto/
│       └── shopping/              # 커머스 연계
│           ├── controller/
│           ├── domain/
│           ├── infrastructure/
│           └── presentation/
├── chat/                          # 채팅 세션 관리
│   ├── controller/
│   ├── service/
│   ├── model/
│   └── dto/
├── config/                        # 설정
│   ├── AiConfig.kt               # AI 설정 (벡터 스토어, PDF 인제스트)
│   └── PropertyConfig.kt
└── const/                         # 상수 및 열거형
```

## 주요 기능

### 1. AI 챗봇 (RAG 기반)
- **엔드포인트**: `/api/ai/chat`
- PDF 매뉴얼을 벡터 스토어에 인덱싱하여 정확한 육아 정보 제공
- 대화 컨텍스트를 기반으로 한 개인화된 응답
- 세션별 대화 기록 관리

### 2. 제품 추천
- **엔드포인트**: `/api/ai/recommend`
- 카테고리, 예산, 연령대를 기반으로 한 맞춤 제품 추천
- 사용자의 대화 이력과 다이어리 정보를 활용한 개인화

### 3. 아기 정보 관리
- **엔드포인트**: `/api/baby/info`
- 아기 기본 정보 (이름, 성별, 생년월일, 월령 등) 관리
- 프로필 이미지 지원

### 4. 육아 다이어리
- **엔드포인트**: `/api/baby/diaries`
- 육아 일기 작성 및 관리
- 다이어리 정보를 AI 추천에 활용

### 5. 세션 관리
- **엔드포인트**: `/api/sessions`
- 대화 세션 생성 및 조회
- 세션별 메시지 히스토리 관리

## 환경 설정

### 필수 환경 변수

```bash
# OpenAI API Key
OPENAI_API_KEY=your_openai_api_key

# PostgreSQL (벡터 스토어)
POSTGRES_SERVER=localhost
POSTGRES_USER=ai
POSTGRES_PASSWORD=ai1234

# MongoDB (애플리케이션 데이터)
# application.yml에서 설정 또는 환경 변수로 오버라이드
```

### application.yml 설정

```yaml
spring:
  datasource:
    url: jdbc:postgresql://${POSTGRES_SERVER}:5432/aivector
    username: ${POSTGRES_USER:ai}
    password: ${POSTGRES_PASSWORD:ai1234}
  
  data:
    mongodb:
      uri: mongodb://your_mongodb_uri
  
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-3.5-turbo
          temperature: 0.7
    
    vectorstore:
      pgvector:
        initialize-schema: false
        index-type: HNSW
        distance-type: COSINE_DISTANCE
        dimensions: 1536
```

## 빌드 및 실행

### 사전 요구사항
- JDK 21
- Gradle 8.x
- PostgreSQL (pgvector 확장 설치)
- MongoDB

### 빌드

```bash
./gradlew build
```

### 실행

```bash
./gradlew bootRun
```

또는

```bash
java -jar build/libs/babycaremall-0.0.1-SNAPSHOT.jar
```


## RAG 구현 상세

### 벡터 스토어 초기화
- 애플리케이션 시작 시 `prompts/manual.pdf` 파일을 자동으로 인덱싱
- TokenTextSplitter를 사용하여 청크 단위로 분할 (chunkSize: 800, overlap: 120)
- 각 청크에 메타데이터(`source: manual.pdf`) 추가

### 검색 전략
- COSINE_DISTANCE를 사용한 유사도 검색
- **HNSW (Hierarchical Navigable Small World)** 인덱스로 빠른 검색 성능 보장
- 상위 K개 결과를 컨텍스트로 활용

## 라이선스

이 프로젝트는 AngelHack 해커톤 프로젝트입니다.

## 기여자

- AngelHack BabyCareMall Team

---

**Note**: 이 프로젝트는 예비/초보 부모를 위한 육아 조언과 맞춤형 제품 추천을 제공하여 사용자 경험을 향상시키고 커머스와의 연계를 통한 비즈니스 가치를 창출하는 것을 목표로 합니다.
