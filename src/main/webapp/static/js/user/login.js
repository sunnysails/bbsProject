/**
 * Created by sunny on 2016/12/17.
 */
$(function () {
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
                        alert("登录成功");
                        window.location.href = "/home"
                    } else {
                        alert(data.message)
                    }
                },
                error: function () {
                    alert("服务器错误")
                },
                complete: function () {
                    $("#loginBtn").text("登录").removeAttr("disable");
                }
            });
        }
    });
});