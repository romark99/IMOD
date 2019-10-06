var stompClient = null;
var nickname = null;

// For todays date;
Date.prototype.today = function () {
    return ((this.getDate() < 10)?"0":"") + this.getDate() +"/"+(((this.getMonth()+1) < 10)?"0":"") + (this.getMonth()+1) +"/"+ this.getFullYear();
};

// For the time now
Date.prototype.timeNow = function () {
    return ((this.getHours() < 10)?"0":"") + this.getHours() +":"+ ((this.getMinutes() < 10)?"0":"") + this.getMinutes() +":"+ ((this.getSeconds() < 10)?"0":"") + this.getSeconds();
};

function setConnected(connected) {
    if (connected) {
        $("#connect").hide();
        const nicknameInput = $("#nicknameInput");
        nicknameInput.hide();
        nickname = nicknameInput.val();
        $("#labelWelcome").text(() => ('Welcome, ' + nickname + '!'));
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
        nickname = null;
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
        stompClient.subscribe('/topic/showHistory' , function (greetings) {
            const fullMessages = JSON.parse(greetings.body);
            showFullMessages(fullMessages);
        })
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
    const newDate = new Date();
    const datetime = newDate.today() + " @ " + newDate.timeNow();
    stompClient.send("/app/hello", {}, JSON.stringify({
        'message': messageInput.val(),
        'nickname': $("#nicknameInput").val(),
        'time' : datetime
    }));
    messageInput.val('').focus();
}

function showHistory() {
    stompClient.send("/app/showHistory", {});
}

function deleteHistory() {
    stompClient.send("/app/deleteHistory", {});
}

function showFullMessage(fullMessage) {
    $("#greetings").append("<tr><td>" + fullMessage + "</td></tr>");
}

function showFullMessages(fullMessages) {
    clearScreen();
    fullMessages.forEach(fullMessage => {
        showFullMessage(fullMessage.content);
    })
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
    $("#btnShowHistory").click(function() {
        showHistory();
    });
    $("#btnDeleteHistory").click(function() {
        deleteHistory();
    });
});

