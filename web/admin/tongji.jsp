<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Revenue 列表</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <style>
        .status-deleted {
            background-color: #f8d7da;
            color: #721c24;
        }

        .status-active {
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
            padding: 20px;
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

        .amount-number {
            font-weight: bold;
        }

        .amount-positive .amount-number {
            color: #38a169; /* 绿色 */
        }

        .amount-negative .amount-number {
            color: #e53e3e; /* 红色 */
        }
    </style>
</head>
<body class="bg-gray-100">
<jsp:include page="/nav/adminNav.jsp"/>

<div class="container mx-auto px-4 py-6">
    <div class="text-3xl font-bold text-center mb-8">Revenue 列表</div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <c:forEach var="revenue" items="${revenueList}">
            <div class="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-200">
                <div class="flex justify-between items-center mb-4">
                    <div>
                        <h3 class="text-xl font-bold">ID: ${revenue.id}</h3>
                        <p>日期: ${revenue.date}</p>
                        <p class="<c:choose>
                                     <c:when test='${revenue.amount >= 0}'>amount-positive</c:when>
                                     <c:otherwise>amount-negative</c:otherwise>
                                  </c:choose>">金额: <span class="amount-number">¥${revenue.amount}</span></p>
                        <p>创建时间: ${revenue.createTime}</p>
                        <p>更新时间: ${revenue.updateTime}</p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>


<!-- 引入 jQuery -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
</body>
</html>
