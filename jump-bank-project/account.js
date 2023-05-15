let currentUser = localStorage.getItem("currentUser");
currentUser = JSON.parse(currentUser);
const infoDiv = document.getElementById("information");

const userName = document.createElement("h3");
userName.textContent = `Account Owner: ${currentUser.fullname}`
infoDiv.appendChild(userName);

const email = document.createElement("h3");
email.textContent = `Email: ${currentUser.email}`;
infoDiv.appendChild(email);

const checkings = document.createElement("h3");
checkings.textContent = currentUser.checkings ? "Checkings Account: Active" : "Checkings Account: Inactive";
infoDiv.appendChild(checkings);

const savings = document.createElement("h3");
savings.textContent = currentUser.savings ? "Savings Account: Active" : "Savings Account: Inactive";
infoDiv.appendChild(savings);
