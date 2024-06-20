<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>食材列表</title>
    <!-- 引入Tailwind CSS -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <!-- 引入jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <style>
        .container {
            margin: 20px;
        }

        .header {
            text-align: center;
            font-size: 24px;
            margin-bottom: 20px;
        }

        .edit-btn {
            text-align: center;
        }

        .ellipsis {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .status-active {
            color: green;
        }

        .status-inactive {
            color: red;
        }

        .modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .modal-content {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .modal-header button {
            background: transparent;
            border: none;
            font-size: 20px;
            cursor: pointer;
        }

        .modal-body {
            margin-top: 20px;
        }
    </style>
</head>
<body class="bg-gray-100">
<jsp:include page="/nav/cookNav.jsp"/>

<div class="container">
    <div class="header">食材列表</div>
    <table class="min-w-full bg-white">
        <thead>
        <tr>
            <th class="py-2 px-4 border-b">食材ID</th>
<%--            <th class="py-2 px-4 border-b">餐馆ID</th>--%>
            <th class="py-2 px-4 border-b">食材名称</th>
            <th class="py-2 px-4 border-b">库存</th>
<%--            <th class="py-2 px-4 border-b">创建时间</th>--%>
            <th class="py-2 px-4 border-b">更新时间</th>
            <th class="py-2 px-4 border-b">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${ingredientList}" var="ingredient">
            <tr>
                <td class="py-4 px-6 border-b text-center">${ingredient.ingredientId}</td>
<%--                <td class="py-4 px-6 border-b text-center">${ingredient.restaurantId}</td>--%>
                <td class="py-4 px-6 border-b text-center ellipsis">${ingredient.name}</td>
                <td class="py-4 px-6 border-b text-center">${ingredient.quantity}</td>
<%--                <td class="py-4 px-6 border-b text-center">${ingredient.createdAt}</td>--%>
                <td class="py-4 px-6 border-b text-center">${ingredient.updatedAt}</td>
                <td class="py-4 px-6 border-b text-center edit-btn">
                    <button class="bg-blue-500 text-white py-1 px-3 rounded hover:bg-blue-700" onclick="showEditModal('${ingredient.ingredientId}', '${ingredient.quantity}')">编辑</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- 编辑模态框 -->
<div id="editModal" class="modal-overlay hidden">
    <div class="modal-content">
        <div class="modal-header">
            <span>编辑食材</span>
            <button type="button" onclick="closeModal()">×</button>
        </div>
        <div class="modal-body">
            <form id="editForm" class="space-y-4">
                <input type="hidden" id="ingredientId" name="ingredientId">
                <div>
                    <label for="quantity" class="block text-gray-700">数量</label>
                    <input type="number" id="quantity" name="quantity" required placeholder="请输入数量" class="mt-1 block w-full rounded border-gray-300 shadow-sm focus:border-indigo-500 focus:ring focus:ring-indigo-200 focus:ring-opacity-50">
                </div>
                <div class="text-right">
                    <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">确认</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function showEditModal(ingredientId, quantity) {
        $('#ingredientId').val(ingredientId);
        $('#quantity').val(quantity);
        $('#editModal').removeClass('hidden');
    }

    function closeModal() {
        $('#editModal').addClass('hidden');
    }

    // 初始化表单提交事件
    $('#editForm').on('submit', function(event) {
        event.preventDefault();
        const formData = $(this).serialize();
        $.post('/cook/ingredient', formData, function(res) {
            res = JSON.parse(res);
            if (res.success) {
                alert(res.message);
                location.reload(); // 刷新页面以更新状态
            } else {
                alert('更新失败：' + res.message);
            }
            closeModal();
        });
    });
</script>

</body>
</html>
