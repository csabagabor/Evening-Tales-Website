var apiTaleURL = "https://lit-wildwood-83335.herokuapp.com/api/tale/";

var getDates = function(startDate, endDate) {
  var dates = [],
      currentDate = startDate,
      addDays = function(days) {
        var date = new Date(this.valueOf());
        date.setDate(date.getDate() + days);
        return date;
      };
  while (currentDate <= endDate) {
    dates.push(currentDate);
    currentDate = addDays.call(currentDate, 1);
  }
  return dates;
};

function showTaleByDate(date){
  let getURL = apiTaleURL;
  if(date)
    getURL+=date;
  $.ajax({
      url: getURL
  }).then(function(data) {
     $('#tale-title').text(data.title);
     $('#tale-description').text(data.description);
  });
}


function getTaleByURL(url){
  let searchParams = new URLSearchParams(url.search);
  let paramDate = searchParams.get('date');
  if(paramDate)
    showTaleByDate(paramDate);
  else showTaleByDate(null);
}

$(document).ready(function() {
  if(window.location.href.indexOf("top")  > -1){
    //top.html

  }
  else{
    //index.html
      getTaleByURL(window.location);


      appendItemsToArchiveList();
  }

  function appendItemsToArchiveList(){
    var dates = getDates(new Date(2018,08,17), new Date());
    var archiveListElem = document.getElementById("archive-list");
    dates.forEach(function(date) {
      var prettyDate = date.toISOString().substring(0, 10);
      var listItem = document.createElement("a");
      listItem.appendChild(document.createTextNode(prettyDate));
      listItem.href = "javascript:showTaleByDate('"+prettyDate+"');";
      listItem.className = "dropdown-item";
      archiveListElem.appendChild(listItem);
    });
  }

});
