# 전체 개요  
## https://github.com/code-4-devdoc/DevDoc  
## https://github.com/code-4-devdoc/DevDoc-FE  
## https://github.com/code-4-devdoc/DevDoc-BE  
<br/>

---

# 메모
## H2-Console
#### http://localhost:8080/h2-console  
- JDBC URL : jdbc:h2:mem:testdb  
- UserName : root  
- Password : root
<br/>

---

# API 설계 v2
### `POST /api/register` 회원가입
: test 에서는 User ID = 1 필요
### `GET /api/resumes` Resume 목록 조회
: 로그인 User 검증  
: resumeId, title, createdAt
### `GET /api/resumes/test` Resume 목록 조회
: User ID = 1  
: resumeId, title, createdAt
<br/>
<br/>
<br/>
### `GET /api/resumes/{resumeId}` Resume 조회
: 로그인 User 검증  
: status = T 인 모든 테이블
### `POST /api/resumes` Resume 생성
: 로그인 User 검증  
### `DEL /api/resumes/{resumeId}` Resume 삭제
: 로그인 User 검증
### `PATCH /api/resumes/{resumeId}/title` Resume 업데이트
: 로그인 User 검증  
: title
### `PATCH /api/resumes/{resumeId}/data` Resume 업데이트
: 로그인 User 검증  
: title 포함 테이블 전체
<br/>
<br/>
<br/>
### `GET /api/resumes/test/{resumeId}` Resume 조회
: User ID = 1  
: status = F 포함 모든 테이블
### `POST /api/resumes/test` Resume 생성
: User ID = 1
### `DEL /api/resumes/test/{resumeId}` Resume 삭제
: User ID = 1
### `PATCH /api/resumes/test/{resumeId}/title` Resume 업데이트
: User ID = 1  
: title
### `PATCH /api/resumes/test/{resumeId}/data` Resume 업데이트
: User ID = 1  
: title 포함 테이블 전체
<br/>
<br/>
<br/>
### `GET /api/resumes/skills/{resumeId}` Skill ID 조회
: 컨테이너 ID 부여용
### `GET /api/resumes/skills/{resumeId}/data` Skill 조회
: status = T 인 모든 테이블  
: 만약 없다면 Skill 테이블 3개 생성
### `GET /api/resumes/skills/test/{resumeId}/data` Skill 조회
: status = F 포함 모든 테이블  
: 만약 없다면 Skill 테이블 3개 생성
### `DEL /api/resumes/skills/{resumeId}` Skill 삭제
