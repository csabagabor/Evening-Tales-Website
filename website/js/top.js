var apiTaleURL = "https://lit-wildwood-83335.herokuapp.com/api/tale/";
var apiRatingURL = "http://localhost:8080/api/tale/rating/";
var apiTopTalesURL = "http://localhost:8080/api/tale/top/";
//http://localhost:8080/api/tale/rating/
//https://lit-wildwood-83335.herokuapp.com/api/tale/rating/

function getTaleByDate(date){
  currentTaleDate = date;
  let getURL = apiTaleURL;
  getURL+=date;
  $.ajax({
      url: getURL
  }).then(function(data) {
     $('#tale-title').text(data.title);
     $('#tale-description').text(data.description);
  });
  //show user's Rating
  var rating = getOwnRatingByDate(date);
  appendRating(rating);
  showTaleRatingByDate(date);
}

function showTaleRatingByDate(date){
  $.ajax({
      url: apiRatingURL+date
  }).then(function(data) {
     $('#average-rating').text("Avg. Rating: "+ data.rating + "("+ data.nr_rating +" ratings)");
  });
}

function formatDateToString(date){
    year = date.getFullYear();
    month = date.getMonth()+1;
    dt = date.getDate();

    if (dt < 10) {
      dt = '0' + dt;
    }
    if (month < 10) {
      month = '0' + month;
    }
    return (year+'-' + month + '-'+dt);
}

function appendItemsToArchiveList(){
  var dates = getDates(new Date(2018,08,17), Date.now());
  var archiveListElem = document.getElementById("archive-list");
  dates.forEach(function(date) {
    var prettyDate = formatDateToString(date);
    var listItem = document.createElement("a");
    listItem.appendChild(document.createTextNode(prettyDate));
    listItem.href = "javascript:showTaleByDate('"+prettyDate+"');";
    listItem.className = "dropdown-item";
    archiveListElem.appendChild(listItem);
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

               "<h2 id='tale-title'>" + data[i].title + "</h2>" +
               "<p id='tale-description' class='card-text'>" +  data[i].description  +

               '</p></div>' +
             '</div>');
         }
       }
  });
}

$(document).ready(function() {
    getTopTales(25);
    //appendItemsToArchiveList();
});
