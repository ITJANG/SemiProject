document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("postForm");

  // 남은 바이트 수를 보여줄 UI 요소 추가 (선택적으로 만들기!)
  const byteCountElement = document.createElement("div");
  byteCountElement.classList.add("text-muted", "mt-2");
  byteCountElement.textContent = "0 / 4000 bytes";
  form.appendChild(byteCountElement);

  $("#summernote").summernote({
    placeholder: "내용을 입력해주세요",
    height: 300,
    lang: "ko-KR",
    toolbar: [
      ["style", ["bold", "italic", "underline", "clear"]],
      ["font", ["strikethrough", "superscript", "subscript"]],
      ["fontsize", ["fontsize"]],
      ["para", ["ul", "ol", "paragraph"]],
      ["table", ["table"]],
      ["insert", ["picture"]],
      ["view", ["help"]],
    ],
    callbacks: {
      onImageUpload: function (files) {
        for (let i = 0; i < files.length; i++) {
          defaultUploadImage(files[i]);
        }
      },
      // 실시간 현재 바이트 수 계산 안내
      onKeyup: function () {
        // 내용 가져오기
        const content = $("#summernote").summernote("code").trim();
        // 내용 UTF-8 기준 바이트 길이 가져옴
        const byteLength = new Blob([content]).size;
        // 바이트 길이 출력
        byteCountElement.textContent = `${byteLength} / 4000 bytes`;
      },
    },
  });

  // 이미지 업로드 함수 (기존 그대로)
  function defaultUploadImage(file) {
    const formData = new FormData();
    formData.append("image", file);

    fetch("/editBoard/2/defaultUploadImage", {
      method: "POST",
      body: formData,
    })
      .then((res) => res.text())
      .then((imageUrl) => {
        $("#summernote").summernote("insertImage", imageUrl);
      })
      .catch((err) => {
        console.error("이미지 업로드 실패", err);
        alert("이미지 업로드 중 오류 발생");
      });
  }

  // 제출 시점에서만 최종 바이트 길이 검사
  form.addEventListener("submit", function (e) {
    const content = $("#summernote").summernote("code").trim();
    // html 태그 제거 후 내용만 가져오기
    const textContent = $("<div>").html(content).text().trim();
    const byteLength = new Blob([content]).size;

    if (textContent.length === 0) {
      alert("내용을 입력해주세요!");
      e.preventDefault(); // 제출 방지
    }

    if (byteLength > 4000) {
      alert("최대 4000바이트까지만 입력 가능합니다.");
      e.preventDefault();
      return;
    }
  });
});
