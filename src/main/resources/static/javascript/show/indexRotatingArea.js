  var idx = 0;
  
  function toRight(){
    
    console.log(idx);

    idx++;
    $(".gallery ul").animate({
      "left":-95*idx+"px"
    },300);

    if(idx> $(".gallery ul li").length-6){
       $(".gallery ul").animate({
         "left":0
       },0);

       idx=0;
    }
    console.log(idx);
  }
   
  function toLeft(){
    
    console.log(idx);

    idx--;
    $(".gallery ul").animate({
      "left":-95*(idx)+"px"
    },300);


    if(idx < 0){
       $(".gallery ul").animate({
        "left":-95*($(".gallery ul li").length-6)+"px"
       },0);
       idx=$(".gallery ul li").length-6;
    }
    
    console.log(idx);
  }