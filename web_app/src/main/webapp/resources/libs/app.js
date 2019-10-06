var stompClient = null;

function setConnected(connected) {
    if (connected) {
        $("#connect").hide();
        $("#disconnect").show();
        $("#conversation").show();
    }
    else {
        $("#disconnect").hide();
        $("#connect").show();
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            const fullMessage = JSON.parse(greeting.body).content;
            showFullMessage(fullMessage);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.send("/app/hello", {}, JSON.stringify({'message': $("#messageInput").val()}));
}

function showFullMessage(fullMessage) {
    $("#greetings").append("<tr><td>" + fullMessage + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#conversation").hide();
    $("#disconnect").hide();
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#messageSend" ).click(function() { sendMessage(); });
});

