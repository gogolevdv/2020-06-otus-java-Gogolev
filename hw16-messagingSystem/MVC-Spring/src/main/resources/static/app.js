let stompClient = null;

const setConnected = (connected) => {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
}

const connect = (connected) => {
    if (connected) {

        console.log('Connected: ' + connected);
    } else {
        stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
        stompClient.connect({}, (frame) => {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe("/topic/response", (message) => showUser(JSON.parse(message.body)));
            stompClient.subscribe("/topic/userList", (message) => showMessage(JSON.parse(message.body)));
            stompClient.subscribe('/topic/saveUserOperationStatus',
                (operationStatusJson) => saveUserOperationStatus(operationStatusJson.body));
        });
    }
}

const saveUser = () => {
    let user = new Object();
    user.id = document.getElementById("userId").value;
    user.name = document.getElementById("name").value;
    user.age = document.getElementById("age").value;
    user.login = document.getElementById("login").value;
    user.password = document.getElementById("password").value;
    stompClient.send("/app/user/save", {}, JSON.stringify(user));
}

const saveUserOperationStatus = (operationStatus) => {
    console.log("operationStatus = " + operationStatus);

    if (operationStatus != null) {
        console.log("backToUserList()");
        toIndex();
    } else {
        $("#errorLines").html("");
        $("#errorLines").append("<tr><td>" + "error" + "</td></tr>");
        $("#errorsTable").show();
    }
}


const sendMsg = () => stompClient.send("/app/message." + $("#id").val(), {})

const sendMsgAllUsers = (connected) => {
    console.log("DDD");
    if (connected) {
        stompClient.send("/app/user/list", {}, {});
    } else {
        console.log("NOT Connected");
    }
}

const showUser = (message) => $("#oneUserTable").append("<tr>" +
    "<td>" + message.id + "</td>" +
    "<td>" + message.name + "</td>" +
    "<td>" + message.age + "</td>" +
    "<td>" + message.login + "</td>" +
    "<td>" + message.password + "</td>" +
    "</tr>")

const showMessage = (message) => {
    $("#userTable").html("");
    console.log(message.userList);
    message.userList.forEach(function (userList) {
        $("#userTable").append("<tr>" +
            "<td>" + userList.id + " </td>" +
            "<td>" + userList.name + " </td>" +
            "<td>" + userList.age + " </td>" +
            "<td>" + userList.login + " </td>" +
            "<td>" + userList.password + " </td>" +
            "</tr>>");
    })
}

const toIndex = () => {
    window.location.replace("index.html");
}

$(function () {
    $("#oneUser").click(sendMsg);
    $("#listUsers").click(sendMsgAllUsers);
    $("#save").click(saveUser);
});