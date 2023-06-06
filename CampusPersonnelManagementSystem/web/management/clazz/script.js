// 班级信息数组
let clazzes = [];

// 获取添加班级按钮和弹窗
document.getElementById("add-class-button");
let addClazzModal = document.getElementById("add-class-modal");

// 显示添加班级模态框
function showAddClazzModal() {
    addClazzModal.style.display = "block";
}

// 关闭模态框
function closeModal(modalId) {
    let modal = document.getElementById(modalId);
    modal.style.display = "none";
}

// 提交表单
function addClazz(event) {
    event.preventDefault(); // 阻止表单提交

    // 获取表单数据
    let clazzIdInput = document.querySelector("#add-class-modal form input[name='classId']");
    let nameInput = document.querySelector("#add-class-modal form input[name='name']");
    let departmentInput = document.querySelector("#add-class-modal form input[name='department']");

    let clazzId = clazzIdInput.value.trim();
    let name = nameInput.value.trim();
    let department = departmentInput.value.trim();

    // 执行添加班级的逻辑
    addClazzAjax(clazzId, name, department);

    // 清空表单数据
    clazzIdInput.value = "";
    nameInput.value = "";
    departmentInput.value = "";

    // 关闭弹窗
    closeModal("add-class-modal");
}

// 添加班级（Ajax）
function addClazzAjax(clazzId, name, department) {
    // 创建XMLHttpRequest对象
    let xhr = new XMLHttpRequest();

    // 监听请求状态变化
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // 请求成功，处理返回的数据
                let response = JSON.parse(xhr.responseText);
                if (response.success) {
                    // 添加成功，更新页面
                    let clazz = {
                        clazzId: clazzId,
                        name: name,
                        department: department
                    };
                    clazzes.push(clazz);
                    console.log("成功");
                    renderTable();
                } else {
                    // 添加失败，显示错误信息
                    alert("添加班级失败: " + response.message);
                }
            } else {
                // 请求失败，显示错误信息
                alert("请求失败: " + xhr.status);
            }
        }
    };

    // 设置请求方法和URL
    xhr.open("POST", "/add-class", true);

    // 设置请求头
    xhr.setRequestHeader("Content-Type", "application/json");

    // 发送请求
    xhr.send(JSON.stringify({
        clazzId: clazzId,
        name: name,
        department: department
    }));
}

// 删除班级确认弹窗
function showDeleteConfirmation(clazzId) {
    let deleteConfirmationModal = document.getElementById("delete-confirmation-modal");
    let deleteButton = document.querySelector("#delete-confirmation-modal button:first-child");
    deleteButton.setAttribute("onclick", "deleteClazz('" + clazzId + "')");
    deleteConfirmationModal.style.display = "block";
}

// 删除班级（Ajax）
function deleteClazz(clazzId) {
    // 创建XMLHttpRequest对象
    let xhr = new XMLHttpRequest();

    // 监听请求状态变化
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // 请求成功，处理返回的数据
                let response = JSON.parse(xhr.responseText);
                if (response.success) {
                    // 删除成功，更新页面
                    let index = clazzes.findIndex(function (clazz) {
                        return clazz.clazzId === clazzId;
                    });
                    if (index !== -1) {
                        clazzes.splice(index, 1);
                        renderTable();
                    }
                } else {
                    // 删除失败，显示错误信息
                    alert("删除班级失败: " + response.message);
                }
            } else {
                // 请求失败，显示错误信息
                alert("请求失败: " + xhr.status);
            }
        }
    };

    // 设置请求方法和URL
    xhr.open("POST", "/delete-class", true);

    // 设置请求头
    xhr.setRequestHeader("Content-Type", "application/json");

    // 发送请求
    xhr.send(JSON.stringify({
        clazzId: clazzId
    }));

    closeModal("delete-confirmation-modal");
}

// 显示编辑班级模态框
function showEditModal(clazzId) {
    let editModal = document.getElementById("edit-class-modal");
    let editForm = document.querySelector("#edit-class-modal form");

    let clazz = clazzes.find(function (clazz) {
        return clazz.clazzId === clazzId;
    });

    editForm.elements.editClassId.value = clazz.clazzId;
    editForm.elements.editName.value = clazz.name;
    editForm.elements.editDepartment.value = clazz.department;

    editModal.style.display = "block";
}

// 编辑班级（Ajax）
function editClazz(event) {
    event.preventDefault(); // 阻止表单提交

    let editForm = document.querySelector("#edit-class-modal form");

    let clazzId = editForm.elements.editClassId.value.trim();
    let name = editForm.elements.editName.value.trim();
    let department = editForm.elements.editDepartment.value.trim();

    // 创建XMLHttpRequest对象
    let xhr = new XMLHttpRequest();

    // 监听请求状态变化
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // 请求成功，处理返回的数据
                let response = JSON.parse(xhr.responseText);
                if (response.success) {
                    // 编辑成功，更新页面
                    let index = clazzes.findIndex(function (clazz) {
                        return clazz.clazzId === clazzId;
                    });
                    if (index !== -1) {
                        clazzes[index] = {
                            clazzId: clazzId,
                            name: name,
                            department: department
                        };
                        renderTable();
                        closeModal("edit-class-modal");
                    }
                } else {
                    // 编辑失败，显示错误信息
                    alert("编辑班级失败: " + response.message);
                }
            } else {
                // 请求失败，显示错误信息
                alert("请求失败: " + xhr.status);
            }
        }
    };

    // 设置请求方法和URL
    xhr.open("POST", "/edit-class", true);

    // 设置请求头
    xhr.setRequestHeader("Content-Type", "application/json");

    // 发送请求
    xhr.send(JSON.stringify({
        clazzId: clazzId,
        name: name,
        department: department
    }));
}

// 渲染班级表格
function renderTable() {
    clazzes.sort(function (a, b) {
        return a.clazzId - b.clazzId;
    });
    let tableBody = document.getElementById("class-table-body");
    tableBody.innerHTML = ""; // 清空表格内容

    let startIndex = (currentPage - 1) * pageSize;
    let endIndex = startIndex + pageSize;
    let currentPageClasses = clazzes.slice(startIndex, endIndex);

    Array.from(currentPageClasses).forEach(function (clazz) {
        let row = document.createElement("tr");
        row.innerHTML = `
            <td>${clazz.clazzId}</td>
            <td>${clazz.name}</td>
            <td>${clazz.department}</td>
            <td>
                <button class="delete-button" onclick="showDeleteConfirmation('${clazz.clazzId}')">删除</button>
                <button class="edit-button" onclick="showEditModal('${clazz.clazzId}')">修改</button>
            </td>
        `;
        tableBody.appendChild(row);
    });

    let totalPages = Math.ceil(clazzes.length / pageSize);
    let currentPageSpan = document.getElementById("current-page");
    let totalPagesSpan = document.getElementById("total-pages");

    currentPageSpan.textContent = currentPage;
    totalPagesSpan.textContent = totalPages;

    updatePaginationButtons();
}

// 初始化函数
function init() {
    // 获取班级数据并渲染表格
    fetchClasses();
}

// 获取班级数据（Ajax）
function fetchClasses() {
    // 创建XMLHttpRequest对象
    let xhr = new XMLHttpRequest();

    // 监听请求状态变化
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // 请求成功，处理返回的数据
                clazzes = JSON.parse(xhr.responseText);
                renderTable();
            } else {
                // 请求失败，显示错误信息
                alert("请求失败: " + xhr.status);
            }
        }
    };

    // 设置请求方法和URL
    xhr.open("GET", "/classes", true);

    // 发送请求
    xhr.send();
}

let currentPage = 1; // 当前页码
let pageSize = 10; // 每页显示的行数

// 上一页按钮点击事件处理函数
function previousPage() {
    if (currentPage > 1) {
        currentPage--;
        renderTable();
    }
}

// 下一页按钮点击事件处理函数
function nextPage() {
    let totalPages = Math.ceil(clazzes.length / pageSize);
    if (currentPage < totalPages) {
        currentPage++;
        renderTable();
    }
}


// 更新分页按钮状态
function updatePaginationButtons() {
    let previousPageButton = document.getElementById("previous-page-button");
    let nextPageButton = document.getElementById("next-page-button");
    let totalPages = Math.ceil(clazzes.length / pageSize);

    previousPageButton.disabled = (currentPage === 1);
    nextPageButton.disabled = (currentPage === totalPages);
}

document.addEventListener("DOMContentLoaded", function () {
    init();
});
