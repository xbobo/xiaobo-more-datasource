 $.fn.extend({
     showMessage: function($msg, $time) {
         var oDiv = document.createElement("div");
         oDiv.setAttribute("id", "toast");
         var oBody = document.getElementsByTagName('body')[0];
         oBody.append(oDiv);
         $("#toast").css({
             "position": "fixed",
             "top": "44%",
             "left": "50% ",
             "transform": "translateX(-50%)",
             "min-width": "80px",
             "max-width": "180px",
             "min-height": "18px",
             "padding": "10px",
             "line-height": "18px",
             "text-align": "center",
             "font-size": "16 px",
             "color": "#fff",
             "background": "rgba(0, 0, 0, 0.6)",
             "border-radius": "5px",
             "display": "none",
             "z-index": "999"
         });
         $('#toast').text($msg);
         $('#toast').fadeIn();
         setTimeout(function() {
             $('#toast').fadeOut();
         }, $time);
     }
 });

 function toastTips(msg) {
     $("#toast").showMessage(msg, 3000);
 }