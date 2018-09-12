
$(document).ready(function() {
    $.ajax({
        url: "https://lit-wildwood-83335.herokuapp.com/api/tale/"
    }).then(function(data) {
       $('#tale-title').append(data.title);
       $('#tale-description').append(data.description);
    });
});
