$(function() {
	$("#summernote").summernote(setting);
});
 
toolbar = [
	// 글꼴 설정
	['fontname', ['fontname']],
	// 글자 크기 설정
	['fontsize', ['fontsize']],
	// 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
	['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
	// 글자색
	['color', ['forecolor', 'color']],
	// 표만들기
	['table', ['table']],
	// 글머리 기호, 번호매기기, 문단정렬
	['para', ['ul', 'ol', 'paragraph']],
	// 줄간격
	['height', ['height']],
	// 그림첨부, 링크만들기, 동영상첨부
	['insert', ['picture', 'link', 'video']],
	// 코드보기, 확대해서보기, 도움말
	['view', ['codeview', 'fullscreen', 'help']]
];

setting = {
	height: 500,
	minHeight: null,
	maxHeight: null,
	focus: true,
	lang: 'ko-KR',
	toolbar: toolbar,
	callbacks: { //여기 부분이 이미지를 첨부하는 부분
		onImageUpload: function(files, editor, welEditable) {
			for (var i = files.length - 1; i >= 0; i--) {
				uploadSummernoteImageFile(files[i], this);
			}
		}
	}
};


function uploadSummernoteImageFile(file, el) {
	data = new FormData();
	data.append("file", file);
	
	$.ajax({
		data: data,
		type: "POST",
		url: "/admin/file/notice",
		contentType: false,
		enctype: 'multipart/form-data',
		processData: false,
		success: function(data) {
			$(el).summernote('editor.insertImage', data.url);
		}
	});
}