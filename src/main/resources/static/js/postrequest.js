$(document).ready( () => {
    $("#btnSubmit").click((event) => {

        //stop submit the form, we will post it manually.
        event.preventDefault();
        let user = $("#greeting").attr("data-user");

        doAjax(user);
    });

});

function doAjax(user) {

    // Get form
    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    data.append(username,user);
    //var user = $("#user").val();
        //if (user){

         $.ajax({
            type: "POST",
            //enctype: 'multipart/form-data',
            url: "/api/file/upload",
            data: data,
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            success: (data) => {
                //$("#listFiles").text(data);
                //use user id to build a URL that returns the uploaded image
                fetch("http://localhost:8080/api/file/upload/uploadfile?username=" +user+ "&uploadfile=" + uploadfile)
                .then(response=> response.blob)
                .then((data)=> {
                    console.log(data);
                });

            },
            //error: (e) => {
                //$("#listFiles").text(e.responseText);
            //}
        });
    }