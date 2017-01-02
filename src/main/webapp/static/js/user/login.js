$(function () {

    function getParameterByName(name, url) {
        if (!url) {
            url = window.location.href;
        }
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    $("#loginBtn").click(function () {
        $("#loginForm").submit;
    });

    $("#loginForm").validate({
        errorElment: "span",
        errorClass: "text-error",
        rules: {
            userName: {
                required: true
            },
            passWord: {
                required: true
            },
            validatecode:{
                required: true
            }
        },
        messages: {
            userName: {
                required: "请输入账号"
            },
            passWord: {
                required: "请输入密码"
            },
            validatecode:{
                required: "请输入验证码"
            }
        },
        errorPlacement: function(error, element) { //错误信息位置设置方法
            error.appendTo( element.parent()); //这里的element是录入数据的对象
        },

        submitHandler: function (form) {
            $.ajax({
                url: "/login",
                type: "post",
                data: $(form).serialize(),
                beforeSend: function () {
                    $("#loginBtn").text("登录中...").attr("disable", "disable")
                },
                success: function (data) {
                    if (data.state == 'success') {
                        swal({
                                title: "登录成功",
                                text: "欢迎您!",
                                type: "success",
                                timer: 2000,
                                showConfirmButton: false,
                                showCancelButton: false,
                                closeOnConfirm: false
                            },
                            function () {
                                var url = getParameterByName("redirect");
                                if (url) {
                                    window.location.href = url;
                                } else {
                                    window.location.href = "/home";
                                }
                            });
                    } else {
                        swal(data.message, "", "error");
                    }
                },
                error: function () {
                    swal("服务器错误", "", "error")
                },
                complete: function () {
                    $("#loginBtn").text("登录").removeAttr("disable");
                }
            });
        }
    });
});