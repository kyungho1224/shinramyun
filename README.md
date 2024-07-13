## 신라면

---

### 🖥️ HOST : 13.124.139.131

### 📋 [API 문서(SwaggerUI)](http://13.124.139.131:8080/swagger-ui/index.html)



### 📌 [회원가입 DEMO](http://13.124.139.131:8080/signup.html)
```
{
    "username":"김밀크",
    "email":"milk@kakao.com",
    "password":"1234567890",
    "birth":"20060520"
}
```

### 📌 [로그인 DEMO](http://13.124.139.131:8080/signin.html)
1. 로그인 API 요청
    1. /public/api/sign-in
    2. [Try it out] 클릭
    3. 로그인 정보 입력(아래 코드 스니펫 참조)
   4. [Execute] 클릭
2. 정상 로그인 처리 후 JWT 발급

```
{
    "username":"김밀크",
    "password":"1234567890"
}
```

### 📌 회원정보 가져오기
1. 발급된 JWT 문자열 복사
2. 페이지 상단 우측 [Authorize] 버튼 클릭
3. 복사한 문자열 [Value]에 넣은 후 [Authorize] 클릭 후 [Close]
4. 인증된 상태로 요청 가능한 회원정보 API 요청
```
데모 페이지에서는 로그인 처리가 정상적으로 완료되면 바로 가져오는 상태
```