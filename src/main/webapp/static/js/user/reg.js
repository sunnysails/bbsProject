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
                minlength: "帐号至少需要3个字符",
                remote:"该帐号已被注册，不可用"
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
                email: "请输入有效的电子邮件地址",
                remote: "电子邮件已被占用"
            },
            phone: {
                required: "请输入11位电话号码",
                rangelength: "手机号码格式错误",
                digits: "手机号码中不能包含非数字内容"
            }
        },
        submitHandler: function (form) {
            $.ajax({
                url: "/reg",
                type: "post",
                data: $(form).serialize(),
                beforeSend: function () {
                    $("#regBtn").text("注册中...").attr("disabled", "disabled")
                },
                success: function (data) {
                    if (data.state == 'success') {
                        swal({
                                title: "注册成功",
                                text: "请到邮箱查收您的激活邮件!",
                                type: "success",
                                showCancelButton: false,
                                confirmButtonColor: "#159492",
                                confirmButtonText: "OK!",
                                closeOnConfirm: false
                            },
                            function () {
                                window.location.href = "/login";
                            });
                    } else {
                        swal(data.message, "", "error");
                    }
                },
                error: function () {
                    swal("服务器错误", "", "error");
                },
                complete: function () {
                    $("#regBtn").text("注册").removeAttr("disable");
                }
            });
        }
    });
});