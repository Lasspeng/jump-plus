import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import Navbar from "./Navbar";

export default function OrderHistory() {
    const [orders, setOrders] = useState([]);
    const userId = useSelector((state) => state.userId.id);
    let counter = 0;

    useEffect(() => {
        fetch("http://localhost:3000/users", {
            method: "GET"
        })
        .then((response) => response.json())
        .then((data) => {
            setOrders(data[userId - 1].orders);
        })  
    }, [])
    
    return (
        <div>
            <Navbar />
            <div className="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
                <h1 className="display-4">Order History</h1>
            </div>
            <div className="container">
                {orders.map((order) => {
                    return (
                        <div key={counter}>
                            <h3 className="display-7">Order No. {++counter}</h3>
                            <ul>
                                {order.map((item) => { return (
                                    <li>Name: {item.name} <br></br>Price: ${item.price}</li>
                                    )})}
                            </ul>
                        </div>
                )})}
            </div>
        </div>
    )
}
