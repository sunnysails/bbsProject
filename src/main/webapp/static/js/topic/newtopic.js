/**
 * Created by sunny on 2016/12/21.
 */
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
                url: "/newtopic",
                type: "post",
                data: $(form).serialize(),
                beforeSend: function () {
                    $("#sendBtn").text("正在提交请稍候").attr("disabled", "disabled");
                },
                success: function (json) {
                    if (json.state == "success") {
                        window.location.href = "/topicdetail?topicId=" + json.data.id;
                    } else {
                        swal("新增主题异常", "", "error");
                    }
                },
                error: function () {
                    swal("服务器异常", "", "error");
                },
                complete: function () {
                    $("#sendBtn").text("发布主题").removeAttr("disabled");
                }
            });
        }
    });
});