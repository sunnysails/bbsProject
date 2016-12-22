/**
 * Created by sunny on 2016/12/19.
 */
$(function () {
    //邮件地址修改
    $("#basicBtn").click(function () {
        $("#basicForm").submit();
    });

    $("#basicForm").validate({
        errorElement: 'span',
        errorClass: 'text-error',
        rules: {
            email: {
                required: true,
                email: true,
                remote: '/validate/email?type=1'
            }
        },
        messages: {
            email: {
                required: "请输入电子邮件",
                email: "电子邮件格式错误",
                remote: '电子邮件已被占用'
            }
        },
        submitHandler: function (form) {
            $.ajax({
                url: "/setting?action=profile",
                type: "post",
                data: $(form).serialize(),
                beforeSend: function () {
                    $("#basicBtn").text("保存中...").attr("disabled", "disabled");
                },
                success: function (data) {
                    if (data.state == "success") {
                        swal("修改成功!", "", "success");
                    }
                },
                error: function () {
                    swal("服务器错误", "", "error");
                },
                complete: function () {
                    $("#basicBtn").text("保存").removeAttr("disabled");
                }
            });
        }
    });

    //密码修改
    $("#setPSBtn").click(function () {
        $("#setPSForm").submit();
    });

    $("#setPSForm").validate({
        errorClass: "text-error",
        errorElement: "span",
        rules: {
            oldPassWord: {
                required: true,
                rangelength: [6, 18]
            },
            newPassWord: {
                required: true,
                rangelength: [6, 18]
            },
            reNewPassWord: {
                required: true,
                rangelength: [6, 18],
                equalTo: "#newPassWord"
            }
        },
        messages: {
            oldPassWord: {
                required: "请输入原始密码",
                rangelength: "密码长度6-18个字符"
            },
            newPassWord: {
                required: "请输入新密码",
                rangelength: "密码长度6-18个字符"
            },
            reNewPassWord: {
                required: "请输入确认密码",
                rangelength: "密码长度6-18个字符",
                equalTo: "两次密码不一致"
            }
        },
        submitHandler: function (form) {
            $.ajax({
                url: "/setting?action=password",
                type: "post",
                data: $(form).serialize(),
                beforeSend: function () {
                    $("#setPSBtn").text("保存中...").attr("disabled", "disabled");
                },
                success: function (data) {
                    if (data.state == "success") {
                        swal({
                                title: "密码重置成功,请重新登录",
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
                    $("#setPSBtn").text("保存").removeAttr("disabled");
                }
            });
        }
    });
});
