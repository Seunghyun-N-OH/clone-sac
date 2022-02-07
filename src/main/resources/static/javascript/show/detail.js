$(function(){
    $(".infoSelectors button").click(function(){
        var target = $(this).index();
        $(".infoSelectors button").removeClass("selected");
        $(this).addClass("selected");

        $(".selectedInfos > div").addClass("hidden");
        $(".selectedInfos > div").eq(target).removeClass("hidden");
    });
});