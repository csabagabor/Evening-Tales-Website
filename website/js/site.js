var apiTaleURL = "https://lit-wildwood-83335.herokuapp.com/api/tale/";

function getTaleByURL(url){
  let getURL = apiTaleURL;
  let searchParams = new URLSearchParams(url.search);
  let paramDate = searchParams.get('date');
  if(paramDate)
    getURL+="/"+paramDate;
  $.ajax({
      url: getURL
  }).then(function(data) {
     $('#tale-title').append(data.title);
     $('#tale-description').append(data.description);
  });
}

$(document).ready(function() {

  getTaleByURL(window.location);


});
