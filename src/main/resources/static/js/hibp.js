$(document).ready(function() {
    /*HIBP API call - if found, populate a hidden tag with the latest breach date */
    $("#submitRegister").click(function(){
        var user = $("#user").val();
        if (user){
            $.ajax({
                url: 'https://haveibeenpwned.com/api/v2/breachedaccount/' + user + '?includeUnverified=true',
                type: 'GET',
                dataType: 'json',
                async: false,
                statusCode: {
                    200: function(data){
                        var latestBreach = data[0].BreachDate;
                        for(i=0;i<data.length;i++) {
                            if(data[i].BreachDate > latestBreach){
                                latestBreach = data[i].BreachDate;
                                }
                            }
                        $("#lastBreach").val(latestBreach);
                        },
                    /*404: function(){
                        $("#lastBreach").val("none");
                    },*/
                }});
                $("#register").submit();
             }
    });
    /*user interactions if a breach notification is found */
    $("#hideNotification").click(function() {
        if ($('input[type=checkbox]').prop('checked')) {
            let breachNotify = null;
            let user = $("#greeting").attr("data-user");

            console.log(hidePermanently);
            fetch("http://localhost:8080/rest/breachNotify?username=" + user + "&breachNotify=" + breachNotify)
            .then((response) => {
                return response.json();
            })
            .then((jsonResponse) => {
                console.log(jsonResponse);
                $("#hibp").hide();
                })
            } else {
                $("#hibp").hide();
            }
        });
})
