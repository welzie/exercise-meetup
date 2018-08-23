$(document).ready(function(){
    $('.header').height($(window).height());

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
                    404: function(){
                        $("#lastBreach").val("none");
                    },
                }});
                $("#register").submit();
             }
    });
})