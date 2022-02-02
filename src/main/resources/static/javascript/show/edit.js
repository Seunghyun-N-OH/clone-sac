$(function(){
    initSelectors();
    $("#hours").change(runningTimeCalc);
    $("#minutes").change(runningTimeCalc);
});

// TODO 셀렉터 변경감지 함수작성..

function initSelectors(){
    var hour = parseInt($("#rt").val())/60;
    $("#hours").val(hour).attr("selected","true");
    var minute = parseInt($("#rt").val())-(hour*60);
    $("#minutes").val(minute).attr("selected","true");
}

function addPriceLine(){
    var target = document.getElementById("priceBlock");
    var fakeBox = document.createElement('span');
    fakeBox.innerHTML="<br><input type='tex'' name='subject' class='half-length' placeholder='대상 (연령 또는 자격기준)'><input type='text' name='price' style='margin-left:1%' class='half-length' placeholder='판매가격'>";
    target.appendChild(fakeBox);
}

function addHostLine(){
    var target = document.getElementById("hostLine");
    var fakeBox = document.createElement('span');
    fakeBox.innerHTML="<br><input type='text' name='host' class='half-length' placeholder='주관 단체(기업 명)'>";
    target.appendChild(fakeBox);
}

function addOrganizerLine(){
    var target = document.getElementById("organizerLine");
    var fakeBox = document.createElement('span');
    fakeBox.innerHTML="<br><input type='text' name='organizer' class='half-length' placeholder='주최 단체(기업 명)'>";
    target.appendChild(fakeBox);
}

function addSponsorLine(){
    var target = document.getElementById("sponsorLine");
    var fakeBox = document.createElement('span');
    fakeBox.innerHTML="<br><input type='text' name='sponsor' class='half-length' placeholder='후원인, 단체, 기업 명'>";
    target.appendChild(fakeBox);
}

function addEventTimeLine(){
    var target = document.getElementById("eventTimeLine");
    var fakeBox = document.createElement('span');
    fakeBox.innerHTML="<br><input type='datetime-local' name='eventTime'>";
    target.appendChild(fakeBox);
}

function submitCheck(){
    if(!eGroupCheck()) return false;
    if(!isVenueSelected()) return false;
    if(!isPlannerSelected()) return false;
    if(!eventTitleCheck()) return false;
    if(!requiredAgeCheck()) return false;
    if(!onSaleCheck()) return false;
    if(!pricePolicyCheck()) return false;
    if(!contactCheck()) return false;
    
    $("#eventForm").submit();
}

function eGroupCheck(){
    if(!($("#show").is(":checked")) && !($("#exhibition").is(":checked"))){
        alert("행사 구분을 선택해주세요.");
        $("#show").focus();
        return false;
    } else {
        return true;
    }
}

function isVenueSelected(){
    if($("#venue1").val()==""){
        alert("행사 장소를 선택해주세요.");
        $("#venue1").focus();
        return false;
    } else {
        return true;
    }
}

function isPlannerSelected(){
    if($("#sacPlanned").val()=='o'){
        alert("예술의전당 기획여부를 선택해주세요.");
        $("#sacPlanned").focus();
        return false;
    } else {
        return true;
    }
}

function eventTitleCheck(){
    if($.trim($("#eventTitle").val())==""){
        alert("행사 이름을 입력해주세요.");
        $("#eventTitle").focus();
        return false;
    } else {
        return true;
    }
}

function hostCheck(){
    if($.trim($("#host").val())=="") {
        alert("주관사를 최소 1개 이상 입력해주세요.");
        $("#host").focus();
        return false;
    } else {
        return true;
    }
}

function organizerCheck(){
    if($.trim($("#organizer").val())=="") {
        alert("주최사를 최소 1개 이상 입력해주세요.");
        $("#organizer").focus();
        return false;
    } else {
        return true;
    }
}

function sponsorCheck(){
    if($.trim($("#sponsor").val())=="") {
        alert("협력/후원/협차사를 최소 1개 이상 입력해주세요.");
        $("#sponsor").focus();
        return false;
    } else {
        return true;
    }
}

function requiredAgeCheck(){
    if($.trim($("#rAge").val())==""){
        alert("연령제한이 없을 경우, '연령제한 없음'이라고 입력해주세요.");
        $("#rAge").focus();
        return false;
    } else {
        return true;
    }
}

function onSaleCheck(){
    if($("#onSale").val()=='n'){
        alert("입장권 판매상태를 입력해주세요.");
        $("#onSale").focus();
        return false;
    } else {
        return true;
    }
}

function pricePolicyCheck(){
    if( $.trim($("#sub1").val())=="" || $.trim($("#pri1").val())=="" ){
        alert("가격정보를 1개이상 입력해주세요");
        $("#sub1").focus();
        return false;
    } else {
        return true;
    }
}

function contactCheck(){
    if($.trim($("#contact").val())==""){
        alert("행사에 대한 문의를 받아보실 연락처를 입력해주세요.");
        $("#contact").focus();
        return false;
    } else {
        return true;
    }
}

function runningTimeCalc(){
    var h = parseInt($("#hours").val());
    var m = parseInt($("#minutes").val());
    var sum = (h*60) + m;
    $("#rt").val(sum);
}

function setDeletePoster(a){
    $("#"+a).css("text-decoration", "line-through");
    var target = document.getElementById("deletePosterBox");
    var fakeBox = document.createElement('span');
    fakeBox.innerHTML="<input type='tex'' name='deletePoster' hidden value='yes'>";
    target.appendChild(fakeBox);
}

function setDeleteDetailImg(a){
    $("#"+a).css("text-decoration", "line-through");
    var target = document.getElementById("deleteDetailBox");
    var fakeBox = document.createElement('span');
    fakeBox.innerHTML="<input type='tex'' name='deleteDetails' hidden value='"+a+"'>";
    target.appendChild(fakeBox);
}

function doubleCheckDelete(){
    if(confirm("정말 삭제하시겠습니까?")){
        $("#delForm").submit();
    } else {
            return false;
        }
}