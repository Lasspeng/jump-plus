function transferHandler(e) {
    e.preventDefault;
    const radios = document.getElementsByName("account");
    const transferAmount = Number(document.getElementById("amount").value);
    let currentUser = localStorage.getItem("currentUser");
    currentUser = JSON.parse(currentUser);
    let userList = localStorage.getItem("userList");
    userList = JSON.parse(userList);
    let userIndex = 0;

    for (userIndex; userIndex < userList.length; userIndex++) {
        if (currentUser.email === userList[userIndex].email) break;
    }

    if (!transferAmount) {
        alert("Transfer could not be made. Input a number.");
        return false;
    }
    if (radios[0].checked && !radios[1].checked) {
        let oldCheckings = Number(currentUser.checkings);
        currentUser.checkings = oldCheckings + transferAmount;
        currentUser.checkingsLog.push(`Transfer in for $${transferAmount}`);

        let oldSavings = Number(currentUser.savings);
        currentUser.savings = oldSavings - transferAmount;
        currentUser.savingsLog.push(`Transfer out for $(${transferAmount})`);
        userList[userIndex] = currentUser;
        localStorage.setItem("currentUser", JSON.stringify(currentUser));
        localStorage.setItem("userList", JSON.stringify(userList));
        alert("Money successfully transferred.");
        return true;
    } else if (!radios[0].checked && radios[1].checked) {
        let oldCheckings = Number(currentUser.checkings);
        currentUser.checkings = oldCheckings - transferAmount;
        currentUser.checkingsLog.push(`Transfer out for $(${transferAmount})`);
        userList[userIndex] = currentUser;

        let oldSavings = Number(currentUser.savings);
        currentUser.savings = oldSavings + transferAmount;
        currentUser.savingsLog.push(`Transfer in for $${transferAmount}`);
        userList[userIndex] = currentUser;
        localStorage.setItem("currentUser", JSON.stringify(currentUser));
        localStorage.setItem("userList", JSON.stringify(userList));
        alert("Money successfully transferred.");
        return true;
    }
    alert("Transfer could not be made. Choose an account to transfer to.");
    return false;
}
