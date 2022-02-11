var pnRule = /^(?=.*[0-9]).{11}$/;    // 전화번호 rule : 11자리, 숫자만 입력가능
      var emptyCheck = /\s/;                // 공백 체크 rule
      var phoneNumberInput = document.getElementById("pno");

      $(function() {
        
        $("#sendSMSbtn").click(smscheck);
        $("#verifyBtn").click(verifyCheck);

      });

      function notAvailableAlert(){
        alert("사용할 수 없는 기능입니다.\n임시인증번호를 사용해 가입 진행이 가능합니다.");
      }

      function smscheck(){
        var phoneNumber = $('#pno').val();
        if (pnRule.test(phoneNumber) && !emptyCheck.test(phoneNumber)){} else {
          alert("공백, (-) 없이 번호만 입력해주세요.")
          return false;
        }
        $.ajax({
          type: "GET",
          url: "/member/signup/verification",
          data: {
          phoneNumber : phoneNumber,
        },
        success: function(res){
          alert("인증번호가 발송되었습니다.");
          document.getElementById("tmpVN").value=res;
          $("#pno").attr("disabled",true);
        },
        });
      }

      function verifyCheck(){


        if($("#tmpVN").val() == $("#codeTocheck").val()){
            alert("인증 성공");
          document.getElementById("toNextPage").submit();
        }else{
          alert("인증 실패");
        }
                  
      }