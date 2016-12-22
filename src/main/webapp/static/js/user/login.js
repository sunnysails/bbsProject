/**
 * Created by sunny on 2016/12/17.
 */
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
            }
        },
        messages: {
            userName: {
                required: "请输入账号"
            },
            passWord: {
                required: "请输入密码"
            }
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
                                showCancelButton: false,
                                confirmButtonColor: "#159492",
                                confirmButtonText: "OK!",
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