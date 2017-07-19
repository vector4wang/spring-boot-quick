<!DOCTYPE html>

<html lang="en">
<head>
    <title>Img2Txt</title>
    <meta charset="utf-8"/>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    Img2Txt 选择您所要上传的图片(背景为白色效果最好),支持jpg,png格式，大小不要超过2M
                </h4>
            </div>
            <div class="panel-body">
                <form method="POST" enctype="multipart/form-data" action="/transfer">
                    <div>
                        <p>
                            选择文件<input type="file" name="file"/>
                        </p>
                        <p>
                            <input type="submit" value="上传"/>
                        </p>
                        <p>
                            上传图片：<img src="https://ooo.0o0.ooo/2017/06/11/593c2c1d64882.jpg" class="img-responsive"/>
                            转换后：<img src="https://ooo.0o0.ooo/2017/06/11/593c2a4b4980f.jpg" class="img-responsive"/>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4 class="panel-title">
                    各路网友上传的图片与结果
                </h4>
            </div>
            <div class="panel-body">
                <table class="table table-bordered">
                    <tr>
                        <td><img src="https://i.loli.net/2017/07/19/596ebbe2b91c9.jpg" alt="171499830929_.pic.jpg"
                                 title="171499830929_.pic.jpg" class="img-responsive" width="400px"/></td>
                        <td><img src="https://i.loli.net/2017/07/19/596ebbe231226.png" alt="QQ截图20170719095236.png"
                                 title="QQ截图20170719095236.png" class="img-responsive" width="400px"/></td>
                    </tr>
                    <tr>
                        <td><img src="https://ooo.0o0.ooo/2017/07/19/596ebbf47bc01.png" alt="11.png" title="11.png"
                                 class="img-responsive" width="400px"/></td>
                        <td><img src="https://i.loli.net/2017/07/19/596ebbe1a5850.png" alt="1.png" title="1.png"
                                 class="img-responsive" width="400px"/></td>
                    </tr>
                    <tr>
                        <td><img src="https://i.loli.net/2017/07/19/596ebbe33b5b6.png" alt="4.png" title="4.png"
                                 class="img-responsive" width="400px"/></td>
                        <td>脑补</td>
                    </tr>

                </table>
            </div>
        </div>
    </div>

</div>
</body>

</html>