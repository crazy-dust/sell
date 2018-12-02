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
                        <div class="col-md-12 column">
                            <form role="form" enctype="multipart/form-data" method="post" action="/sell/seller/category/save">
                                <div class="form-group">
                                    <label for="exampleInputEmail1">名称</label>
                                    <input type="text" class="form-control" name="categoryName" value="${(productCategory.categoryName) !''}"/>
                                </div>

                                <div class="form-group">
                                    <label for="exampleInputEmail1">类型</label>
                                    <input type="text" class="form-control" name="categoryType" value="${(productCategory.categoryType) !''}" />
                                </div>

                                <input type="hidden" name="categoryId" value="${(productCategory.categoryId) !''}">
                                <button type="submit" class="btn btn-default" onsubmit="javascript: ceshi()" id="submit">提交</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
<#--bootstrap引入-->
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
</html>