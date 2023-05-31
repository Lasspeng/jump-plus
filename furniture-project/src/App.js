import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import logo from './logo.svg';
// import './App.css';
import SignIn from './components/SignIn';
import { useState } from 'react';
import Home from "./components/Home";
import Checkout from "./components/Checkout";
import OrderHistory from "./components/OrderHistory";

function App() {
  const [currentUserId, setCurrentUserId] = useState(0);

  function setId(x) {
    setCurrentUserId(x);
  }
  return (
    <BrowserRouter>
      <Routes>
        <Route exact path="/" element={<Home />}></Route>
        <Route exact path="/signin" element={<SignIn />} ></Route>
        <Route exact path="/checkout" element={<Checkout />}></Route>
        <Route exact path="/history" element={<OrderHistory />}></Route>
      </Routes>
    
    </BrowserRouter>
  );
}

export default App;
