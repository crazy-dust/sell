<html>
    <#include "../common/header.ftl">

    <body>

        <div id="wrapper" class="toggled">

        <#--边栏-->
            <#include "../common/nav.ftl">
            <#setting number_format="#">

        <#--主要内容-->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <form role="form" enctype="multipart/form-data" method="post" action="/sell/seller/product/save">
                                <div class="form-group">
                                    <label for="exampleInputEmail1">名称</label>
                                    <input type="text" class="form-control" name="productName" value="${(productInfo.productName) !''}"/>
                                </div>

                                <div class="form-group">
                                    <label for="exampleInputEmail1">价格</label>
                                    <input type="text" class="form-control" name="productPrice" value="${(productInfo.productPrice) !''}" />
                                </div>

                                <div class="form-group">
                                    <label for="exampleInputEmail1">库存</label>
                                    <input type="text" class="form-control" name="productStock" value="${(productInfo.productStock) !''} " />
                                </div>

                                <div class="form-group">
                                    <label for="exampleInputEmail1">描述</label>
                                    <input type="text" class="form-control" name="productDescription"  value="${(productInfo.productDescription)!''}"/>
                                </div>

                                <div class="form-group">
                                    <label for="exampleInputFile">图片</label>
                                    <img width="300" height="300" alt="${(productInfo.productName)!''}" src="\sell${(productInfo.productIcon)!''}">
                                    <br/><br/>
                                    <input name="productIcon" type="file" id="photoFile" />
                                </div>

                                <div class="form-group">
                                    <label for="exampleInputEmail1">类目</label>
                                        <select name="categoryType">
                                            <#list productCategoryList as productCategory>
                                                <option value="${productCategory.categoryId}"
                                                        <#if (productInfo.categoryType) ?? && productInfo.categoryType == productCategory.categoryType>
                                                            selected
                                                        </#if>
                                                >${productCategory.categoryName}
                                                </option>
                                            </#list>
                                        </select>
                                </div>

                                <input type="hidden" name="productId" value="${(productInfo.productId) !''}">
                                <button type="submit" class="btn btn-default">提交</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <#--bootstrap引入-->
        <script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </body>
</html>