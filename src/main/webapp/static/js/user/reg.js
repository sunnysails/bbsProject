/**
 * Created by sunny on 2016/12/15.
 */
$(function () {
    $("#regBtn").click(function () {
        $("#regForm").submit();
    });
    $("#regForm").validate({
        errorElement: 'span',
        errorClass: 'text-error',
        rules: {
            userName: {
                required: true,
                minlength: 3,
                remote: "/validate/user"
            },
            passWord: {
                required: true,
                rangelength: [6, 18]
            },
            dpassWord: {
                required: true,
                rangelength: [6, 18],
                equalTo: "#passWord"
            },
            email: {
                required: true,
                email: true,
                remote: "/validate/email"
            },
            phone: {
                required: true,
                rangelength: [11, 11],
                digits: true
            }
        },
        messages: {
            userName: {
                required: "请输入账号",
                minlength: "帐号至少需要3个字符"
            },
            passWord: {
                required: "请输入密码",
                rangelength: "密码长度在6——18位之间"
            },
            dpassWord: {
                required: "请重复输入密码，两次密码必须保持一致",
                rangelength: "密码长度在6——18位之间",
                equalTo: "两次输入的密码不一致"
            },
            email: {
                required: "请输入电子邮件",
                email: "请输入有效的电子邮件地址"
            },
            phone: {
                required: "请输入11位电话号码",
                rangelength: "手机号码格式错误",
                digits: "手机号码中不能包含非数字内容"
            }
        },
        submitHandler: function () {
            $.ajax({
                url: "/reg",
                type: "post",
                data: $("#regForm").serialize(),
                beforeSend: function () {
                    $("#regBtn").text("注册中...").attr("disabled", "disabled")
                },
                success: function () {
                    if (data.state == 'success') {
                        alert("注册成功，请查收激活邮件。");
                        window.location.href = "/login";
                    } else {
                        alert(data.massage);
                    }
                },
                error: function () {
                    alert("服务器错误");
                },
                complete: function () {
                    $("#regBtn").text("注册").removeAttr("disable");
                }
            });
        }
    });
});