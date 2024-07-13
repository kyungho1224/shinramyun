// 비밀번호 보였다 안보였다
document.getElementById('show-password').addEventListener('change', function () {
    const passwordField = document.getElementById('password');
    if (this.checked) {
        passwordField.type = 'text';
    } else {
        passwordField.type = 'password';
    }
});

// 이메일 중복 체크 API
// 입력한 이메일이 데이터베이스에 등록된 상태라면 true, 등록되지 않아서 사용이 가능하다면 false 반환
// 결과가 헷갈릴 수 있지만 메서드명이 isDuplicatedEmail()이라서 중복 상태일 때 true
document.getElementById('check-email').addEventListener('click', async () => {
    const email = document.getElementById('email').value;

    try {
        // 입력한 이메일을 쿼리파라미터로 서버에 전달
        const response = await fetch(`http://13.124.139.131:8080/public/api/check-email?email=${email}`);
        const isDuplicated = await response.json();

        const resultElement = document.getElementById('email-check-result');
        if (isDuplicated) { // 요청 후 반환 결과 값 true
            resultElement.textContent = '이 이메일은 이미 사용 중입니다.';
            resultElement.style.color = 'red';
        } else {    // 요청 후 반환 결과 값 false
            resultElement.textContent = '이 이메일은 사용 가능합니다.';
            resultElement.style.color = 'green';
        }
    } catch (error) {
        console.error('Error:', error);
    }
});

// 회원 가입 API
document.getElementById('signup-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const birth = document.getElementById('birth').value;

    // API 요청하기 위한 데이터 직렬화
    const customerRegisterRequest = {
        username,
        email,
        password,
        birth
    };

    try {
        // 회원 가입 API
        const response = await fetch('http://13.124.139.131:8080/public/api/sign-up', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(customerRegisterRequest)
        });

        // 서버에서 내려주는 HttpStatus 201(HttpStatus.CREATED)
        if (response.status === 201) {
            alert('회원가입이 완료되었습니다.');
        } else {
            const errorData = await response.json();
            alert(`회원가입 실패: ${errorData.message}`);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('회원가입 중 오류가 발생했습니다.');
    }
});
