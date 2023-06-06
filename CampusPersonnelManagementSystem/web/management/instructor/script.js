// 班导师信息数组
let instructors = [];

// 获取添加班导师按钮和弹窗
document.getElementById("add-instructor-button");
let addInstructorModal = document.getElementById("add-instructor-modal");

// 显示添加班导师模态框
function showAddInstructorModal() {
    addInstructorModal.style.display = "block";
}

// 关闭模态框
function closeModal(modalId) {
    let modal = document.getElementById(modalId);
    modal.style.display = "none";
}

// 提交表单
function addInstructor(event) {
    event.preventDefault(); // 阻止表单提交

    // 获取表单数据
    let instructorIdInput = document.querySelector("#add-instructor-modal form input[name='instructorId']");
    let nameInput = document.querySelector("#add-instructor-modal form input[name='name']");
    let genderInput = document.querySelector("#add-instructor-modal form input[name='gender']");
    let ageInput = document.querySelector("#add-instructor-modal form input[name='age']");

    let instructorId = instructorIdInput.value.trim();
    let name = nameInput.value.trim();
    let gender = genderInput.value.trim();
    let age = parseInt(ageInput.value.trim(), 10);

    // 执行添加班导师的逻辑
    addInstructorAjax(instructorId, name, gender, age);

    // 清空表单数据
    instructorIdInput.value = "";
    nameInput.value = "";
    genderInput.value = "";
    ageInput.value = "";

    // 关闭弹窗
    closeModal("add-instructor-modal");
}

// 添加班导师（Ajax）
function addInstructorAjax(instructorId, name, gender, age) {
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
                    let instructor = {
                        instructorId: instructorId,
                        name: name,
                        gender: gender,
                        age: age,
                    };
                    instructors.push(instructor);
                    console.log("成功");
                    renderTable();
                } else {
                    // 添加失败，显示错误信息
                    alert("添加班导师失败: " + response.message);
                }
            } else {
                // 请求失败，显示错误信息
                alert("请求失败: " + xhr.status);
            }
        }
    };

    // 设置请求方法和URL
    xhr.open("POST", "/add-instructor", true);

    // 设置请求头
    xhr.setRequestHeader("Content-Type", "application/json");

    // 发送请求
    xhr.send(JSON.stringify({
        instructorId: instructorId,
        name: name,
        gender: gender,
        age: age,
    }));
}

// 删除班导师确认弹窗
function showDeleteConfirmation(instructorId) {
    let deleteConfirmationModal = document.getElementById("delete-confirmation-modal");
    let deleteButton = document.querySelector("#delete-confirmation-modal button:first-child");
    deleteButton.setAttribute("onclick", "deleteInstructor('" + instructorId + "')");
    deleteConfirmationModal.style.display = "block";
}

// 删除班导师（Ajax）
function deleteInstructor(instructorId) {
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
                    let index = instructors.findIndex(function (instructor) {
                        return instructor.instructorId === instructorId;
                    });
                    if (index !== -1) {
                        instructors.splice(index, 1);
                        renderTable();
                    }
                } else {
                    // 删除失败，显示错误信息
                    alert("删除班导师失败: " + response.message);
                }
            } else {
                // 请求失败，显示错误信息
                alert("请求失败: " + xhr.status);
            }
        }
    };

    // 设置请求方法和URL
    xhr.open("POST", "/delete-instructor", true);

    // 设置请求头
    xhr.setRequestHeader("Content-Type", "application/json");

    // 发送请求
    xhr.send(JSON.stringify({
        instructorId: instructorId
    }));

    closeModal("delete-confirmation-modal");
}

// 显示编辑班导师模态框
function showEditModal(instructorId) {
    let editModal = document.getElementById("edit-instructor-modal");
    let editForm = document.querySelector("#edit-instructor-modal form");

    let instructor = instructors.find(function (instructor) {
        return instructor.instructorId === instructorId;
    });

    editForm.elements.editInstructorId.value = instructor.instructorId;
    editForm.elements.editName.value = instructor.name;
    editForm.elements.editGender.value = instructor.gender;
    editForm.elements.editAge.value = instructor.age;

    editModal.style.display = "block";
}

// 编辑班导师（Ajax）
function editInstructor(event) {
    event.preventDefault(); // 阻止表单提交

    let editForm = document.querySelector("#edit-instructor-modal form");

    let instructorId = editForm.elements.editInstructorId.value.trim();
    let name = editForm.elements.editName.value.trim();
    let gender = editForm.elements.editGender.value.trim();
    let age = parseInt(editForm.elements.editAge.value.trim(), 10);

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
                    let index = instructors.findIndex(function (instructor) {
                        return instructor.instructorId === instructorId;
                    });
                    if (index !== -1) {
                        instructors[index] = {
                            instructorId: instructorId,
                            name: name,
                            gender: gender,
                            age: age,
                        };
                        renderTable();
                        closeModal("edit-instructor-modal");
                    }
                } else {
                    // 编辑失败，显示错误信息
                    alert("编辑班导师失败: " + response.message);
                }
            } else {
                // 请求失败，显示错误信息
                alert("请求失败: " + xhr.status);
            }
        }
    };

    // 设置请求方法和URL
    xhr.open("POST", "/edit-instructor", true);

    // 设置请求头
    xhr.setRequestHeader("Content-Type", "application/json");

    // 发送请求
    xhr.send(JSON.stringify({
        instructorId: instructorId,
        name: name,
        gender: gender,
        age: age,
    }));
}

// 渲染班导师表格
function renderTable() {
    instructors.sort(function(a, b) {
        return a.instructorId - b.instructorId;
    });
    let tableBody = document.getElementById("instructor-table-body");
    tableBody.innerHTML = ""; // 清空表格内容

    let startIndex = (currentPage - 1) * pageSize;
    let endIndex = startIndex + pageSize;
    let currentPageInstructors = instructors.slice(startIndex, endIndex);

    Array.from(currentPageInstructors).forEach(function (instructor) {
        let row = document.createElement("tr");
        row.innerHTML = `
            <td>${instructor.instructorId}</td>
            <td>${instructor.name}</td>
            <td>${instructor.gender}</td>
            <td>${instructor.age}</td>
            <td>
                <button class="delete-button" onclick="showDeleteConfirmation('${instructor.instructorId}')">删除</button>
                <button class="edit-button" onclick="showEditModal('${instructor.instructorId}')">修改</button>
            </td>
        `;
        tableBody.appendChild(row);
    });

    let totalPages = Math.ceil(instructors.length / pageSize);
    let currentPageSpan = document.getElementById("current-page");
    let totalPagesSpan = document.getElementById("total-pages");

    currentPageSpan.textContent = currentPage;
    totalPagesSpan.textContent = totalPages;

    updatePaginationButtons();
}

// 初始化函数
function init() {
    // 获取班导师数据并渲染表格
    fetchInstructors();
}

// 获取班导师数据（Ajax）
function fetchInstructors() {
    // 创建XMLHttpRequest对象
    let xhr = new XMLHttpRequest();

    // 监听请求状态变化
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // 请求成功，处理返回的数据
                instructors = JSON.parse(xhr.responseText);
                renderTable();
            } else {
                // 请求失败，显示错误信息
                alert("请求失败: " + xhr.status);
            }
        }
    };

    // 设置请求方法和URL
    xhr.open("GET", "/instructors", true);

    // 发送请求
    xhr.send();
}

document.addEventListener("DOMContentLoaded", function() {
    init();
});
