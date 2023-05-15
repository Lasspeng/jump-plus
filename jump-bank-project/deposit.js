function depositHandler(e) {
    e.preventDefault;
    const radios = document.getElementsByName("account");
    const depositAmount = Number(document.getElementById("amount").value);
    let currentUser = localStorage.getItem("currentUser");
    currentUser = JSON.parse(currentUser);
    let userList = localStorage.getItem("userList");
    userList = JSON.parse(userList);
    let userIndex = 0;
    
    for (userIndex; userIndex < userList.length; userIndex++) {
        if (currentUser.email === userList[userIndex].email) break;
    }

    if (!depositAmount) {
        alert("Deposit could not be made. Input a number.");
        return false;
    }
    if (radios[0].checked) {
        let oldCheckings = Number(currentUser.checkings);
        currentUser.checkings = oldCheckings + depositAmount;
        currentUser.checkingsLog.push(`Deposit for: $${depositAmount}`);
        userList[userIndex] = currentUser;
        localStorage.setItem("currentUser", JSON.stringify(currentUser));
        localStorage.setItem("userList", JSON.stringify(userList))
        alert("Money successfully deposited.");
        return true;
    } else if (radios[1].checked) {
        let oldSavings = Number(currentUser.savings);
        currentUser.savings = oldSavings + depositAmount;
        currentUser.savingsLog.push(`Deposit for: $${depositAmount}`);
        userList[userIndex] = currentUser;
        localStorage.setItem("currentUser", JSON.stringify(currentUser));
        localStorage.setItem("userList", JSON.stringify(userList))
        alert("Money successfully deposited.");
        return true;
    }
    alert("Deposit could not be made. Choose an account to deposit to.");
    return false;
}
