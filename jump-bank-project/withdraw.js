function withdrawalHandler(e) {
    e.preventDefault;
    const radios = document.getElementsByName("account");
    const withdrawAmount = Number(document.getElementById("amount").value);
    let currentUser = localStorage.getItem("currentUser");
    currentUser = JSON.parse(currentUser);
    let userList = localStorage.getItem("userList");
    userList = JSON.parse(userList);
    let userIndex = 0;
    
    for (userIndex; userIndex < userList.length; userIndex++) {
        if (currentUser.email === userList[userIndex].email) break;
    }

    if (!withdrawAmount) {
        alert("Withdrawal could not be made. Input a number.");
        return false;
    }
    if (radios[0].checked) {
        let oldCheckings = Number(currentUser.checkings);
        currentUser.checkings = oldCheckings - withdrawAmount;
        currentUser.checkingsLog.push(`Withdrawal for: $(${withdrawAmount})`);
        userList[userIndex] = currentUser;
        localStorage.setItem("currentUser", JSON.stringify(currentUser));
        localStorage.setItem("userList", JSON.stringify(userList))
        alert("Money successfully withdrawn.");
        return true;
    } else if (radios[1].checked) {
        let oldSavings = Number(currentUser.savings);
        currentUser.savings = oldSavings - withdrawAmount;
        currentUser.savingsLog.push(`Withdrawal for: $(${withdrawAmount})`);
        userList[userIndex] = currentUser;
        localStorage.setItem("currentUser", JSON.stringify(currentUser));
        localStorage.setItem("userList", JSON.stringify(userList))
        alert("Money successfully withdrawn.");
        return true;
    }
    alert("Withdrawal could not be made. Choose an account to deposit to.");
    return false;
}
