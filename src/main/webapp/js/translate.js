
function handleTranslate(dom) {
    console.log('translate...');
    const no = $(dom).data("no");
    const title = $(dom).attr("data-title");
    const content = $(dom).attr("data-content");
    // console.log(no, title, content);

    $(dom).prop("disabled", true);
    $.ajax({
        url : "api/translate",
        method: "POST",
        contentType: "application/json; charset=UTF-8",
        data : JSON.stringify({
            no: no,
            title: title,
            content: content
        })
    }).done( (res) => {
        console.log('translate done!!!');
        console.log(res);
    }).fail((xhr, status, error) => {
        console.error("번역 실패:", error);
        console.error(xhr.responseText);
    });
}