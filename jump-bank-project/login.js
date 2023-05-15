// localStorage.clear();
class User {
    email;
    password;
    fullname;
    checkings;
    savings;
    checkingsLog
    savingsLog;
    constructor(email, password, fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.checkings = 0;
        this.savings = 0;
        this.checkingsLog = [];
        this.savingsLog = [];
    }
}
const users = [];
users.push(new User("JohnDoe@gmail.com", "JohnD", "John Doe"));
users.push(new User("GLiburd@gmail.com", "GavinL", "Gavin Liburd"));

function loginToAccount(e) {
    e.preventDefault;
    if (localStorage.getItem("userList") === null) { 
        localStorage.setItem("userList", JSON.stringify(users));
    }
    const retrievedData = localStorage.getItem("userList");
    localStorageList = JSON.parse(retrievedData);

    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    for (let user of localStorageList) {
        if (user.email === email && user.password === password) {
            localStorage.setItem("currentUser", JSON.stringify(user));
            // alert("Hi");
            return true;
        }
    }
    alert("Login credentials were wrong. Try again");
    return false;
        
}
