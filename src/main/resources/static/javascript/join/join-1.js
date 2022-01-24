$(function() {
    $("#changeAll").change(changeAll);
  })

  function checkRadio(){
    // if((!document.getElementById("center-yes").checked &&!document.getElementById("center-no").checked) || (!document.getElementById("personal-yes").checked &&!document.getElementById("personal-no").checked) || (!document.getElementById("marketing-yes").checked &&!document.getElementById("marketing-no").checked)){alert("필수 동의여부를 모두 선택해주세요");}else{document.getElementById("toNextPage").submit();}
    if(!document.getElementById("center-yes").checked){
      alert("예술의전당 회원 약관에 동의 하여 주십시오");
      return false;
    } else if (!document.getElementById("personal-yes").checked){
      alert("개인정보 수집 및 이용안내에 동의 하여 주십시오");
      return false;
    } else if (!document.getElementById("marketing-yes").checked &&!document.getElementById("marketing-no").checked){
      alert("홍보 및 마케팅에 관한 동의를 선택 해 주세요");
      return false;
    } else {
      document.getElementById("toNextPage").submit();
    }
  }

  function changeAll(){
    if($("#changeAll").is(":checked")){
      $("#center-yes").prop("checked", true);
      $("#personal-yes").prop("checked", true);
      $("#marketing-yes").prop("checked", true);
    } else {
      $("#center-yes").prop("checked", false);
      $("#center-no").prop("checked", false);
      $("#personal-yes").prop("checked", false);
      $("#personal-no").prop("checked", false);
      $("#marketing-yes").prop("checked", false);
      $("#marketing-no").prop("checked", false);
    }
  }

  function uncheckAllBTN(){
    $("#changeAll").prop("checked", false);
  }