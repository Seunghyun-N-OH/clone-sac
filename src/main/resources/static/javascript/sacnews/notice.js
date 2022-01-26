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

function searchNotice(){
    var target = $("#categorySelector").val();
    var word = $("#keyword").val();

    if(word.trim() == ""){
        alert("검색어를 입력해주세요.");
        return false;
    }

    $.ajax({
        type : "GET",
        url : "/sacnews/notice/search?target="+target+"&key="+word,
        success : function(a){
            $("#noticeLists").html(a);
        }
    })
}