var stompClient = null;

function setConnected(connected) {
    if (connected) {
        $("#connect").hide();
        const nicknameInput = $("#nicknameInput");
        nicknameInput.hide();
        $("#labelWelcome").text(() => ('Welcome, ' + nicknameInput.val() + '!'));
        $("#divHistoryButtons").show();
        $("#divMessage").show();
        $("#conversation").show();
        $("#disconnect").show();
        $("#messageInput").val('').focus();
    } else {
        $("#disconnect").hide();
        $("#divMessage").hide();
        $("#conversation").hide();
        $("#divHistoryButtons").hide();
        $("#labelWelcome").text(() => ('What is your nickname?'));
        $("#nicknameInput").show();
        $("#connect").show();
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

function clearScreen() {
    $("#greetings").html("");
}

function sendMessage() {
    const messageInput = $("#messageInput");
    stompClient.send("/app/hello", {}, JSON.stringify({
        'message': messageInput.val(),
        'nickname': $("#nicknameInput").val()
    }));
    messageInput.val('').focus();
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
    $("#divMessage").hide();
    $("#divHistoryButtons").hide();
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#messageSend").click(function () {
        sendMessage();
    });
    $("#btnClearScreen").click(function () {
        clearScreen();
    });
});

