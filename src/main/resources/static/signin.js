// 비밀번호 보였다 안보였다
document.getElementById('show-password').addEventListener('change', function () {
    const passwordField = document.getElementById('password');

    if (this.checked) { // 체크 상태에 따라 필드의 타입을 변경한다
        passwordField.type = 'text';
    } else {
        passwordField.type = 'password';
    }
});

// 로그인 폼 처리하기
document.getElementById('signin-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    // API 요청하기 위한 데이터 직렬화
    const customerSignInRequest = {
        email,
        password
    };

    try {
        // 로그인 API
        const response = await fetch('http://13.124.139.131:8080/public/api/sign-in', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(customerSignInRequest)
        });

        // 서버에서 내려주는 HttpStatus 200(HttpStatus.OK)
        if (response.status === 200) {
            const data = await response.json();
            localStorage.setItem('authToken', data.token);  // 토큰을 로컬 스토리지에 저장
            alert('로그인 성공');

            // 사용자 정보를 가져와서 표시
            await fetchUserInfo();
        } else {
            alert('로그인 실패: ' + (await response.json()).message);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('로그인 중 오류가 발생했습니다.');
    }
});

// 로그인 사용자 정보 가져오기
async function fetchUserInfo() {
    // 로컬 저장소에 저장된 토큰 가져오기
    const token = localStorage.getItem('authToken');
    if (!token) {
        // 토큰이 없으면 로그인을 해야한다. 로그인을 하면 발급받은 토큰을 로컬에 저장한다.
        alert('로그인이 필요합니다.');
        return;
    }

    try {
        // 사용자 정보 요청 API
        const response = await fetch('http://13.124.139.131:8080/api/customers/me', {
            method: 'GET',  // HttpMethod.GET -> GET, POST, PUT, PATCH, DELETE 등... GET = 요청/조회
            headers: {
                'Authorization': `Bearer ${token}`  // 헤더에 토큰을 담아서 API를 요청한다. 서버에서 토큰을 디코딩하여 사용자 인증/인가 처리
            }
        });

        if (response.status === 200) {
            // API 요청 결과가 200(OK) = response를 userData 객체에 넣고 파싱한다.
            // response를 userData로 역직렬화
            const userData = await response.json();
            document.getElementById('user-id').textContent = `아이디: ${userData.id}`;
            document.getElementById('user-username').textContent = `이름: ${userData.username}`;
            document.getElementById('user-email').textContent = `이메일: ${userData.email}`;
            document.getElementById('user-birth').textContent = `생년월일: ${userData.birth}`;
            document.getElementById('user-created_at').textContent = `가입일: ${userData.created_at}`;
            document.getElementById('user-info').style.display = 'block';
        } else {
            // response.status가 200이 아니라면 요청 실패임
            alert('사용자 정보를 가져오지 못했습니다: ' + response.statusText);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('사용자 정보를 가져오는 중 오류가 발생했습니다.');
    }
}

//
// 실행 시점 : HTML 문서의 구조가 로드되었을 때 실행, DOM에 접근하고 조작 가능 (스크립트, 스타일시트, 이미지 등 다른 자원과는 상관없음)
document.addEventListener('DOMContentLoaded', async () => {
    const token = localStorage.getItem('authToken');    // 로그인 했을 때 발급받은 JWT를 가져온다
    if (token) {
        await fetchUserInfo();  // 페이지 로드 시 토큰이 있으면 사용자 정보 표시
    }
});
