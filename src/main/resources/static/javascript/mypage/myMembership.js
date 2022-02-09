$(function(){
    $(".classSelector button").click(function(){
        $(".classSelector button").removeClass("c");
        $(this).addClass("c");

        $(".expirySelect").addClass("hidden");
        $(".expirySelect").eq($(this).index()).removeClass("hidden");
    });

    $(".expirySelect button").click(function(){
        $(".expirySelect button").removeClass("c");
        $(this).addClass("c");
    });
});

// iamport ##########################################################

function getMerchantId(mClass, fee, product_id, userId, period){
    $.ajax({
      type : "POST",
      url : "/member/premium/initPurchase",
      data : {
        "memberClass" : mClass,
        "product_id" : product_id,
        "user" : userId,
      },
      success : function(a) {
        payMembershipFee_card(mClass, fee, a, userId, period);
      }
    });

  };

  function payMembershipFee_card(mClass, fee, mId, userId, period){

    var IMP = window.IMP; // 생략 가능
    IMP.init("imp23270551"); // 예: imp00000000
    IMP.request_pay({
      pg : "html5_inicis",
      pay_method : "card",
      merchant_uid : mId,
      name : mClass,
      amount : fee,
    }, function (result){
      if(result.success){
        $.ajax({
          url : "/member/premium/success",
          method: "POST",
          data : {
            merchantUid : result.merchant_uid,
            payMethod : result.pay_method,
            paidAmount : result.paid_amount,
            name : result.name,
            buyerUserId : userId,
            buyerEmail : result.buyer_email,
            period : period,
          },
          success : function(a){
              alert("정상적으로 가입되었습니다.\n등급 적용을 위해 다시 로그인해주세요.")
            location.href=a;
          }
        });
        alert("result posted");
      } else {
        alert("결제에 실패했습니다.\n"+result.error_msg);
      }
    });
  }