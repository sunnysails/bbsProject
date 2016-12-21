/**
 * Created by sunny on 2016/12/17.
 */
$(function () {
    $("#type").change(function () {
        var value = $(this).val();
        if ("email" == value) {
            $("#typename").text("请输入电子邮件地址");
        } else {
            $("#typename").text("请输入手机号码");
        }
    });

    $("#btn").click(function () {
        $("#form").submit();
    });

    $("#form").validate({
        errorElement: 'span',
        errorClass: 'text-type',
        rules: {
            value: {
                required: true
            }
        },
        messages: {
            value: {
                required: "该项必填"
            }
        },
        submitHandler: function (form) {
            $.ajax({
                url: "/foundpassword",
                type: "post",
                data: $(form).serialize(),
                beforeSend: function () {
                    $("#btn").text("提交中...").attr("disable", "disable");
                },
                success: function (data) {
                    if (data.state == "success") {
                        var type = $("#type").val();
                        if ("email" == type) {
                            swal("邮件发送成功!", "请到邮箱查收您的验证邮件!", "success");
                        } else {
                            //TODO 电话提示模块
                        }
                    } else {
                        swal(data.message, "error");
                    }
                },
                error: function () {
                    swal("服务器错误，请稍后重试", "error")
                },
                complete: function () {
                    $("#btn").text("提交").removeAttr("disable");
                }
            });
        }
    });
});