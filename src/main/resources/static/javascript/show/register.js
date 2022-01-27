$(function(){
})

// TODO 셀렉터 변경감지 함수작성..

function initSelectors(){
    $("#venue2").find("option[value!=0]").detach();
    $("#venue3").find("option[value!=0]").detach();
}

function setVenue2(){
    var a = $("#venue2").find("option[value!=0]");
    $("#venue2").append(a.filter(".b" + $(this).val()));
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