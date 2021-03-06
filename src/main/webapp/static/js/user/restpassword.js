/**
 * Created by sunny on 2016/12/18.
 */
$(function () {
    $("#restBtn").click(function () {
        $("#restForm").submit();
    });

    $("#restForm").validate({
        errorElement: 'span',
        errorClass: 'text-error',
        rules: {
            passWord: {
                required: true,
                rangelength: [6, 18]
            },
            rePassWord: {
                required: true,
                rangelength: [6, 18],
                equalTo: "#passWord"
            }
        },
        messages: {
            passWord: {
                required: "请输入密码",
                rangelength: "密码长度6-18个字符"
            },
            rePassWord: {
                required: "请输入确认密码",
                rangelength: "密码长度6-18个字符",
                equalTo: "两次密码不一致"
            }
        },
        submitHandler: function (form) {
            $.ajax({
                url: "/user/restpassword",
                type: "post",
                data: $(form).serialize(),
                beforeSend: function () {
                    $("#restBtn").text("保存中...").attr("disabled", "disabled");
                },
                success: function (data) {
                    if (data.state == 'success') {
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
                    $("#restBtn").text("保存").removeAttr("disabled");
                }
            });
        }
    });
});
