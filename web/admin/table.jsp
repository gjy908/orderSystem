<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>餐桌列表</title>
    <!-- 引入LayUI的相关样式文件 -->
    <link rel="stylesheet" href="https://cdn.bootcdn.net/ajax/libs/layui/2.6.8/css/layui.css">
    <!-- 引入jQuery -->
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <!-- 引入LayUI的相关js文件 -->
    <script src="https://cdn.bootcdn.net/ajax/libs/layui/2.6.8/layui.js"></script>
    <style>
        .container {
            margin: 20px;
        }

        .header {
            text-align: center;
            font-size: 24px;
            margin-bottom: 20px;
        }

        .layui-table img {
            max-width: 100px;
            max-height: 100px;
        }

        .status-available {
            color: green;
        }

        .status-occupied {
            color: red;
        }

        .view-btn {
            text-align: center;
        }
    </style>
</head>
<body>
<jsp:include page="/nav/adminNav.jsp"/>

<div class="container">
    <div class="header">餐桌列表</div>
    <table class="layui-table">
        <thead>
        <tr>
            <th>餐桌ID</th>
            <th>餐馆ID</th>
            <th>餐桌编号</th>
            <th>二维码</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>更新时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tableList}" var="table">
            <tr>
                <td>${table.tableId}</td>
                <td>${table.restaurantId}</td>
                <td>${table.tableNumber}</td>
                <td>
                    <img src="${table.qrCode}" alt="二维码">
                </td>
                <td>
                    <c:choose>
                        <c:when test="${table.status == 0}">
                            <span class="status-available">空闲</span>
                        </c:when>
                        <c:otherwise>
                            <span class="status-occupied">占用</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${table.createdAt}</td>
                <td>${table.updatedAt}</td>
                <td class="view-btn">
                    <button class="layui-btn layui-btn-primary layui-btn-sm" onclick="showStatistics('${table.tableId}')">统计</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- 统计模态框 -->
<div id="statisticsModal" class="layui-modal" style="display: none;">
    <div class="layui-modal-content">
        <div class="layui-modal-header">
            <span>餐桌使用统计</span>
            <button type="button" class="layui-btn layui-btn-primary layui-btn-sm layui-btn-close" onclick="closeModal()">×</button>
        </div>
        <div class="layui-modal-body" id="statisticsContent">
            <!-- 统计数据将在此渲染 -->
        </div>
    </div>
</div>

<script>
    layui.use(['layer'], function () {
        var layer = layui.layer;

        function showStatistics(tableId) {
            $.get('/admin/statistics?tableId=' + tableId, function(data){
                // 在模态框中渲染统计数据
                var html = '<ul>';
                $.each(data, function(index, item) {
                    html += '<li>' + item.date + ': ' + item.count + '次</li>';
                });
                html += '</ul>';
                $('#statisticsContent').html(html);

                layer.open({
                    type: 1,
                    title: '餐桌使用统计',
                    content: $('#statisticsModal'),
                    area: ['400px', '300px']
                });
            });
        }

        function closeModal() {
            layer.closeAll();
        }
    });
</script>
</body>
</html>
