$(function () {
    $(".over25").mouseover(putShadow);
    $(".over25").mouseleave(removeShadow);
    $(".under25").mouseover(putShadow);
    $(".under25").mouseleave(removeShadow);
    $(".over70").mouseover(putShadow);
    $(".over70").mouseleave(removeShadow);
    $(".corpMember").mouseover(putShadow);
    $(".corpMember").mouseleave(removeShadow);
    $(".goldMember").mouseover(putShadow);
    $(".goldMember").mouseleave(removeShadow);
    $(".blueMember").mouseover(putShadow);
    $(".blueMember").mouseleave(removeShadow);
    $(".greenMember").mouseover(putShadow);
    $(".greenMember").mouseleave(removeShadow);
  });

  function putShadow() {
    $(this).css("box-shadow", "0 0 10px #bdbbbb");
  }

  function removeShadow() {
    $(this).css("box-shadow", "none");
  }