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
    var imgContainer =$('#imgContainer');
    data.append(username,user);
    data.append(imgContainer,imgContainer);
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
                const url = "http://localhost:8080/viewfile/image?"+ id;
                console.log(url);
                fetch(url).then((data)=> {
                   //console.log(data);
                    imgContainer.html('');
                    var img = '<img src="data:' + data.contenttype + ';base64,' + data.base64 +'"/>';

                    imgContainer.append(img);
                });

            },
            //error: (e) => {
                //$("#listFiles").text(e.responseText);
            //}
            //uploadfile?username=" +user+ "&uploadfile=" + uploadfile)
        });
    }