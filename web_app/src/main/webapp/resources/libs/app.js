var stompClient = null;

function setConnected(connected) {
    if (connected) {
        $("#formNicknameDisconnected").hide();
        $("#labelWelcome").text(() => ('Welcome, ' + $("#nicknameInput").val()));
        $("#divMessage").show();
        $("#formNicknameConnected").show();
        $("#conversation").show();
    }
    else {
        $("#formNicknameConnected").hide();
        $("#divMessage").hide();
        $("#conversation").hide();
        $("#formNicknameDisconnected").show();
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
    const messageInput = $("#messageInput");
    stompClient.send("/app/hello", {}, JSON.stringify({'message': messageInput.val(), 'nickname': $("#nicknameInput").val()}));
    messageInput.val('');
}

function showFullMessage(fullMessage) {
    $("#greetings").append("<tr><td>" + fullMessage + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#conversation").hide();
    $("#formNicknameConnected").hide();
    $("#divMessage").hide();
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#messageSend" ).click(function() { sendMessage(); });
});

