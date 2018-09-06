$(document).ready(function() {

    function search() {
   
        let completed = false;
        let incompletedActivities = $("#future")[0].checked;
        let completedActivities = $("#completed")[0].checked;
        if (incompletedActivities && completedActivities) {
            completed = "both";
        } else if(completedActivities) {
            completed = true;
        }

        let qeuryHolder = "";
        let level = $("#level").val();
        let type = $("#type").val();
        let time = $("#time").val();
        let date = $("#date").val();

        let counter = 0;
        if(level != "") { 
            qeuryHolder += "level=" + level
            counter++;
        }
        if(type != "") { 
            counter == 0 ? qeuryHolder += "type=" + type : qeuryHolder += "&type=" + type;
            counter++;
        }
        if(time != "") { 
            counter == 0 ? qeuryHolder += "time=" + time : qeuryHolder += "&time" + time;
            counter++;
        }
        if(date != "") { 
            counter == 0 ? qeuryHolder += "search_date=" + date : qeuryHolder += "&date" + date;
            counter++;
        }
        if(completed != "false") { 
            counter == 0 ? qeuryHolder += "completed=" + completed : qeuryHolder += "&completed" + completed;
        }

        fetch('http://localhost:8080/rest/search?' + qeuryHolder)
        .then((response) => {
            return response.json();
        }).then((jsonResponse) => {

            if($(".activity-container")) {
                $(".activity-container").remove();
            }

            $(".content").append("<div class='activity-container'><div>");
            for(let i = 0; i < jsonResponse.length; i++) {
                console.log(jsonResponse[i]);
                let activity = jsonResponse[i];
                let level = activity.level;
                let type = activity.type;
                let time;
                let user = activity.user.username;
                let date = activity.date.month + " " + activity.date.dayOfMonth;
                let minutes = activity.time.minute.toString().length > 1 ? activity.time.minute.toString(): "0" + activity.time.minute.toString();

                if(activity.time.hour > 12) {
                    time = (activity.time.hour % 12).toString() + ':' + minutes + ' PM'
                } else {
                    time = (activity.time.hour).toString() + ':' + minutes + ' AM'
                }

                let activityStructure = '<div class="activity-item flex-grid ' + level + '"> ' +
                    '<div>' +
                        '<p>' + level + '</p>' +
                        '<h5>' + date + '</h5>' +
                        '<h2>' + time + '</h2>' +
                    '</div>' +
                    '<div>' +
                        '<h1>' + type + '</h1>' +
                        '<a href="/user/' + user + '" ><h3>' + user + '</h3></a>' +
                    '</div>' +
                '</div>';
                console.log(activityStructure);
                $(".activity-container").append(activityStructure);
            }
        })
    }

    $("#type").change(() => {
        search();
    })

    $("#level").change(() => {
        search();
    })

    $("#time").change(() => {
        search();
    })

    $("#date").change(() => {
        search();
    })

    $("#future").change(() => {
        search();
    })

    $("#completed").change(() => {
        search();
    })
})