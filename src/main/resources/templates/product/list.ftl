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

                        <#--填充页面-->
                        <div class="col-md-12 column">
                            <table class="table table-bordered table-hover table-condensed" width="1000">
                                <thead>
                                <tr>
                                    <th>商品ID</th>
                                    <th>名称</th>
                                    <th>图片</th>
                                    <th>单价</th>
                                    <th>库存</th>
                                    <th>描述</th>
                                    <th>类目</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list productInfoPage.getContent() as productInfo>
                                    <tr>
                                        <td>${productInfo.productId}</td>
                                        <td>${productInfo.productName}</td>
                                        <td>
                                            <img height="30" width="30" alt="${productInfo.productName}" src="/sell${productInfo.productIcon}">
                                        </td>
                                        <td>${productInfo.productPrice}</td>
                                        <td>${productInfo.productStock}</td>
                                        <td>${productInfo.productDescription}</td>
                                        <td>${productInfo.categoryType}</td>
                                        <td>${productInfo.createTime}</td>
                                        <td>${productInfo.updateTime}</td>
                                        <td>
                                            <a>
                                                <a href="/sell/seller/product/index?productid=${productInfo.productId}">修改</a>
                                            </a>
                                        </td>
                                        <td>
                                            <#if productInfo.getProductStatus() == 0>
                                                <a href="/sell/seller/product/downAndup?productid=${productInfo.productId}&status=0">下架</a>
                                                <#else>
                                                <a href="/sell/seller/product/downAndup?productid=${productInfo.productId}&status=1">上架</a>
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
                                         <a href="/sell/seller/product/list?page=1&size=${size}">上一页</a>
                                     </li>
                                    <#else>
                                    <li>
                                        <a href="/sell/seller/product/list?page=${currentPage - 1}&size=${size}">上一页</a>
                                    </li>
                                </#if>

                                <#--主要分页页数-->
                                <#list 1..productInfoPage.getTotalPages() as index>
                                    <#if currentPage == index>
                                        <li class="disabled">
                                            <a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a>
                                        </li>
                                        <#else>
                                            <li>
                                                <a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a>
                                            </li>
                                    </#if>

                                </#list>

                                <#--下一页-->
                                <#if currentPage gte productInfoPage.getTotalPages()>
                                     <li class="disabled">
                                         <a href="/sell/seller/product/list?page=${productInfoPage.getTotalPages()}&size=${size}">下一页</a>
                                     </li>
                                <#else>
                                    <li>
                                        <a href="/sell/seller/product/list?page=${currentPage + 1}&size=${size}">下一页</a>
                                    </li>
                                </#if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <#--bootstrap引入-->
        <script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </body>
</html>