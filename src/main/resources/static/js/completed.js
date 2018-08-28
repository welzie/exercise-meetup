$(document).ready(function(){
    
    $("#set-completed").click(() => {
        let completed = $("#set-completed").attr("data-completed");
        let activityId = $("#set-completed").attr("data-activity-id");
        let user = $("#username").attr("data-user");

        console.log(completed);
        fetch("http://localhost:8080/rest/completed?username=" + user + "&activityId=" + activityId + "&completed=" + completed)
        .then((response) => {
            return response.json();
        })
        .then((jsonResponse) => {
            console.log(jsonResponse);            
            $("#set-completed").replaceWith("<p>Completed!</p>");
            
        })

    })
   })
   