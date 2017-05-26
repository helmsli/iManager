/**
 * Created by sunhui on 2016/9/12.
 */
function openModal() {
    /*显示摸态框*/
    $('#creatModal').modal('show');
    setTimeout(function () {
        /*隐藏摸态框*/
        $('#creatModal').modal('hide');
    }, 1000);
    /*手动切换摸态框*/
    /*$('#identifier').modal('toggle')}*/
}


$(function () {
    $('#creatModal').on('show.bs.modal', function () {
        console.log("在调用 show 方法后触发");
    });

    $('#creatModal').on('shown.bs.modal', function () {
        console.log("当模态框对用户可见时触发");
    });

    $('#creatModal').on('hide.bs.modal', function () {
        console.log("当调用 hide 实例方法时触发");
    });

    $('#creatModal').on('hidden.bs.modal', function () {
        console.log("当模态框完全对用户隐藏时触发");
    });
});
