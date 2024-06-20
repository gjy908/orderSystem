<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>菜单列表</title>
    <!-- 引入Tailwind CSS -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <!-- 引入jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <style>
        .table-header {
            text-align: center;
            padding: 20px 0;
        }
        .status-active {
            color: green;
        }
        .status-inactive {
            color: red;
        }
        .truncate-ellipsis {
            display: block;
            max-width: 200px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .table-row:hover {
            background-color: #f2f2f2;
        }
        .full-description {
            position: absolute;
            background-color: #fff;
            border: 1px solid #ccc;
            padding: 10px;
            border-radius: 5px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
            z-index: 100;
            display: none;
        }
        .dialog-overlay {
            position: fixed;
            inset: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            background: rgba(0, 0, 0, 0.5);
        }
    </style>
</head>
<body class="bg-gray-100">
<jsp:include page="/nav/cookNav.jsp"/>

<div class="container mx-auto p-4">
    <div class="bg-white p-8 rounded-lg shadow-lg">
        <div class="table-header">
            <h2 class="text-3xl font-bold">菜单列表</h2>
        </div>
        <div class="overflow-x-auto">
            <table class="min-w-full bg-white">
                <thead>
                <tr>
                    <th class="py-2 px-4 border-b">菜单ID</th>
<%--                    <th class="py-2 px-4 border-b">餐厅ID</th>--%>
                    <th class="py-2 px-4 border-b">名称</th>
                    <th class="py-2 px-4 border-b">描述</th>
                    <th class="py-2 px-4 border-b">价格</th>
                    <th class="py-2 px-4 border-b">状态</th>
                    <th class="py-2 px-4 border-b">推荐</th>
                    <th class="py-2 px-4 border-b">创建时间</th>
                    <th class="py-2 px-4 border-b">更新时间</th>
                    <th class="py-2 px-4 border-b">操作</th>
                </tr>
                </thead>
                <tbody>
                <!-- 使用JSTL标签遍历菜单列表 -->
                <c:forEach var="menu" items="${menuList}">
                    <tr class="table-row">
                        <td class="py-4 px-6 border-b text-center">${menu.menuId}</td>
<%--                        <td class="py-4 px-6 border-b text-center">${menu.restaurantId}</td>--%>
                        <td class="py-4 px-6 border-b text-center">${menu.name}</td>
                        <td class="py-4 px-6 border-b text-center relative">
                            <span class="truncate-ellipsis">${menu.description}</span>
                            <div class="full-description">${menu.description}</div>
                        </td>
                        <td class="py-4 px-6 border-b text-center">${menu.price}</td>
                        <td class="py-4 px-6 border-b text-center">
                            <span class="<c:choose>
                                <c:when test='${menu.status == 1}'>status-active</c:when>
                                <c:otherwise>status-inactive</c:otherwise>
                            </c:choose>">
                                <c:choose>
                                    <c:when test="${menu.status == 1}">上架</c:when>
                                    <c:otherwise>下架</c:otherwise>
                                </c:choose>
                            </span>
                        </td>
                        <td class="py-4 px-6 border-b text-center">
                            <c:choose>
                                <c:when test="${menu.commend >= 3}">推荐</c:when>
                                <c:otherwise>不推荐</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="py-4 px-6 border-b text-center">${menu.createdAt}</td>
                        <td class="py-4 px-6 border-b text-center">${menu.updatedAt}</td>
                        <td class="py-4 px-6 border-b text-center">
                            <button class="bg-blue-500 text-white py-1 px-3 rounded hover:bg-blue-700" onclick="showEditDialog('${menu.menuId}', '${menu.status}')">编辑</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- 初始化Tailwind CSS -->
<script>
    document.addEventListener('DOMContentLoaded', function () {
        // 鼠标悬停显示完整描述
        document.querySelectorAll('.truncate-ellipsis').forEach(item => {
            item.addEventListener('mouseover', function () {
                const fullDescription = this.nextElementSibling;
                fullDescription.style.display = 'block';
            });
            item.addEventListener('mouseout', function () {
                const fullDescription = this.nextElementSibling;
                fullDescription.style.display = 'none';
            });
        });

        // 显示编辑对话框
        window.showEditDialog = function(menuId, currentStatus) {
            const content = `
                <form id="editForm" class="p-4 bg-white rounded-lg shadow-lg">
                    <input type="hidden" id="menuId" name="menuId" value="`+menuId+`">
                    <div class="mb-4">
                        <label class="block text-gray-700 text-sm font-bold mb-2" for="status">状态：</label>
                        <select name="status" id="status" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                            <option value="0" ${currentStatus == 0 ? 'selected' : ''}>下架</option>
                            <option value="1" ${currentStatus == 1 ? 'selected' : ''}>上架</option>
                        </select>
                    </div>
                    <div class="flex items-center justify-between">
                        <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit">确认</button>
                    </div>
                </form>
            `;

            const dialog = document.createElement('div');
            dialog.innerHTML = content;
            dialog.classList.add('dialog-overlay');
            dialog.addEventListener('click', function (e) {
                if (e.target === dialog) {
                    document.body.removeChild(dialog);
                }
            });

            document.body.appendChild(dialog);

            // 提交编辑表单
            $('#editForm').on('submit', function(event) {
                event.preventDefault();
                const formData = $(this).serialize();
                console.log("formData==",formData)
                $.post('/cook/menu', formData, function(res) {
                    if (res.success) {
                        alert('状态修改成功');
                        location.reload(); // 刷新页面以更新状态
                    } else {
                        alert('状态修改失败：' + res.message);
                    }
                });
            });
        };
    });
</script>

</body>
</html>
