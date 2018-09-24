var domain = "https://lit-wildwood-83335.herokuapp.com";
//var domain = "http://localhost:8080";//for testing
var apiTaleURL = domain+"/api/tale/";
var apiRatingURL = domain+"/api/tale/rating/";
var apiTopTalesURL = domain+"/api/tale/top/";


function getTaleRatingByDate(date){
  $.ajax({
      url: apiRatingURL+date
  }).then(function(data) {
     $("#rating-date-"+date).text("Avg. Rating: "+ data.rating.toFixed(2) + "("+ data.nr_rating +" ratings)");
  });
}


function getTopTales(limit){
  $.ajax({
      url: apiTopTalesURL + limit
  }).then(function(data) {
        for(let i=0;i<data.length ; i++){
          if(data !== null){

           $("#my-content").append(  '<div class="card content" style="width: 18rem;margin-top:40px">' +
               '<div class="card-body">' +

               "<h2>Top " +(i+1)+ " </br> " + data[i].title + "</h2>" +
               "<p class='card-text'>" +  data[i].description.substring(0, 40) + '...' + '</p>' +
               '<h5>Date added: '+ data[i].dateAdded +  '</h5>' +
               '<p id="rating-date-' + data[i].dateAdded + '"></p>' +
               '<a href="main.html" id="tale-date-' + data[i].dateAdded +'" class="li-modal btn btn-info">See Full description</a>' +
             '</div></div>');
            //append rating when available
            getTaleRatingByDate(data[i].dateAdded);

            $('.li-modal').on('click', function(e){
                  e.preventDefault();
                  var id = $(this).attr('id');
                  var date = id.replace("tale-date-","");

                  $('#mainModal').modal('show').find('.modal-content').load($(this).attr('href'),function(data){
                    //append tale and new title
                    $('#story-big-title').text("Tale: "+date);
                    getTaleByDate(date);
                });

            });

         }
       }
       hideLoadingScreen();
  });
}



$(document).ready(function() {
    getTopTales(25);
    //appendItemsToArchiveList();
});
