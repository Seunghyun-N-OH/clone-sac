$(function(){
})

function doubleCheckNDelete(){
    var r = confirm("이 공지사항을 삭제하시겠습니까");
	if(r){
        $("#deleteNotice").submit();
	} else {}
	
}