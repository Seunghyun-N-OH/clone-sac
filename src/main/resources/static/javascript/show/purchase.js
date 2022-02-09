$(function(){

    $(".numberUp").click(function(){
        $(this).prev().val(parseInt(parseInt($(this).prev().val())+1));
        sum($(this));
    });
});

function sum(a){
    alert(parseInt(a.siblings("input.visitors").val()));
    alert(parseInt(a.siblings("input.ticketprice").val()));
    var total = parseInt(a.siblings("input.visitors").val()) * parseInt(a.siblings("input.ticketprice").val());
    $("#totalPrice").text(total);
}