function reactive() {
    document.write("hello world");
}

function function1() {
    var date = new Date();
    document.write("this is"+date);
}

function add() {
    var one = parseInt(document.getElementById("one").value);
    var two = parseInt(document.getElementById("two").value);
    document.getElementById("three").value = one+two;
}

function roundBack() {
    history.back();
}

function hide1() {
    document.getElementById("div_h1").style.display="none";
}

function show1() {
    document.getElementById("div_h1").style.display="block";
}

// 账号不能为空
function notNull() {
    var text = document.getElementById("account").value;
    if (0==text.length){
        alert("账号不能为空！");
        return false;
    }
    return true;
}

// JSON键名可以不用括号
function json() {
    var gareen = {name:"盖伦",hp:616};
    document.write(gareen.name+":"+gareen.hp);
    var heroes = [{name:"概论",hp:616},{name:"haha",hp:95}];
    for (var i=0;i<heroes.length;i++){
        document.write(heroes[i].name+"------->"+heroes[i].hp);
    }
}