$(function() {
    initList();
});

function getGuideOnly(){
    $.ajax({
        type : "GET",
        url : "/sacnews/notice",
        data : {
            cat : "guide",
        },
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