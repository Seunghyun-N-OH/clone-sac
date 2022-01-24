function showToggleMenu() {
    $("#menuBtn2").removeClass("hidden");
    $("#menuBtn").addClass("hidden");
    $("#toggleMenu").slideDown(200);
    // document.getElementById("menuBtn2").classList.remove("hidden");
    // document.getElementById("menuBtn").classList.add("hidden");
    // document.getElementById("toggleMenu").classList.remove("hidden");
  }

  function hideToggleMenu() {
    $("#menuBtn").removeClass("hidden");
    $("#menuBtn2").addClass("hidden");
    $("#toggleMenu").slideUp(200);
    // document.getElementById("menuBtn").classList.remove("hidden");
    // document.getElementById("menuBtn2").classList.add("hidden");
    // document.getElementById("toggleMenu").classList.add("hidden");
  }