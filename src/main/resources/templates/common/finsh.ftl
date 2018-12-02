<html>
<head>
    <title>完结订单成功</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-success">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    成功!
                </h4> <strong>${msg}！</strong> Best check yo self, you're not looking too good. <a href="${url}" class="alert-link">alert link</a>
            </div>
        </div>
    </div>
</div>
<script>
    setTimeout('window.location.href="${url}"', 3000);
</script>
</body>
</html>

