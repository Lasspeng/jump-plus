let currentUser = localStorage.getItem("currentUser");
currentUser = JSON.parse(currentUser);

const header = document.getElementById("welcome");
header.textContent = `Welcome, ${currentUser.fullname}`;

const checkings = document.getElementById("checkingsBal");
const savings = document.getElementById("savingsBal");
checkings.textContent = `Checkings Balance: $${Math.round(currentUser.checkings * 100) / 100}`;
savings.textContent = `Savings Balance: $${Math.round(currentUser.savings * 100) / 100}`;
