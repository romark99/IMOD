var stompClient = null;
var nickname = null;
var room = 1;

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
            const message = {
                content: JSON.parse(greeting.body).content,
                datetime_str: JSON.parse(greeting.body).datetime_str,
                nickname: JSON.parse(greeting.body).nickname,
                room: JSON.parse(greeting.body).room
            };
            showFullMessage(message);
        });
        stompClient.subscribe('/topic/showHistory', function (greetings) {
            const fullMessages = JSON.parse(greetings.body);
            showFullMessages(fullMessages);
        });
        stompClient.subscribe('/topic/removeHistory', function (result) {
            if (result) {
                clearScreen();
            }
        });
        stompClient.subscribe('/topic/typing', function (user) {
            const nickname = JSON.parse(user.body).nickname;
            showTyping(nickname);
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
        'content': messageInput.val(),
        'nickname': $("#nicknameInput").val(),
        'room': room
    }));
    messageInput.val('').focus();
}

function triggerTyping() {
    stompClient.send("/app/typing", {}, JSON.stringify({
        'nickname': nickname
    }));
}

function showTyping(nickname) {
    const spanWhoIsTyping = $("#spanWhoIsTyping");
    spanWhoIsTyping.show();
    spanWhoIsTyping.text(() => (nickname + ' is typing...'));
    const hideTyping = () => {
        spanWhoIsTyping.hide();
    };
    setTimeout(hideTyping, 6000);
}

function showHistory() {
    stompClient.send("/app/showHistory", {}, room);
}

function deleteHistory() {
    stompClient.send("/app/removeHistory", {}, room);
}

function showFullMessage(message) {
    const fullMessage = "Nickname: " + message.nickname + ", Room: " + message.room + ", Date: " + message.datetime_str + ", Content: '" + message.content + "'";
    $("#greetings").append("<tr><td>" + fullMessage + "</td></tr>");
}

function showFullMessages(fullMessages) {
    clearScreen();
    fullMessages.forEach(fullMessage => {
        showFullMessage(fullMessage);
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
    $("#spanWhoIsTyping").hide();
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
    $("#btnShowHistory").click(function () {
        showHistory();
    });
    $("#btnDeleteHistory").click(function () {
        deleteHistory();
    });
    $("#messageInput").change(function () {
        triggerTyping();
    });

    $("input[name='optradio']").on("change", function () {
        room = this.value;
        showHistory();
    });
});

