$(function () {
    $(".updateNode").click(function () {
        var nodeId = $(this).attr("rel");
        swal({
            title: "请输入新节点名称!",
            type: "input",
            showCancelButton: true,
            cancelButtonText: "取消",
            closeOnConfirm: false,
            animation: "slide-from-top",
            confirmButtonText: "确定",
            inputPlaceholder: "输入新节点"
        }, function (inputValue) {
            if (inputValue === false) return false;
            if (inputValue === "") {
                swal.showInputError("节点名称不能为空");
                return false
            }
            swal({
                title: "新节点名称：" + inputValue,
                text: "确定要建立或更改节点名称?",
                showConfirmButton: true,
                confirmButtonText: "确定",
                showCancelButton: true,
                cancelButtonText: "取消",
                closeOnConfirm: false
            }, function () {
                $.ajax({
                    url: "/admin/addnode",
                    type: "post",
                    data: {"inputValue": inputValue,"nodeId":nodeId},
                    success: function (data) {
                        if (data.state == 'success') {
                            swal({
                                title: "更新节点名称成功!",
                                type: "success",
                                timer: 1000,
                                showConfirmButton: false,
                                showCancelButton: false,
                                closeOnConfirm: false
                            }, function () {
                                window.history.go(0);
                            });
                        } else {
                            swal("更新失败", data.message, "error");
                        }
                    },
                    error: function () {
                        swal("服务器异常,更新失败!", "", "error");
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
            cancelButtonText: "取消",
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            closeOnConfirm: false
        }, function () {
            $.ajax({
                url: "/admin/nodedel",
                type: "post",
                data: {"id": id},
                success: function (data) {
                    if (data.state == 'success') {
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