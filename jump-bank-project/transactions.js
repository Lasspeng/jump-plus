const checkingsDiv = document.getElementById("checkingsTrans");
const savingsDiv = document.getElementById("savingsTrans");
let currentUser = localStorage.getItem("currentUser");
currentUser = JSON.parse(currentUser);
const checkingsList = currentUser.checkingsLog;
const savingsList = currentUser.savingsLog;

for (let i = checkingsList.length - 1; i >= checkingsList.length - 5; i--) {
    const transaction = document.createElement("h4");
    transaction.textContent = checkingsList[i];
    checkingsDiv.appendChild(transaction);
}

for (let i = savingsList.length - 1; i >= savingsList.length - 5; i--) {
    const transaction = document.createElement("h4");
    transaction.textContent = savingsList[i];
    savingsDiv.appendChild(transaction);
}
