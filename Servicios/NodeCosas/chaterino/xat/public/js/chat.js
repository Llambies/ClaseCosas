let ipServidor = '0.0.0.0';

ipServidor = prompt('Introduce la IP del servidor');
if (ipServidor == null || ipServidor == '') {
    ipServidor = '0.0.0.0';
}

var socket = io('http://' + ipServidor + ':8080');

socket.on('conectado', () => {
    alert('Conectado al servidor');
    document.getElementById('formulario').style.display = '';
    document.getElementById('enviar').setAttribute('onclick', 'enviar()');
});

function enviar() {
    var nick = document.getElementById('nick').value;
    var texto = document.getElementById('texto').value;
    if (nick != '' && texto != '') {
        socket.emit('enviar', { nick: nick, texto: texto });
    } else {
        alert('Por favor, introduce un nick y un mensaje');
    }
}

socket.on('difundir', (data) => {
    var chat = document.getElementById('chat');
    var mensaje = document.createElement('p');
    mensaje.innerHTML = '<em>' + data.nick + ':</em> ' + data.texto;
    chat.appendChild(mensaje);
});