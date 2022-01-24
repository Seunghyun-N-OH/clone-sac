$(function () {
    $("#free-park-btn").click(function () {
      buttonPreset($(this));
      document.getElementById("free-park").classList.remove("hidden");
    });

    $("#rehearsal-btn").click(function () {
      buttonPreset($(this));
      document.getElementById("rehearsal").classList.remove("hidden");
    });

    $("#tour-btn").click(function () {
      buttonPreset($(this));
      document.getElementById("tour").classList.remove("hidden");
    });

    $("#events-btn").click(function () {
      buttonPreset($(this));
      document.getElementById("events").classList.remove("hidden");
    });

    $("#booking-btn").click(function () {
      buttonPreset($(this));
      document.getElementById("booking").classList.remove("hidden");
    });

    $("#specialoffers-btn").click(function () {
      buttonPreset($(this));
      document.getElementById("specialoffers").classList.remove("hidden");
    });

    function buttonPreset(v){
      $(".vingt").addClass("theme-non");
      $(".vingt").removeClass("theme-selected");
      $(v).removeClass("theme-non");
      $(v).addClass("theme-selected");
      $(".theme-detail").addClass("hidden");
    }
  });