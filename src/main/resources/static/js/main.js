$(document).ready(function(){
 $('.header').height($(window).height());
})
/*

function checkusername(uName) {
    return $.ajax({
        url: 'https://haveibeenpwned.com/api/v2/breachedaccount/' + uName + '?includeUnverified=true',
        type: 'GET',
        dataType: 'json',
        success:function(data) {
            var html = '<h4>Your username was found in the following breaches:</h4>';
            for(i=0;i<data.length;i++) {
                var breachName = data[i].Title;
                var breachDesc = data[i].Description
                html += '<h3><span>' + breachName + '</span></h3>';
                html += '<p>' + breachDesc + '</p>';
                html += '<br>';
            }
            $('#results').append(html);
        }
    });
}
*/
