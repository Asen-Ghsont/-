window.addEventListener('DOMContentLoaded', function () {
    let sidebar = document.getElementById('sidebar');
    let lis = sidebar.getElementsByTagName('li');

    for (let i = 0; i < lis.length; i++) {
        lis[i].addEventListener('click', function () {
            let current = sidebar.querySelector('.selected');
            if (current) {
                current.classList.remove('selected');
            }
            this.classList.add('selected');
        });
    }
});

function getCookie(name) {
    let nameEQ = name + "=";
    let cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i];
        while (cookie.charAt(0) === ' ') {
            cookie = cookie.substring(1, cookie.length);
        }
        if (cookie.indexOf(nameEQ) === 0) {
            return cookie.substring(nameEQ.length, cookie.length);
        }
    }
    return null;
}

let username = getCookie("username");
let userInfo = document.getElementById('user-info');
userInfo.textContent = '用户 | ' + username;