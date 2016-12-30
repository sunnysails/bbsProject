$(function () {
    $(".del").click(function () {
        var id = $(this).attr("rel");
        swal({
            title: "确定要删除该主题?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            closeOnConfirm: false
        }, function () {
            $.ajax({
                url: "/admin/topic",
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
                        swal("删除失败",data.message,"error");
                    }
                },
                error: function () {
                    swal("服务器异常,删除失败!", "", "error");
                }
            });
        });
    });
});