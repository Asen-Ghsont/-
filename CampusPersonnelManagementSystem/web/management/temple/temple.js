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