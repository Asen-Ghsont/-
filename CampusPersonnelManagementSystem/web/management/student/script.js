// 学生信息数组
let students = [];

// 获取添加学生按钮和弹窗
document.getElementById("add-student-button");
let addStudentModal = document.getElementById("add-student-modal");

// 显示添加学生模态框
function showAddStudentModal() {
    addStudentModal.style.display = "block";
}

// 关闭模态框
function closeModal(modalId) {
    let modal = document.getElementById(modalId);
    modal.style.display = "none";
}

// 提交表单
function addStudent(event) {
    event.preventDefault(); // 阻止表单提交

    // 获取表单数据
    let studentIdInput = document.querySelector("#add-student-modal form input[name='studentId']");
    let nameInput = document.querySelector("#add-student-modal form input[name='name']");
    let genderInput = document.querySelector("#add-student-modal form input[name='gender']");
    let ageInput = document.querySelector("#add-student-modal form input[name='age']");
    let departmentInput = document.querySelector("#add-student-modal form input[name='department']");

    let studentId = studentIdInput.value.trim();
    let name = nameInput.value.trim();
    let gender = genderInput.value.trim();
    let age = parseInt(ageInput.value.trim(), 10);
    let department = departmentInput.value.trim();

    // 执行添加学生的逻辑
    addStudentAjax(studentId, name, gender, age, department);

    // 清空表单数据
    studentIdInput.value = "";
    nameInput.value = "";
    genderInput.value = "";
    ageInput.value = "";
    departmentInput.value = "";

    // 关闭弹窗
    closeModal("add-student-modal");
}

// 添加学生（Ajax）
function addStudentAjax(studentId, name, gender, age, department) {
    // 创建XMLHttpRequest对象
    let xhr = new XMLHttpRequest();

    // 监听请求状态变化
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // 请求成功，处理返回的数据
                let response = JSON.parse(xhr.responseText);
                if (response.success) {
                    // 添加成功，更新页面
                    let student = {
                        studentId: studentId,
                        name: name,
                        gender: gender,
                        age: age,
                        department: department
                    };
                    students.push(student);
                    console.log("成功");
                    renderTable();
                } else {
                    // 添加失败，显示错误信息
                    alert("添加学生失败: " + response.message);
                }
            } else {
                // 请求失败，显示错误信息
                alert("请求失败: " + xhr.status);
            }
        }
    };

    // 设置请求方法和URL
    xhr.open("POST", "/add-student", true);

    // 设置请求头
    xhr.setRequestHeader("Content-Type", "application/json");

    // 发送请求
    xhr.send(JSON.stringify({
        studentId: studentId,
        name: name,
        gender: gender,
        age: age,
        department: department
    }));
}

// 删除学生确认弹窗
function showDeleteConfirmation(studentId) {
    let deleteConfirmationModal = document.getElementById("delete-confirmation-modal");
    let deleteButton = document.querySelector("#delete-confirmation-modal button:first-child");
    deleteButton.setAttribute("onclick", "deleteStudent('" + studentId + "')");
    deleteConfirmationModal.style.display = "block";
}

// 删除学生（Ajax）
function deleteStudent(studentId) {
    // 创建XMLHttpRequest对象
    let xhr = new XMLHttpRequest();

    // 监听请求状态变化
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // 请求成功，处理返回的数据
                let response = JSON.parse(xhr.responseText);
                if (response.success) {
                    // 删除成功，更新页面
                    let index = students.findIndex(function (student) {
                        return student.studentId === studentId;
                    });
                    if (index !== -1) {
                        students.splice(index, 1);
                        renderTable();
                    }
                } else {
                    // 删除失败，显示错误信息
                    alert("删除学生失败: " + response.message);
                }
            } else {
                // 请求失败，显示错误信息
                alert("请求失败: " + xhr.status);
            }
        }
    };

    // 设置请求方法和URL
    xhr.open("POST", "/delete-student", true);

    // 设置请求头
    xhr.setRequestHeader("Content-Type", "application/json");

    // 发送请求
    xhr.send(JSON.stringify({
        studentId: studentId
    }));

    closeModal("delete-confirmation-modal");
}

// 显示编辑学生模态框
function showEditModal(studentId) {
    let editModal = document.getElementById("edit-student-modal");
    let editForm = document.querySelector("#edit-student-modal form");

    let student = students.find(function (student) {
        return student.studentId === studentId;
    });

    editForm.elements.editStudentId.value = student.studentId;
    editForm.elements.editName.value = student.name;
    editForm.elements.editGender.value = student.gender;
    editForm.elements.editAge.value = student.age;
    editForm.elements.editDepartment.value = student.department;

    editModal.style.display = "block";
}

// 编辑学生（Ajax）
function editStudent(event) {
    event.preventDefault(); // 阻止表单提交

    let editForm = document.querySelector("#edit-student-modal form");

    let studentId = editForm.elements.editStudentId.value.trim();
    let name = editForm.elements.editName.value.trim();
    let gender = editForm.elements.editGender.value.trim();
    let age = parseInt(editForm.elements.editAge.value.trim(), 10);
    let department = editForm.elements.editDepartment.value.trim();

    // 创建XMLHttpRequest对象
    let xhr = new XMLHttpRequest();

    // 监听请求状态变化
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // 请求成功，处理返回的数据
                let response = JSON.parse(xhr.responseText);
                if (response.success) {
                    // 编辑成功，更新页面
                    let index = students.findIndex(function (student) {
                        return student.studentId === studentId;
                    });
                    if (index !== -1) {
                        students[index] = {
                            studentId: studentId,
                            name: name,
                            gender: gender,
                            age: age,
                            department: department
                        };
                        renderTable();
                        closeModal("edit-student-modal");
                    }
                } else {
                    // 编辑失败，显示错误信息
                    alert("编辑学生失败: " + response.message);
                }
            } else {
                // 请求失败，显示错误信息
                alert("请求失败: " + xhr.status);
            }
        }
    };

    // 设置请求方法和URL
    xhr.open("POST", "/edit-student", true);

    // 设置请求头
    xhr.setRequestHeader("Content-Type", "application/json");

    // 发送请求
    xhr.send(JSON.stringify({
        studentId: studentId,
        name: name,
        gender: gender,
        age: age,
        department: department
    }));
}

// 渲染学生表格
function renderTable() {
    students.sort(function(a, b) {
        return a.studentId - b.studentId;
    });
    let tableBody = document.getElementById("student-table-body");
    tableBody.innerHTML = ""; // 清空表格内容

    let startIndex = (currentPage - 1) * pageSize;
    let endIndex = startIndex + pageSize;
    let currentPageStudents = students.slice(startIndex, endIndex);

    Array.from(currentPageStudents).forEach(function (student) {
        let row = document.createElement("tr");
        row.innerHTML = `
            <td>${student.studentId}</td>
            <td>${student.name}</td>
            <td>${student.gender}</td>
            <td>${student.age}</td>
            <td>${student.department}</td>
            <td>
                <button class="delete-button" onclick="showDeleteConfirmation('${student.studentId}')">删除</button>
                <button class="edit-button" onclick="showEditModal('${student.studentId}')">修改</button>
            </td>
        `;
        tableBody.appendChild(row);
    });

    let totalPages = Math.ceil(students.length / pageSize);
    let currentPageSpan = document.getElementById("current-page");
    let totalPagesSpan = document.getElementById("total-pages");

    currentPageSpan.textContent = currentPage;
    totalPagesSpan.textContent = totalPages;

    updatePaginationButtons();
}

// 初始化函数
function init() {
    // 获取学生数据并渲染表格
    fetchStudents();
}

// 获取学生数据（Ajax）
function fetchStudents() {
    // 创建XMLHttpRequest对象
    let xhr = new XMLHttpRequest();

    // 监听请求状态变化
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // 请求成功，处理返回的数据
                students = JSON.parse(xhr.responseText);
                renderTable();
            } else {
                // 请求失败，显示错误信息
                alert("请求失败: " + xhr.status);
            }
        }
    };

    // 设置请求方法和URL
    xhr.open("GET", "/students", true);

    // 发送请求
    xhr.send();
}

document.addEventListener("DOMContentLoaded", function() {
    init();
});
