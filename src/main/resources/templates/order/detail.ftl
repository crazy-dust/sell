<html>

    <#include "../common/header.ftl">

    <body>
        <div id="wrapper" class="toggled">

        <#--边栏-->
            <#include "../common/nav.ftl">

        <#--主要内容-->
            <br/><br/><br/>
            <div class="container">
                <div class="row clearfix">

                <#--主订单详情-->
                    <div class="col-md-6 column">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th>
                                    订单ID
                                </th>
                                <th>
                                    订单总金额
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${orderMasterDTO.orderId}</td>
                                <td>${orderMasterDTO.orderAmount}</td>
                            </tr>
                        </table>
                    </div>

                <#--订单详情-->
                    <div class="col-md-12 column">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th>
                                    商品ID
                                </th>
                                <th>
                                    商品名称
                                </th>
                                <th>
                                    商品价格
                                </th>
                                <th>
                                    商品数量
                                </th>
                                <th>
                                    创建时间
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                    <#list orderDetailList as OrderDetail>
                    <tr>
                        <td>${OrderDetail.productId}</td>
                        <td>${OrderDetail.productName}</td>
                        <td>${OrderDetail.productPrice}</td>
                        <td>${OrderDetail.productQuantity}</td>
                        <td>${OrderDetail.createTime}</td>
                    </tr>
                    </#list>
                            </tbody>
                        </table>
                    </div>

                <#-- 订单处理 -->
                    <div class="col-md-12 column">

                <#if orderMasterDTO.getOrderStatus() != 1>
                    <a href="/sell/seller/order/finsh?orderid=${orderid}"><button type="button" class="btn btn-default btn-primary">完结订单</button></a>
                <#else>
                </#if>


                <#if orderMasterDTO.getOrderStatus() != 2 && orderMasterDTO.getOrderStatus() != 1>
                    <a href="/sell/seller/order/cancel?orderid=${orderid}"><button type="button" class="btn btn-default btn-danger">取消订单</button></a>
                <#else>
                </#if>
                    </div>
                </div>
            </div>
        </div>

        <#--bootstrap引入-->
        <script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </body>
</html>