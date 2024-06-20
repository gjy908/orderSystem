<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>餐厅桌号列表</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <style>
        .status-idle {
            color: green;
        }
        .status-occupied {
            color: red;
        }
        .status-reserved {
            color: orange;
        }
        .status-unknown {
            color: gray;
        }
    </style>
</head>
<body class="bg-gray-100">
<jsp:include page="/nav/customerNav.jsp"/>
<div class="container mx-auto my-10 p-8 bg-white rounded-lg shadow-lg">
    <h1 class="text-4xl font-bold mb-8">餐厅桌号列表</h1>
    <div class="overflow-x-auto">
        <table class="min-w-full bg-white">
            <thead>
            <tr>
                <th class="py-2 px-4 border-b-2 border-gray-300 text-left text-lg text-gray-700">桌号</th>
<%--                <th class="py-2 px-4 border-b-2 border-gray-300 text-left text-lg text-gray-700">二维码</th>--%>
                <th class="py-2 px-4 border-b-2 border-gray-300 text-left text-lg text-gray-700">状态</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="table" items="${tableList}">
                <tr class="hover:bg-gray-50">
                    <td class="py-4 px-6 border-b border-gray-200">${table.tableNumber}</td>
<%--                    <td class="py-4 px-6 border-b border-gray-200">--%>
<%--                        <a href="javascript:void(0);" onclick="viewQRCode('${table.qrCode}')">--%>
<%--                            <img class="w-12 h-12 rounded-lg shadow-md" src="${table.qrCode}" alt="QR Code">--%>
<%--                        </a>--%>
<%--                    </td>--%>
                    <td class="py-4 px-6 border-b border-gray-200">
                        <c:choose>
                            <c:when test="${table.status == 0}">
                                <div class="flex space-x-2">
                                    <button class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded" onclick="reserveTable(${table.tableId})">预订</button>
                                    <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" onclick="orderTable(${table.restaurantId},${table.tableId})">点餐</button>
                                </div>
                            </c:when>
                            <c:when test="${table.status == 1}">
                                <span class="status-occupied">占用</span>
                            </c:when>
                            <c:when test="${table.status == 2}">
                                <span class="status-reserved">已预订</span>
                            </c:when>
                            <c:otherwise>
                                <span class="status-unknown">未知</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/layui/2.7.2/layui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
    layui.use(['layer'], function(){
        var layer = layui.layer;

        // 点击二维码图片查看放大效果
        window.viewQRCode = function(qrCodeUrl) {
            layer.photos({
                photos: {
                    title: '二维码放大查看',
                    data: [{
                        src: qrCodeUrl
                    }]
                },
                shadeClose: true,
                closeBtn: 1,
                anim: 5
            });
        }

        // 预订桌子
        window.reserveTable = function(tableId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/customer/table',
                type: 'POST',
                data: { tableId: tableId },
                success: function(response) {
                    if (response.success) {
                        layer.msg(response.message, {icon: 1});
                        setTimeout(function() {
                            location.reload(); // 重新加载页面刷新状态
                        }, 1000);
                    } else {
                        layer.msg('预订失败: ' + response.message, {icon: 2});
                    }
                },
                error: function() {
                    layer.msg('请求失败，请稍后重试', {icon: 2});
                }
            });
        }

        // 点餐
        window.orderTable = function(restaurantId, tableId) {

            window.location.href = '${pageContext.request.contextPath}/customer/menu?rId=' + restaurantId + '&tId=' + tableId;
        }
    });
</script>

</body>
</html>
