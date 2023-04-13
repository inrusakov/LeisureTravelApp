$("#toVoteID").keydown(function () {
    if (event.keyCode == "13") {
        toVoteID();
    }
});

$("#gotoVoteID").click(function () {
    toVoteID();
});

function toVoteID() {
    $("#toVoteID").modal("hide");
    var voteID = $("#voteID").val();
    $.ajax({
        url: "/checkVoteID/" + voteID,
        success: function (data) {
            if (data == 1) {
                //Exist
                location.href = "/vote/en/" + voteID;
            } else if (data == 0) {
                //Not exist
                alert("VoteID not found! Please try again.")
            }
        }
    });
}

$("#toRouteID").keydown(function () {
    if (event.keyCode == "13") {
        toRouteID();
    }
});

$("#gotoRouteID").click(function () {
    toRouteID();
});

function toRouteID() {
    $("#toRouteID").modal("hide");
    let routeID = $("#routeID").val();
    $.ajax({
        url: "/checkRouteID/" + routeID,
        success: function (data) {
            if (data == 1) {
                //Exist
                location.href = "/routeObserver/" + routeID;
            } else if (data == 0) {
                //Not exist
                alert("VoteID not found! Please try again.")
            }
        }
    });
}