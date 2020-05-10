$(function () {
    $("#button-business").click(function () {
        window.location.href='/button?button=business';
    })

    $("#button-component").click(function () {
        window.location.href='/button?button=component';
    })

    $("#button-supplier").click(function () {
        window.location.href='/button?button=supplier';
    })

    $("#button-report").click(function () {
        window.location.href='/button?button=report';
    })
})