function confirmResignation(){
    window.open("/member/mypage/resignation", "예술의전당 회원 탈퇴","width=400, height=200, history=no, resizable=no, status=no, scrollbars=no");
    
};

function doublecheck(){
    if(confirm("정말로 탈퇴하시겠습니까")){
        $("#resignForm").submit();
    } else {
        return false;
    }
}