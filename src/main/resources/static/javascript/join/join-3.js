var pwRule = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{10,20}$/;     //  10~20자, 대문자,소문자,숫자,특수문자 최소 1개 포함
        var idRule = /^(?=.*[a-z])(?=.*[0-9]).{8,16}$/;        //  8~16자, 대문자불가, 숫자 포함 가능
        var mailRule = /^[a-z0-9_+.-]+@([a-z0-9-]+\.)+[a-z0-9]{2,4}$/;
        var phoneRule = /^(?=.*[0-9]).{4}$/;
        var whiteSpaceCheck = /\s/;

        $(function(){
            $("#userpwInput").focusout(pwRuleCheck);
            $("#userpwInput-doublecheck").focusout(pwDoubleCheck);
            $("#findPostal").click(startDaumAPI);
            $("#postal").click(startDaumAPI);
            $("#address1").click(startDaumAPI);
        })

        function nextPageCheck(){
            if(!genderCheck()){
                return false;
            }
            if(!checkIdAvailability()){
                alert("아이디를 다시 확인 후 중복확인을 해주세요");
                return false;
            }
            if(!pwRuleCheck()){
                alert("필수입력정보 : 비밀번호를 정확하게 입력해주세요.\n10~20자, 대문자,소문자,숫자,특수문자 최소 각 1개 포함");
                return false;
            }
            if(!pwDoubleCheck()){
                alert("필수입력정보 : 비밀번호 확인을 정확하게 입력해주세요.");
                return false;
            }
            if($("#postal").val() == null){
                alert("필수입력정보 : 주소를 입력해 주세요.");
                return false;
            }
            if(!phoneNumberCheck()){
                alert("필수입력정보 : 연락처를 정확하게 입력해주세요.");
                return false;
            }
            if(!emailRuleCheck()){
                alert("필수입력정보 : 이메일을 정확하게 입력해주세요.");
                return false;
            }
            if(!feeCheck()) return false;
            if(!$("#mail-yes").is(":checked")){
                $("#mail-no").prop("checked", true);
            }
            if(!$("#sms-yes").is(":checked")){
                $("#sms-no").prop("checked", true);
            }
            $("#joinBox").submit();
            // document.getElementById("joinBox").submit();

        }

        function feeCheck(){
            if($("#fee > option#defaultSelect:selected").val()){
                alert("가입하시는 회원등급의 회비를 확인 후 선택해주세요.");
                return false;
            } else {
                return true;
            }
        }

    function emailRuleCheck(){
        var emailInput = $("#email").val();
        if(mailRule.test(emailInput)){
            $("emailCheckMSG").text("");
            return true;
        } else {
            $("emailCheckMSG").text("이메일을 정확하게 입력해주세요");
            return false;
        }
    }

    function phoneNumberCheck(){
        var phone2 = $("#phone2").val();
        var phone3 = $("#phone3").val();
        if(!whiteSpaceCheck.test(phone2) && !whiteSpaceCheck.test(phone3)){
            // 연락처 가운데, 마지막 번호에 공백이 없을 경우
            if(phoneRule.test(phone2)&&phoneRule.test(phone3)){
                $("#phoneCheckMSG").text("");
                return true;
            } else {
                $("#phoneCheckMSG").css("color", "red");
                $("#phoneCheckMSG").text("연락처를 정확하게 입력해주세요.");
                return false;
                // 모든 사람들의 연락처가 반드시 010-XXXX-XXXX 자릿수라는 가정
            }
        } else {
            $("#phoneCheckMSG").css("color", "red");
            $("#phoneCheckMSG").text("연락처를 공백없이 입력해주세요.");
            return false;
        }
    }

    function startDaumAPI(){
            // opens new window when cliking "주소 찾기" using Kakao API
            new daum.Postcode({
                oncomplete: function(data) { // returns Object 'data' that includes all info. of the selected address
                    // pick & get specific info. from 'data', and put them in proper element for each
                    document.getElementById("postal").value=data.zonecode; // zip code 
                    document.getElementById("address1").value = data.address + " (" + data.buildingName + ")";
                    // address + ( building name )
                    document.getElementById("address2").focus();
                    // focus to input box which visitors should type in by themselves
                }
            }).open();
        }

    function pwDoubleCheck(){
        var curPW = $("#userpwInput").val();
        var doubleCheck = $("#userpwInput-doublecheck").val();
        if(curPW == doubleCheck){
            $("#doublecheckMSG").text("");
            return true;
        } else {
            $("#doublecheckMSG").css("color", "red");
            $("#doublecheckMSG").text("비밀번호가 일치하지 않습니다.");
            return false;
        }
    }

    function pwRuleCheck(){
        var curPW = $("#userpwInput").val();
        if(pwRule.test(curPW) && !whiteSpaceCheck.test(curPW)){
            $("#pwLine").css("height" , "4rem");
            $("#pwName").css("height" , "4rem");
            $("#pwValue").css("height" , "4rem");
            $("#pwCheckResult").hide();
            return true;
        } else {
            $("#pwLine").css("height" , "5rem");
            $("#pwName").css("height" , "5rem");
            $("#pwValue").css("height" , "5rem");
            $("#pwCheckResult").show();
            $("#pwCheckResult").css("color", "red");
            $("#pwCheckResult").text("사용하실 수 없는 비밀번호입니다.");
            return false;
        }
    }

    function genderCheck(){
        if(!document.getElementById("male").checked &&!document.getElementById("female").checked){
            alert("성별을 선택해주세요.");
            return false;
        } else {
            return true;
        }
    }

    function checkIdAvailability(){
        var curId = $("#useridInput").val();
        if(idRule.test(curId) && !whiteSpaceCheck.test(curId)){
            // 유효한 아이디, 공백미포함+규칙준수
            $("#idLine").css("height" , "5rem");
            $("#idName").css("height" , "5rem");
            $("#idValue").css("height" , "5rem");
            $("#idCheckResult").css("color", "green");
            $("#idCheckResult").show();
            $("#idCheckResult").text("사용할 수 있는 아이디입니다.");
        } else {
            $("#idLine").css("height" , "5rem");
            $("#idName").css("height" , "5rem");
            $("#idValue").css("height" , "5rem");
            $("#idCheckResult").css("color", "red");
            $("#idCheckResult").show();
            $("#idCheckResult").text("사용할 수 없는 아이디형식입니다. 8~16자, 영소문자,숫자 만 입력가능");
            return false;
        }

        $.ajax({
            type : "GET",
            url : "/member/signup/checkidavailability",
            data : {
                tempID : curId,
            },
            success : function(res){
                $("#idLine").css("height" , "5rem");
                $("#idName").css("height" , "5rem");
                $("#idValue").css("height" , "5rem");
                if(res){      // returns true if ID is available
                    $("#idCheckResult").show();
                    $("#idCheckResult").css("color", "green");
                    $("#idCheckResult").text("사용 가능한 아이디입니다.");
                    return true;
                } else {                // if not, returns false
                    $("#idCheckResult").show();
                    $("#idCheckResult").css("color", "red");
                    $("#idCheckResult").text("이미 사용중인 아이디입니다.");
                    return false;
                }
            }
        })


    }