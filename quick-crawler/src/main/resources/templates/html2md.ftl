<#assign base=request.contextPath />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <base id="base" href="${base}">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">
    <script src="http://cdn.bootcss.com/jquery/2.0.1/jquery.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h4 class="panel-title">
                    链接只支持CSDN，代码高亮解析也只支持CSDN
                </h4>
            </div>
            <div class="panel-body">
                <div class="form-group col-md-10">
                    <label class="sr-only" for="blogUrl">只支持CSDN</label>
                    <div class="input-group">
                        <div class="input-group-addon">文章链接</div>
                        <input type="text" class="form-control" id="blogUrl" placeholder="只支持CSDN的文章链接">
                    </div>
                </div>
                <div class="form-group col-md-10">
                    <label>HTML源代码</label>
                    <textarea class="form-control" rows="8" id="htmlString" style="resize:none"></textarea>
                </div>
                <div class="form-group col-md-10">
                    <button type="submit" id="submitBtn" onclick="convert()" class="btn btn-primary">转换</button>
                </div>
                <div class="form-group col-md-10">
                    <label>MarkDown格式源代码</label>
                    <textarea class="form-control" rows="8" id="result" style="resize:none"></textarea>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function convert() {
        var url = $("#blogUrl").val();
        var html = $("#htmlString").val();
        var base = document.getElementById("base").href
        var target = base.replace('html2md','convert');
        $.ajax({
            type: "POST",
            url: target,
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({url:url,html:html}),
            success: function(data){
                var result = data.data;
                $("#result").val(result);
            }
        });
    }
</script>
</body>
</html>