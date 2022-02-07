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