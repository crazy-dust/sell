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
                    <table class="table table-bordered table-hover table-condensed">
                        <thead>
                        <tr>
                            <th>
                                商品类别ID
                            </th>
                            <th>
                                商品类别名称
                            </th>
                            <th>
                                商品类别类型
                            </th>
                            <th>
                                创建时间
                            </th>
                            <th>
                                修改时间
                            </th>
                            <th>
                                操作
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                       <#list productCategoryList as productCategory>
                           <tr>
                               <td>
                                   ${productCategory.categoryId}
                               </td>
                               <td>
                                   ${productCategory.categoryName}
                               </td>
                               <td>
                                   ${productCategory.categoryType}
                               </td>
                               <td>
                                   ${productCategory.createTime}
                               </td>
                               <td>
                                   ${productCategory.updateTime}
                               </td>
                               <td>
                                   <a href="/sell/seller/category/index?categoryid=${productCategory.categoryId}">修改</a>
                               </td>
                           </tr>
                       </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<#--bootstrap引入-->
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
</html>