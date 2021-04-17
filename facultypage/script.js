
$(document).ready(function () {

    var numm = $(".ma").length;
    $("#num").html(numm);


    $("#b1").click(function(e){
        e.preventDefault();
        $("#cast").prop("disabled", false);
        $("#checkoff").prop("disabled", false);
        $("#b1").prop("disabled", true);
        $("#b2").prop("disabled", false);
        $("#status").html("Running");
        $ajax({
            type: "POST",
            url: "servlet.java",
            data: {status: true},
            success: function(){}
        });
    });

    $("#b2").click(function(e){
        e.preventDefault();
        $("#cast").prop("disabled", true);
        $("#checkoff").prop("disabled", true);
        $("#b1").prop("disabled", false);
        $("#b2").prop("disabled", true);
        $("#status").html("Closed");
        $ajax({
            type: "POST",
            url: "servlet.java",
            data: {status: false},
            success: function(){}
        });
    });

    $("#cast").click(function(e){
        e.preventDefault();
        var castdata = $("#castdata").val();
        $("#castdata").val('');
        $ajax({
            type: "POST",
            url: "servlet.java",
            data: castdata,
            dataType: "text",
            success: function(){}
        });
    });

    $("#checkoff").click(function(e){
        var ndata = $(this).next('div').serialize();
        $(this).next('div').remove();
        var numm = $(".ma").length;
        $("#num").html(numm);
        $ajax({
            type: "POST",
            url: "servlet.java",
            data: $(this).next('div').serialize(),
            success: function(){}
        });
        
    });




});