
function login(){
    var name = $("#name").val();
    var pswd = $("#pswd").val();

    $.ajax({
        url: '../../server/oilLogin',
        data: {name:name,pswd:pswd},
        type: "POST",
        success: function(data) {
            if (data.code == '-1') {
                alert(data.info);
            } else {
                window.location.href = '../html/Applicant.html';
            }
        }
    })
}

function register(){
    window.location.href = '../html/Register.html';
}