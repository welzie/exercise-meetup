$(document).ready(function() {

    
    fetch("http://localhost:8080/rest/activities")
    .then((response) => {
        return response.json();
    }).then((json) => {

        $(".content").append("<div class='activity-container'><div>");
        for(let i = 0; i < json.length; i++) {
            console.log(json[i]);

            let level = json[i].level;
            let type = json[i].type;
            let time;
            let user = json[i].user.username;
            let date = json[i].date.month + " " + json[i].date.dayOfMonth;
            let minutes = json[i].time.minute.toString().length > 1 ? json[i].time.minute.toString(): "0" + json[i].time.minute.toString();

            if(json[i].time.hour > 12) {
                time = (json[i].time.hour % 12).toString() + ':' + minutes + ' PM'
            } else {
                time = (json[i].time.hour).toString() + ':' + minutes + ' AM'
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
})