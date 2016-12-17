/**
 * Created by sunny on 2016/12/17.
 */
$(function () {
    $("#type").change(function () {
        var value = $(this).val();
        if ("email" == value){
            $("#typename").text("请输入电子邮件地址");
        }else{
            $("#typename").text("请输入手机号码");
        }
    });

    $("#btn").click(function () {
        $("#form").submit();
    });

    $("#form").validate({
        errorElement:'span',
        errorClass:'text-type',
        rules:{
            value:{
                required:true
            }
        },
        messages:{
            value: {
                required: "该项必填"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/foundpassword",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#btn").text("提交中...").attr("disable","disable");
                },
                success:function (data) {
                    if (data.state == "success"){
                        var type = $("#type").val();
                        if ("email" == type){
                            alert("请查收邮件");
                        }else{
                            //TODO 电话提示模块
                        }
                    }else{
                        alert(data.message);
                    }
                },
                error:function () {
                    alert("服务器错误，请稍后重试")
                },
                complete:function () {
                    $("#btn").text("提交").removeAttr("disable");
                }
            });
        }
    });
});