var apiTaleURL = "https://lit-wildwood-83335.herokuapp.com/api/tale/";
var apiRatingURL = "http://localhost:8080/api/tale/rating/";
var apiTopTalesURL = "http://localhost:8080/api/tale/top/";
//http://localhost:8080/api/tale/rating/
//https://lit-wildwood-83335.herokuapp.com/api/tale/rating/

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

               "<h2 id='tale-title'>Top " +(i+1)+ " </br> " + data[i].title + "</h2>" +
               "<p id='tale-description' class='card-text'>" +  data[i].description  + '</p>' +
               '<h5>Date added: '+ data[i].dateAdded +  '</h5>' +
               '<p id="rating-date-' + data[i].dateAdded + '"></p>' +

             '</div></div>');
            //append rating when available
            getTaleRatingByDate(data[i].dateAdded);
         }
       }
       hideLoadingScreen();
  });
}

$(document).ready(function() {
    getTopTales(25);
    //appendItemsToArchiveList();
});
