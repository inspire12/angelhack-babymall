CREATE EXTENSION IF NOT EXISTS vector;

-- aivector DB에 접속해 실행
CREATE TABLE IF NOT EXISTS public.vector_store (
                                                   id        text PRIMARY KEY,
                                                   content   text,
                                                   metadata  jsonb,
                                                   embedding vector(1536)  -- 사용 모델 차원에 맞추기!
    );