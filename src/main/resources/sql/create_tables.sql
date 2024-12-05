-- 1. Users 테이블 생성
CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,               -- 유저 ID
    nickname VARCHAR(255) UNIQUE NOT NULL,     -- 유저 닉네임
    password VARCHAR(255) NOT NULL,            -- 비밀번호
    email VARCHAR(255) UNIQUE NOT NULL,        -- 이메일
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 일자
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 업데이트 일자
);

-- 2. TodoLists 테이블 생성 (유저당 여러 개의 리스트)
CREATE TABLE TodoLists (
    list_id SERIAL PRIMARY KEY,                -- 투두 리스트 ID
    user_id INT REFERENCES Users(user_id) ON DELETE CASCADE,  -- 유저 ID (Users 테이블과 외래키 연결)
    list_title VARCHAR(255) NOT NULL,               -- 투두 리스트 제목
    list_description TEXT,                          -- 투두 리스트 설명
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 생성 일자
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP   -- 업데이트 일자
);


-- 3. TodoItems 테이블 생성 (투두 항목)
CREATE TABLE TodoItems (
    item_id SERIAL PRIMARY KEY,               -- todo 항목 ID
    list_id INT REFERENCES TodoLists(list_id) ON DELETE CASCADE, -- 투두 리스트 ID
    item_title VARCHAR(255) NOT NULL,               -- todo 항목 제목
    item_description TEXT,                          -- todo 항목 설명
    status_code INT NOT NULL,                  -- 상태 코드 (0: 미완료, 1: 진행중, 2: 완료)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 생성 일자
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 업데이트 일자
    completed_at TIMESTAMP NULL                -- 완료 일자 (NULL 허용)
);