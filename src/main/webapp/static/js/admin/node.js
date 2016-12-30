$(function () {
    $("#addNode").click(function () {
        swal({
            title: "请输入新节点!",
            text: "不能添加已经存在的节点",
            type: "input",
            showCancelButton: true,
            closeOnConfirm: false,
            animation: "slide-from-top",
            inputPlaceholder: "输入新节点"
        }, function (inputValue) {
            if (inputValue === false) return false;
            if (inputValue === "") {
                swal.showInputError("新节点不能为空");
                return false
            }
            swal({
                title: "新节点：" + inputValue,
                text: "确定要建立该节点?",
                showConfirmButton: true,
                showCancelButton: true,
                closeOnConfirm: false
            }, function () {
                $.ajax({
                    url: "/admin/addnode",
                    type: "post",
                    data: {"inputValue": inputValue},
                    success: function (data) {
                        if (data.state == 'success') {
                            swal({
                                title: "成功创建新节点!",
                                type: "success",
                                timer: 1000,
                                showConfirmButton: false,
                                showCancelButton: false,
                                closeOnConfirm: false
                            }, function () {
                                window.history.go(0);
                            });
                        } else {
                            swal("创建失败", data.message, "error");
                        }
                    },
                    error: function () {
                        swal("服务器异常,创建失败!", "", "error");
                    }
                });
            });
        });
    });


    $(".delNode").click(function () {
        var id = $(this).attr("rel");
        swal({
            title: "确定要删除该节点?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            closeOnConfirm: false
        }, function () {
            $.ajax({
                url: "/admin/nodedel",
                type: "post",
                data: {"id": id},
                success: function (data) {
                    if (data == 'success') {
                        swal({
                            title: "删除成功!",
                            type: "success",
                            timer: 1000,
                            showConfirmButton: false,
                            showCancelButton: false,
                            closeOnConfirm: false
                        }, function () {
                            window.history.go(0);
                        });
                    } else {
                        swal("删除失败", data.message, "error");
                    }
                },
                error: function () {
                    swal("服务器异常,删除失败!", "", "error");
                }
            });
        });
    });
});