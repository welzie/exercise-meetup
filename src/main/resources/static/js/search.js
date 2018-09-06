$(document).ready(function() {

    let qeuryHolder = '';
    /** add click event in html
     *   
     *  name searchActivities()
     * 
    /** build query holder with conditionals */

    fetch('http://localhost:8080/rest/search?completed=both')
    .then((response) => {
        return response.json();
    }).then((jsonResponse) => {
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
})