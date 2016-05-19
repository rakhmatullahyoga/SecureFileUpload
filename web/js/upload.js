/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

document.querySelector('input#file').addEventListener('change', function(){
    var reader = new FileReader();
    reader.onload = function(){
        var markup, result, n, aByte, byteStr;

        markup = [];
        result = reader.result;
        for (n = 0; n < result.length; ++n) {
            aByte = result.charCodeAt(n);
            byteStr = aByte.toString(16);
            if (byteStr.length < 2) {
                byteStr = "0" + byteStr;
            }
            markup.push(byteStr);
        }
        document.getElementById('msg').value = markup.join("");
    };
    reader.readAsBinaryString(this.files[0]);
}, false);

$(document).ready(function() {
    var options = {
        beforeSend : function() {
            $("#progressbox").show();

            // clear everything
            $("#progressbar").width('0%');
            $("#message").empty();
            $("#percent").html("0%");
        },
        uploadProgress : function(event, position, total, percentComplete) {
            $("#progressbar").width(percentComplete + '%');
            $("#percent").html(percentComplete + '%');

            // change message text and % to red after 50%
            if (percentComplete > 50) {
                $("#message").html("<font color='red'>File Upload is in progress .. </font>");
            }
        },
        success : function() {
            $("#progressbar").width('100%');
            $("#percent").html('100%');
        },
        complete : function(response) {
            $("#message").html("<font color='blue'>Your file has been uploaded!</font>");
        },
        error : function() {
            $("#message").html("<font color='red'> ERROR: unable to upload files</font>");
        }
    };
    $("#UploadForm").ajaxForm(options);
});

$(document).on("click", "#verify", function() {
    var key = document.getElementById('pubkey').value;
    var sign = document.getElementById('sig').value;
    $.get("UploadServlet", function(responseText) {
        $("#verification").html(responseText);
    });
});