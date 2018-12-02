<html>
    <#include "../common/header.ftl">

    <body>

        <div id="wrapper" class="toggled">

            <#--边栏-->
            <#include "../common/nav.ftl">

            <#--主要内容-->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">

                        <#--填充-->
                        <div class="col-md-12 column">
                            <table class="table table-hover table-bordered table-condensed">
                                <thead>
                                <tr>
                                    <th>订单ID</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>地址</th>
                                    <th>金额</th>
                                    <th>订单状态</th>
                                    <th>支付状态</th>
                                    <th>创建时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                    <#list orderMasterDTOPage.getContent() as orderMasterDTO>
                    <tr>
                        <td>${orderMasterDTO.orderId}</td>
                        <td>${orderMasterDTO.buyerName}</td>
                        <td>${orderMasterDTO.buyerPhone}</td>
                        <td>${orderMasterDTO.buyerAddress}</td>
                        <td>${orderMasterDTO.orderAmount}</td>
                        <td>${orderMasterDTO.getOrderStatusEnum().getStateInfo()}</td>
                        <td>${orderMasterDTO.getPayStatusEnum().getStateInfo()}</td>
                        <td>${orderMasterDTO.createTime}</td>
                        <td>
                            <a href="/sell/seller/order/detail?orderid=${orderMasterDTO.orderId}">详情</a>
                        </td>
                        <td>
                            <#if orderMasterDTO.getOrderStatusEnum().getState() != OrderStatusEnum.getState() && orderMasterDTO.getOrderStatusEnum().getState() != 1>
                                <a href="/sell/seller/order/cancel?orderid=${orderMasterDTO.orderId}">取消</a>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                                </tbody>
                            </table>
                        </div>

                        <#--分页-->
                        <div class="col-md-12 column">
                            <ul class="pagination pull-right">

                                <#--上一页-->
                                <#if currentPage lte 1>
                                    <li class="disabled">
                                        <a href="#">上一页</a>
                                    </li>
                                <#else>
                                    <li>
                                        <a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a>
                                    </li>
                                </#if>

                                <#--主要分页页数-->
                                <#list 1..orderMasterDTOPage.getTotalPages() as index>
                                    <#if index == currentPage>
                                        <li class="disabled">
                                            <a href="#">${index}</a>
                                        </li>
                                    <#else>
                                         <li>
                                             <a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
                                         </li>
                                    <#--<#if index gte 5 && index lte 12>-->
                                    <#--<#if index == 5>-->
                                    <#--<li>-->
                                    <#--<a>...</a>-->
                                    <#--</li>-->
                                    <#--</#if>-->
                                    <#--<#else>-->

                                    <#--</#if>-->
                                    </#if>
                                </#list>

                                <#--下一页-->
                                <#if currentPage gte orderMasterDTOPage.getTotalPages()>
                                    <li class="disabled">
                                        <a href="#">下一页</a>
                                    </li>
                                 <#else>
                                    <li>
                                        <a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a>
                                    </li>
                                 </#if>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    <#--websocket消息弹窗提示新订单-->
    <div class="modal fade" id="mymodal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        提醒
                    </h4>
                </div>
                <div class="modal-body">
                    您有新的订单来啦!
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="close">关闭</button>
                    <button type="button" class="btn btn-primary" id="selectOrder" onclick="javascript: window.location.reload()">查看新订单</button>
                </div>
            </div>
        </div>
    </div>

        <#--播放音乐-->
        <audio id="music" loop="loop">
            <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
        </audio>

        <#--jquery引入-->
        <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>

        <#--bootstrap引入-->
        <script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>

        <#--websocket支持-->
        <script>
            var websocket = null;

            //  in: 检查它window（或其原型链）是否包含具有指定名称的属性的对象。
            if('WebSocket' in window) {
                websocket = new WebSocket('ws://localhost:8080/sell/websocket');
            } else {
                alert('该浏览器不支持websocket!');
            }

            websocket.onopen = function (event) {
                console.log('建立连接');
            }

            websocket.onclose = function (event) {
                console.log('连接关闭');
            }

            websocket.onmessage = function (event) {
                console.log('收到消息' + event.data);

                // 弹窗提醒, 播放音乐
                $('#mymodal').modal('show');
                document.getElementById('music').play();

                //关闭音乐
                $('#close').on('click', function () {
                    document.getElementById('music').pause();
                });

                // $('#selectOrder').on('click', function (e) {
                //     window.location.href = 'www.baidu.com';
                // });
            }

            websocket.error = function () {
                alert('websocket通信错误');
            }

            websocket.onbeforeunload = function () {
                websocket.close();
            }
        </script>

    </body>
</html>
