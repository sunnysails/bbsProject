$(function () {
    $("#sendBtn").click(function () {
        $("#topicForm").submit();
    });

    $("#topicForm").validate({
        errorElement: "span",
        errorClass: "text-error",
        rules: {
            title: {
                required: true
            },
            nodeId: {
                required: true
            }
        },
        messages: {
            title: {
                required: "请输入标题"
            },
            nodeId: {
                required: "请选择节点"
            }
        },
        submitHandler: function (form) {
            $.ajax({
                url: "/topicedit",
                type: "post",
                data: $(form).serialize(),
                beforeSend: function () {
                    $("#sendBtn").text("正在提交修改请稍候").attr("disabled", "disabled");
                },
                success: function (json) {
                    if (json.state == "success") {
                        window.location.href = "/topicdetail?topicId=" + json.data;
                    } else {
                        swal("修改主题异常", "", "error");
                    }
                },
                error: function () {
                    swal("服务器异常", "", "error");
                },
                complete: function () {
                    $("#sendBtn").text("确认修改").removeAttr("disabled");
                }
            });
        }
    });
});