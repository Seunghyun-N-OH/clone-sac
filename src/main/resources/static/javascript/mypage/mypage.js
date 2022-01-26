

function toEditPage(){
    var curP = $("#pw").val();
    if(curP == ""){
        alert("비밀번호를 입력해주세요.");
        return false;
    }
    $("#toEditForm").submit();
}

function submitEdit(){
    var a = $("#sms-yes").is(":checked");
    if(a){
        $("#real-sms-yes").val('y');
    } else {
        $("#real-sms-yes").val('n');
    }
    a = $("#mail-yes").is(":checked");
    if(a){
        $("#real-mail-yes").val('y');
    } else {
        $("#real-mail-yes").val('n');
    }
    $("#phone").val($("#phone1").val()+$("#phone2").val()+$("#phone3").val());
    
    $("#joinBox").submit();
}