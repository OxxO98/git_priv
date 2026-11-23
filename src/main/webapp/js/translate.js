const handleTranslate = (no, year, title, region, content) => {
    $.ajax({
        url : "/api/translate",
        method: "POST",
        data : {
            no : no,
            year : year,
            title : title,
            region : region,
            content : content
        }
    }).done( (res) => {

    });
}