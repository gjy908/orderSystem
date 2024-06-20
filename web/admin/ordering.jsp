<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单列表</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <style>
        .status-waiting {
            background-color: #fffae6;
            color: #856404;
        }

        .status-cooking {
            background-color: #e2e3ff;
            color: #383d7a;
        }

        .status-canceled {
            background-color: #f8d7da;
            color: #721c24;
        }

        .status-served {
            background-color: #d4edda;
            color: #155724;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 50;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5);
        }

        .modal-content {
            background-color: #fff;
            margin: 10% auto;
            padding: 20px;
            border: none;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
            width: 40%;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 24px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        .modal-header {
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
            text-align: center;
            font-size: 24px;
            font-weight: bold;
        }

        .modal-body {
            padding: 20px 0;
        }

        .modal-footer {
            padding-top: 10px;
            text-align: right;
        }

        .btn-primary {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .description {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            cursor: pointer;
        }

        .tooltip {
            display: none;
            position: absolute;
            background: #333;
            color: #fff;
            padding: 5px;
            border-radius: 3px;
            max-width: 200px;
            word-wrap: break-word;
            z-index: 100;
        }
    </style>
</head>
<body class="bg-gray-100">
<jsp:include page="/nav/adminNav.jsp"/>

<div class="container mx-auto px-4 py-6">
    <div class="text-3xl font-bold text-center mb-8">订单列表</div>
    <div class="space-y-6">
        <c:forEach var="orderDetails" items="${orderDetailsList}">
            <div class="bg-white p-6 rounded-lg shadow-md">
                <div class="flex justify-between items-center mb-4">
                    <div>
                        <h3 class="text-xl font-bold">订单ID: ${orderDetails.order.orderId}</h3>
                        <p>餐桌ID: ${orderDetails.order.tableId}</p>
                        <p>餐馆ID: ${orderDetails.order.restaurantId}</p>
                        <p>创建时间: ${orderDetails.order.createdAt}</p>
                        <p>更新时间: ${orderDetails.order.updatedAt}</p>
                    </div>
                    <button class="bg-green-500 text-white py-2 px-4 rounded hover:bg-green-700"
                            onclick="confirmCheckout('${orderDetails.order.orderId}')">结算订单
                    </button>
                </div>

                <div class="mt-4">
                    <h4 class="text-lg font-bold mb-2">订单项</h4>
                    <div class="space-y-4">
                        <c:forEach var="itemDetails" items="${orderDetails.orderItems}">
                            <div class="p-4 border rounded-lg <c:choose>
                                            <c:when test='${itemDetails.orderItem.status == 0}'>status-waiting</c:when>
                                            <c:when test='${itemDetails.orderItem.status == 1}'>status-cooking</c:when>
                                            <c:when test='${itemDetails.orderItem.status == 2}'>status-canceled</c:when>
                                            <c:when test='${itemDetails.orderItem.status == 3}'>status-served</c:when>
                                          </c:choose>">
                                <p><strong>菜品ID:</strong> ${itemDetails.menu.menuId}</p>
                                <p><strong>名称:</strong> ${itemDetails.menu.name}</p>
                                <p class="description" onmouseover="showTooltip(this)" onmouseout="hideTooltip(this)" data-tooltip="${itemDetails.menu.description}">
                                    <strong>描述:</strong> ${itemDetails.menu.description}
                                </p>
                                <p><strong>价格:</strong> ${itemDetails.menu.price}</p>
                                <p><strong>数量:</strong> ${itemDetails.orderItem.quantity}</p>
                                <p>
                                    <strong>状态:</strong>
                                    <span class="<c:choose>
                                                    <c:when test='${itemDetails.orderItem.status == 0}'>status-waiting</c:when>
                                                    <c:when test='${itemDetails.orderItem.status == 1}'>status-cooking</c:when>
                                                    <c:when test='${itemDetails.orderItem.status == 2}'>status-canceled</c:when>
                                                    <c:when test='${itemDetails.orderItem.status == 3}'>status-served</c:when>
                                                  </c:choose>">
                                        <c:choose>
                                            <c:when test="${itemDetails.orderItem.status == 0}">等待</c:when>
                                            <c:when test="${itemDetails.orderItem.status == 1}">制作中</c:when>
                                            <c:when test="${itemDetails.orderItem.status == 2}">退菜</c:when>
                                            <c:when test="${itemDetails.orderItem.status == 3}">已上菜</c:when>
                                            <c:otherwise>未知</c:otherwise>
                                        </c:choose>
                                    </span>
                                </p>
                                <button class="bg-blue-500 text-white py-1 px-3 rounded hover:bg-blue-700" onclick="showStatusModal('${itemDetails.orderItem.orderItemId}')">切换状态</button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- 自定义模态框 -->
<div id="statusModal" class="modal hidden flex items-center justify-center">
    <div class="modal-content">
        <div class="modal-header">
            <span class="close" onclick="closeModal()">&times;</span>
            <h2>菜品状态</h2>
        </div>
        <div class="modal-body">
            <form id="statusForm" action="/changeMenuItemStatus" method="post">
                <input type="hidden" id="orderItemId" name="orderItemId">
                <div class="mb-4">
                    <label for="status" class="block text-gray-700">状态：</label>
                    <select id="status" name="status" class="block w-full mt-1 rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring focus:ring-indigo-200 focus:ring-opacity-50">
                        <option value="0">等待</option>
                        <option value="1">制作中</option>
                        <option value="2">退菜</option>
                        <option value="3">已上菜</option>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn-primary" onclick="updateMenuItemStatus()">确认</button>
                    <button type="button" class="btn-primary" onclick="closeModal()">取消</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- 引入 jQuery -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script>
    function showTooltip(element) {
        const tooltipText = element.getAttribute('data-tooltip');
        const tooltip = document.createElement('div');
        tooltip.className = 'tooltip';
        tooltip.innerHTML = tooltipText;
        document.body.appendChild(tooltip);

        const rect = element.getBoundingClientRect();
        tooltip.style.left = rect.left + window.pageXOffset + 'px';
        tooltip.style.top = rect.top + window.pageYOffset + tooltip.offsetHeight + 'px';
        tooltip.style.display = 'block';
    }

    function hideTooltip(element) {
        const tooltips = document.querySelectorAll('.tooltip');
        tooltips.forEach(tooltip => {
            tooltip.remove();
        });
    }

    function showStatusModal(orderItemId) {
        $('#orderItemId').val(orderItemId);
        $('#statusModal').removeClass('hidden');
    }

    function closeModal() {
        $('#statusModal').addClass('hidden');
    }

    function updateMenuItemStatus() {
        const formData = $('#statusForm').serialize();
        $.post('/cook/ordering', formData, function(res) {
            res = JSON.parse(res);
            if (res.success) {
                alert('菜品状态更新成功');
                location.reload();
            } else {
                alert('菜品状态更新失败: ' + res.message);
            }
        });
    }

    function confirmCheckout(orderId) {
        if (confirm("确认要结算订单 " + orderId + " 吗？")) {
            checkoutOrder(orderId);
        }
    }

    function checkoutOrder(orderId) {
        $.ajax({
            type: 'POST',
            url: '/admin/checkoutOrder',
            data: { orderId: orderId },
            dataType: 'json',
            success: function (res) {
                if (res.success) {
                    alert('订单结算成功');
                } else {
                    alert('订单结算失败: ' + res.message);
                }
                location.reload();
            },
            error: function () {
                alert('请求失败，请稍后重试');
            }
        });
    }

    window.onclick = function(event) {
        const modal = document.getElementById('statusModal');
        if (event.target == modal) {
            closeModal();
        }
    }
</script>

</body>
</html>
