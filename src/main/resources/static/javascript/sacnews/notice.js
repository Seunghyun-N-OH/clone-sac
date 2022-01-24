$(function() {

});

function getGuideOnly(){
    $.ajax({
        type : "GET",
        url : "/sacnews/notice",
        data : {
            cat : "guide",
        },
        success : function(r){
            
        }
    })
}