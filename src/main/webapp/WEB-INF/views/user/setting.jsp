<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/19
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>设置</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/static/js/uploader/webuploader.css">
    <link rel="stylesheet" href="/static/js/dist/sweetalert.css">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>

<%@include file="../include/navbar.jsp" %>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-cog"></i> 基本设置</span>
        </div>

        <form id="basicForm" action="" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" value="${sessionScope.curr_user.userName}" readonly>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">电子邮件</label>
                <div class="controls">
                    <input type="text" name="email" value="${sessionScope.curr_user.email}">
                </div>
            </div>
            <div class="form-actions">
                <button id="basicBtn" class="btn btn-primary">保存</button>
            </div>
        </form>
    </div>
    <!--box end-->
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-key"></i> 密码设置</span>
            <span class="pull-right muted" style="font-size: 14px">如果你不打算更改密码，请留空以下区域</span>
        </div>

        <form id="setPSForm" action="" class="form-horizontal">

            <div class="control-group">
                <label class="control-label">旧密码</label>
                <div class="controls">
                    <input type="password" name="oldPassWord">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">新密码</label>
                <div class="controls">
                    <input type="password" name="newPassWord" id="newPassWord">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">重复密码</label>
                <div class="controls">
                    <input type="password" name="reNewPassWord" id="reNewPassWord">
                </div>
            </div>
            <div class="form-actions">
                <button id="setPSBtn" class="btn btn-primary">保存</button>
            </div>
        </form>
    </div>
    <!--box end-->
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-user"></i> 头像设置</span>
        </div>

        <form action="" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">当前头像</label>
                <div class="controls">
                    <img id="avatar"
                         src="http://oi245j9g2.bkt.clouddn.com/${sessionScope.curr_user.avatar}?imageView2/1/w/80/h/80"
                         class="img-circle" alt="">
                </div>
            </div>
            <hr>
            <p style="padding-left: 20px">关于头像的规则</p>
            <ul>
                <li>禁止使用任何低俗或者敏感图片作为头像</li>
                <li>如果你是男的，请不要用女人的照片作为头像，这样可能会对其他会员产生误导</li>
            </ul>
            <div class="form-actions">
                <div id="picker">上传新头像</div>
            </div>
        </form>
    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="/static/js/uploader/webuploader.min.js"></script>
<script src="/static/js/dist/sweetalert-dev.js"></script>
<script>
    $(function () {
        //头像上传
        var uploder = WebUploader.create({
            swf: "/static/js/uploader/Uploader.swf",
            server: "http://up-z1.qiniu.com",
            pick: '#picker',
            auto: true,
            fileVal: 'file',
            formData: {"token": "${token}"},
//            accept: {
//                title: 'Images',
//                extensions: 'gif,jpg,jpeg,bmp,png',
//                mimeTypes: 'image/!*'
//            }
        });
        //上传成功
        uploder.on('uploadSuccess', function (file, data) {
            var fileKey = data.key;
            //修改数据库中的值
            $.post("/setting?action=avatar", {'fileKey': fileKey})
                .done(function (data) {
                    if (data.state == 'success') {
                        var url = "http://oi245j9g2.bkt.clouddn.com/" + fileKey;
                        $("#avatar").attr("src", url + "?imageView2/1/w/80/h/80");
                        $("#navbar_avatar").attr("src", url + "?imageView2/1/w/20/h/20");
                    }
                })
                .error(function () {
                    swal("头像设置失败", "", "error");
                });
        });
        //上传失败
        uploder.on('uploadError', function () {
            swal("上传失败，请稍候再试", "", "error");
        });
    });
</script>
<script src="/static/js/user/setting.js"></script>
</body>
</html>