$(function() {
    initList();
});

function getTargetedList(a){
    $.ajax({
        type : "GET",
        url : "/sacnews/notice/target/"+a,
        success : function(r){
            $("#noticeLists").html(r);
        }
    })
}

function initList() {

    $.ajax({
        url : "/sacnews/notice/init",
        type : "get",
        success : function(ab){
            $("#noticeLists").html(ab);
        }
    });
}