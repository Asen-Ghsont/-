// 在页面加载完成后执行操作
window.addEventListener('DOMContentLoaded', function() {
    // 获取登录按钮和输入字段的引用
    let loginButton = document.getElementById('login-button');
    let registerButton = document.getElementById('register-button');
    let usernameInput = document.querySelector('input[type="text"]');
    let passwordInput = document.querySelector('input[type="password"]');

    // 添加点击事件监听器 - 登录按钮
    loginButton.addEventListener('click', async function() {
        let username = usernameInput.value;
        let password = passwordInput.value;

        // 进行验证
        if (username === '' || password === '') {
            alert('请输入用户名和密码！');
            return;
        }

        try {
            // 创建XMLHttpRequest对象
            let xhr = new XMLHttpRequest();

            // 设置请求方法和URL
            xhr.open('POST', '/login');

            // 设置请求头
            xhr.setRequestHeader('Content-Type', 'application/json');

            // 发送登录请求到后端
            xhr.send(JSON.stringify({ username: username, password: password }));

            // 等待后端响应
            await new Promise(function(resolve, reject) {
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            // 登录成功
                            resolve();
                        } else {
                            // 登录失败
                            reject();
                        }
                    }
                };
            });

            // 登录成功
            window.location.href = "/management/main/index.html";
        } catch (error) {
            // 登录失败
            alert('登录失败，请检查用户名和密码！');
        }
    });

    // 添加点击事件监听器 - 注册按钮
    registerButton.addEventListener('click', async function() {
        let username = usernameInput.value;
        let password = passwordInput.value;

        // 进行验证
        if (username === '' || password === '') {
            alert('请输入用户名和密码！');
            return;
        }

        try {
            // 创建XMLHttpRequest对象
            let xhr = new XMLHttpRequest();

            // 设置请求方法和URL
            xhr.open('POST', '/register');

            // 设置请求头
            xhr.setRequestHeader('Content-Type', 'application/json');

            // 发送注册请求到后端
            xhr.send(JSON.stringify({ username: username, password: password }));

            // 等待后端响应
            await new Promise(function(resolve, reject) {
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 201) {
                            // 注册成功
                            resolve();
                        } else {
                            // 注册失败
                            reject();
                        }
                    }
                };
            });

            // 注册成功
            window.location.href = "/management/main/index.html";
        } catch (error) {
            // 注册失败
            alert('注册失败，请稍后重试！');
        }
    });
});
