var idx = 0;
var lectureIdx = 0;

function lectureRight(){
  
  $(".slideBox ul.slides li:nth-child("+(lectureIdx+2)+") .img").animate({
    "width":250+"px",
    "height" : 350+"px"
  },500);
  $(".slideBox ul.slides li:nth-child("+(lectureIdx+2)+") .lectureCaption").animate({
    "top":400+"px"
  },500);
  $(".slideBox ul.slides li:nth-child("+(lectureIdx+3)+") .img").animate({
    "width":350+"px",
    "height" : 450+"px"
  },500);
  $(".slideBox ul.slides li:nth-child("+(lectureIdx+3)+") .lectureCaption").animate({
    "top":450+"px"
  },500);
  lectureIdx++;
  $(".slideBox ul.slides").animate({
    "left":-530*lectureIdx+"px"
  },500);

  if(lectureIdx> $(".slideBox ul.slides li").length-6){
     $(".slideBox ul.slides").animate({
       "left":0
     },0);
     $(".slideBox ul.slides li:nth-child("+(lectureIdx+2)+") .img").animate({
      "width":250+"px",
      "height" : 350+"px"
    },0);
    $(".slideBox ul.slides li:nth-child("+(lectureIdx+2)+") .lectureCaption").animate({
      "top":400+"px"
    },0);
     lectureIdx=0;
     $(".slideBox ul.slides li:nth-child("+(lectureIdx+2)+") .img").animate({
      "width":350+"px",
      "height" : 450+"px"
    },0);
    $(".slideBox ul.slides li:nth-child("+(lectureIdx+2)+") .lectureCaption").animate({
      "top":450+"px"
    },0);
  }
}

function lectureLeft(){
  $(".slideBox ul.slides li:nth-child("+(lectureIdx+2)+") .img").animate({
    "width":250+"px",
    "height" : 350+"px"
  },500);
  $(".slideBox ul.slides li:nth-child("+(lectureIdx+2)+") .lectureCaption").animate({
    "top":400+"px"
  },500);
  $(".slideBox ul.slides li:nth-child("+(lectureIdx+1)+") .img").animate({
    "width":350+"px",
    "height" : 450+"px"
  },500);
  $(".slideBox ul.slides li:nth-child("+(lectureIdx+1)+") .lectureCaption").animate({
    "top":450+"px"
  },500);

  lectureIdx--;
  $(".slideBox ul.slides").animate({
    "left":-530*(lectureIdx)+"px"
  },500);


  if(lectureIdx < 0){
    $(".slideBox ul.slides li:nth-child("+(lectureIdx+2)+") .img").animate({
      "width":250+"px",
      "height" : 350+"px"
    },0);
    $(".slideBox ul.slides li:nth-child("+(lectureIdx+2)+") .lectureCaption").animate({
      "top":400+"px"
    },0);
     $(".slideBox ul.slides").animate({
      "left":-530*($(".slideBox ul.slides li").length-6)+"px"
     },0);
     lectureIdx=$(".slideBox ul.slides li").length-6;
     $(".slideBox ul.slides li:nth-child("+(lectureIdx+2)+") .img").animate({
      "width":350+"px",
      "height" : 450+"px"
    },0);
    $(".slideBox ul.slides li:nth-child("+(lectureIdx+2)+") .lectureCaption").animate({
      "top":450+"px"
    },0);
     
  }
  
}
  
function toRight(){
  

  idx++;
  $(".smallPosterSlide ul.smallPosters").animate({
    "left":-95*idx+"px"
  },300);
  $(".eventSlideBox ul.outerSlideBox").animate({
    "left": -1350*idx + "px"
  },0);

  if(idx> $(".smallPosterSlide ul.smallPosters li").length-6){
     $(".smallPosterSlide ul.smallPosters").animate({
       "left":0
     },0);
    $(".eventSlideBox ul.outerSlideBox").animate({
      "left":0
    },0);
     idx=0;
  }
}
 
function toLeft(){
  

  idx--;
  $(".smallPosterSlide ul.smallPosters").animate({
    "left":-95*(idx)+"px"
  },300);
  $(".eventSlideBox ul.outerSlideBox").animate({
    "left":-1350*(idx)+"px"
   },0);


  if(idx < 0){
     $(".smallPosterSlide ul.smallPosters").animate({
      "left":-95*($(".smallPosterSlide ul.smallPosters li").length-6)+"px"
     },0);
     $(".eventSlideBox ul.outerSlideBox").animate({
      "left":-1350*($(".smallPosterSlide ul.smallPosters li").length-6)+"px"
     },0);
     idx=$(".smallPosterSlide ul.smallPosters li").length-6;
  }
  
}

$(function(){

  $('.CategoryButtons .showNotice').click(function(){
      $('.CategoryButtons .showTicket').removeClass('boosted');
      $(this).addClass('boosted');
      $(".moreNotice").removeClass("hidden");
      $(".moreTicket").addClass("hidden");
      // TODO 공지사항 목록 show + 티켓 목록 hide 추가
  });

  $('.CategoryButtons .showTicket').click(function(){
    $('.CategoryButtons .showNotice').removeClass('boosted');
    $(this).addClass('boosted');
    $(".moreTicket").removeClass("hidden");
    $(".moreNotice").addClass("hidden");
    // TODO 공지사항 목록 hide + 티켓 목록 show 추가
});

  setInterval(toRight, 3000);
  setInterval(lectureRight, 4000);

});

// Youtube API
var playlist = 'lOLvuGV-88o';
{/* //https://www.youtube.com/watch?v=유튜브 영상 고유번호
//playlist만 원하는 재생목록에 따라 가져오면 됨
//maxResult는 50 이하 */}
// https://developers.google.com/youtube/v3/docs/videos
$(document).ready(function () {
  $.get(
    "https://www.googleapis.com/youtube/v3/videos",{
      part: 'snippet',
      maxResults: 1,
      id: playlist,
      key: 'AIzaSyCFcF4_hFYZsWaDwOn5-bdtrgAAwaCrwNE'
    }, function (data) {
    var output;
    $.each(
      data.items, function (i, item) {
        vTitle = item.snippet.title;
        vId = item.snippet.channelId;
        vId2 = "https://www.youtube.com/watch?v="+item.id;
        vDe = item.snippet.description;
        vPa = item.snippet.publishedAt.substr(0,10);
        vaaa = item.snippet.thumbnails.standard.url;
        output = '<li><a href="'+ vId2 +'" >' + vTitle + '</a><br><span>'+ vPa+'</span><br><a href="'+ vId2 +'" ><img style="width : 100%" src ="' + vaaa + '"></a></li>';
        /*output= '<li>'+vTitle+'<iframe src=\"//www.youtube.com/embed/'+vId+'\"></iframe></li>';*/
        $(".Youtube").append(output);
      }
    )}
  );
});


// quick menu at the bottom ###############################

$(function(){

  $('.shortcuts ul li').hover(function(){
    $(this).find("span").addClass('on');
  }, function(){
    $(this).find("span").removeClass('on');
  });

});


// welcomeslide

$(function(){
  $(".welcomeSlide .welcomeSlides li").animate(
    {"opacity" : 0},
    0
  )
  $(".welcomeSlide .welcomeSlides li:first-child").animate(
    {"opacity" : 1},
    0
  )
  $(".welcomeSlide .indicators li:first-child").addClass("currentWelcome");

  $('.welcomeSlide .indicators li').click(function(){
    
    var ci = $(this).index();
    $('.welcomeSlide .indicators li').removeClass('currentWelcome');
    $(this).addClass('currentWelcome');

    $(".welcomeSlide .welcomeSlides li").animate({"opacity" : 0},250);
    $(".welcomeSlide .welcomeSlides li").eq(ci).animate({"opacity":1},0);
});
})

