
const posterBox = document.querySelector(".rotateList");
const posters = document.querySelectorAll(".eachImage");
let current = 0;
const posterNumber = posters.length;
const toLeft = document.querySelector(".rotateLeft");
const toRight = document.querySelector(".rotateRight");
const posterWidth = 75;
const posterMargin = 20;

function initfunction() {
  posterBox.style.width = (posterWidth + posterMargin) * (posterNumber + 2) + "px";
  posterBox.style.left = -(posterWidth + posterMargin) + "px";
}

function makeClone() {
  let cloneSlide_first = posters[0].cloneNode(true);
  let cloneSlide_last = posters.lastElementChild.cloneNode(true);
  posterBox.append(cloneSlide_first);
  posterBox.insertBefore(cloneSlide_last, posters.firstElementChild);
}

toRight.addEventListener("click", function () {
  console.log("before to right : " +current);
  let cloneSlide_first = posters[current].cloneNode(true);
  posterBox.append(cloneSlide_first);
  if (current <= posterNumber - 1) {
    //슬라이드이동
    posterBox.style.left =
      -(current + 1) * (posterWidth + posterMargin) + "px";
    posterBox.style.transition = `${0.5}s ease-out`; //이동 속도
  }
  if (current === posterNumber - 1) {
    //마지막 슬라이드 일때
    setTimeout(function () {
      //0.5초동안 복사한 첫번째 이미지에서, 진짜 첫번째 위치로 이동
      posterBox.style.left =
      -(current + 1) * (posterWidth + posterMargin) + "px";
    posterBox.style.transition = `${0.5}s ease-out`; //이동 속도
    }, 500);
    current = 0;
  }
  current += 1;
  console.log("after to right : " +current);
});

toLeft.addEventListener("click", function(){
  console.log("before to left : " +current);
  let cloneSlide_last = posters[posterNumber-1].cloneNode(true);
  posterBox.insertBefore(cloneSlide_last, posters.firstElementChild);
  console.log(posterNumber);
  console.log(current); 
	if (current >= 0) { 
		posterBox.style.left = -current * (posterWidth + posterMargin) + 'px';
		posterBox.style.transition = `${0.5}s ease-out`;
	} 
	if (current === 0) { 
		setTimeout(function () { 
			posterBox.style.left = -current * (posterWidth + posterMargin) + 'px';
		posterBox.style.transition = `${0.5}s ease-out`;
		}, 500);
		current = posterNumber;
		} 
	current -= 1; 
  console.log("after to left : " +current);
});