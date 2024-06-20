<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>菜单列表</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
        }
        .container {
            max-width: 1000px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .table-title {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 20px;
            text-align: center;
            color: #333;
        }
        .menu-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .menu-table th, .menu-table td {
            padding: 15px;
            text-align: center;
            border-bottom: 1px solid #e2e2e2;
        }
        .menu-table img {
            width: 80px;
            height: 80px;
            border-radius: 4px;
            box-shadow: 0 0 5px rgba(0,0,0,0.1);
            transition: transform 0.3s;
            cursor: pointer;
        }
        .menu-table img:hover {
            transform: scale(1.1);
        }
        .description {
            max-width: 200px;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            cursor: pointer;
        }
        .status-available {
            color: green;
        }
        .status-unavailable {
            color: red;
        }
        .stars {
            color: gold;
        }
        .action-btn {
            background-color: #1E9FFF;
            color: #fff;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .action-btn:hover {
            background-color: #0069d9;
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
            justify-content: center;
            align-items: center;
        }
        .modal-content {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
        }
        .modal-content img {
            max-width: 100%;
            border-radius: 8px;
        }
        .modal-content p {
            margin-top: 10px;
            font-size: 18px;
            color: #333;
        }
        .modal-content h3 {
            margin-bottom: 10px;
        }
        .quantity-input {
            width: 50px;
            text-align: center;
            margin-right: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="/nav/customerNav.jsp"/>
<div class="container">
    <div class="table-title">菜单列表</div>
    <table class="menu-table">
        <thead>
        <tr>
            <th>菜单ID</th>
            <th>图片</th>
            <th>名称</th>
            <th>描述</th>
            <th>价格</th>
            <th>推荐指数</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="menu" items="${menuList}">
            <tr>
                <td>${menu.menuId}</td>
                <td>
                    <img src="${menu.imageUrl}" alt="${menu.name}" onclick="viewImage('${menu.imageUrl}')">
                </td>
                <td>${menu.name}</td>
                <td><div class="description" onclick="viewDescription('${menu.description}')">${menu.description}</div></td>
                <td>￥<span class="font-bold text-red-500">${menu.price}</span></td>
                <td>
                    <div class="stars">
                        <c:forEach var="i" begin="1" end="${menu.commend}">
                            <i class="fa fa-star"></i>
                        </c:forEach>
                        <c:forEach var="i" begin="${menu.commend + 1}" end="5">
                            <i class="fa fa-star-o"></i>
                        </c:forEach>
                    </div>
                </td>
                <td><button class="action-btn" onclick="addToCart('${menu.menuId}', '${menu.name}', ${menu.price})">加入购物车</button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- 图片放大模态框 -->
<div id="imageModal" class="modal">
    <div class="modal-content">
        <img id="imageModalContent" />
    </div>
</div>

<!-- 描述模态框 -->
<div id="descriptionModal" class="modal">
    <div class="modal-content">
        <p id="descriptionModalContent"></p>
    </div>
</div>

<!-- 加入购物车模态框 -->
<div id="cartModal" class="modal">
    <div class="modal-content">
        <h3 id="cartItemName"></h3>
        <p>单价: ￥<span id="cartItemPrice"></span></p>
        <div>
            <label for="quantity">数量:</label>
            <input type="number" id="quantity" name="quantity" value="1" min="1" class="quantity-input">
        </div>
        <button class="action-btn" style="margin-top: 10px;" id="confirmAddToCart">下单</button>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
<script>
    $(document).ready(function(){
        // 点击图片放大
        window.viewImage = function(imageUrl) {
            $('#imageModalContent').attr('src', imageUrl);
            $('#imageModal').css('display', 'flex');
        };

        // 查看完整描述
        window.viewDescription = function(description) {
            $('#descriptionModalContent').text(description);
            $('#descriptionModal').css('display', 'flex');
        };

        // 加入购物车
        window.addToCart = function(menuId, name, price) {
            $('#cartItemName').text(name);
            $('#cartItemPrice').text(price);
            $('#quantity').val(1); // 重置数量为1
            $('#cartModal').css('display', 'flex');
            // 绑定确认按钮点击事件
            $('#confirmAddToCart').off('click').on('click', function() {
                var quantity = $('#quantity').val();
                var totalPrice = quantity * price;

                // 构造要发送的数据
                var data = {
                    menuId: menuId,
                    quantity: quantity,
                    tId: ${tId},
                    rId:${rId}
                };
                console.log('Order data:', data);
                $.ajax({
                    url: '/customer/order',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    success: function(response) {
                        console.log('Order placed successfully:', response);
                        alert('已下单! 总价: ￥' + totalPrice.toFixed(2));
                        $('#cartModal').css('display', 'none');
                    },
                    error: function(xhr, status, error) {
                        console.error('Error placing order:', error);
                        alert('下单失败，请重试');
                    }
                });
            });
        };

        // 关闭模态框
        $('.modal').on('click', function(event) {
            if ($(event.target).is('.modal')) {
                $(this).css('display', 'none');
            }
        });
    });
</script>
</body>
</html>
