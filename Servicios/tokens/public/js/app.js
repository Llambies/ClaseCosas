function login()
{
    $.ajax({
        url:"/login",
        type:"POST",
        data: JSON.stringify({login: $("#login").val(), password: $("#password").val()}),
        contentType:"application/json; charset=utf-8",
        dataType:"json",
        success: function(data) {
            if (data.error) {
                $("#loginError").css('display', 'block');
            } else {
                $("#loginError").css('display', 'none');
                localStorage.setItem('token', data.token);
                cargar('/inicio');
            }
        }
    });
}


function cargar(ruta) 
{
    var token = "";
    if (localStorage.getItem('token')) {
        token = localStorage.getItem('token');
    }

    $.ajax({
        url: ruta,
        type:"GET",
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', token);
        },
        success: function(data) {
            $('#contenido').html(data);
        }
    });
}
