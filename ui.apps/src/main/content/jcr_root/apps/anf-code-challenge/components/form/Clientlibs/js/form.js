// ***Begin Code - Gopi Kotapati *** 

$(document).ready(function(){

    $("#formSubmit").click(function(){
        var name = $("#formName").val();
        var email = $("#formEmail").val();
        var age = $("#formAge").val();
        var country = $(".formCountry").text();
		if((name != '')&&(email != '') && (age != ''))
        {
        validations(name, email, age, country);
        }
        else{
            alert("Please fill the required details");
        }
    });

});

function validations(name, email, age, country){

    $.ajax({
        type: 'GET',
        url:'/bin/saveUserDetails'+"?name="+ name + "&email="+ email +"&age="+age + "&country=" + country,
        async: false,

        success: function (data) { 
             alert(data);
            console.log(data);
            }
    });
}

// *** END CODE ***