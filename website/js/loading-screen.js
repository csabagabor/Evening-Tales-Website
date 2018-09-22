var available = false;

$(document).ready(function() {
    $("#loading-screen").append(
  `<div class="modal" id="loading-modal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">

        <div class="modal-header">
          <h3 class="modal-title">Loading...</h4>
        </div>

        <div class="modal-body">
          Please wait for the page to load......
        </div>

      </div>
    </div>
  </div>`
    );

  $("#loading-modal").modal("show");
  available = true;
});


function hideLoadingScreen(){
  var a = $("#loading-modal");
  if(available) //modal is added dynamically to DOM, so it can happen that it isn't appended yet
    a.modal("hide");
  else
    setTimeout(a.modal("hide"), 3000);
}
