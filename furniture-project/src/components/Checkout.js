import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import Navbar from "./Navbar";
import { emptyCart } from "../redux/cart";

export default function Checkout() {
    const dispatch = useDispatch();
    const userId = useSelector((state) => state.userId.id);
    const userCart = useSelector((state) => state.cart.value);
    const [orderList, setOrderList] = useState([]);
    const [totalPrice, setTotalPrice] = useState(0);
    let aggregatePrice = 0;

    useEffect(() => {
        fetch("http://localhost:3000/users", {
            method: "GET"
        })
        .then((response) => response.json())
        .then((data) => {
            setOrderList(data[userId - 1].orders);
            // console.log(data[userId - 1].orders);
        })  
    }, [])

    function checkoutHandler() {
        console.log(userCart);
        setOrderList((prevList) => [...prevList, userCart]);
        const newList = [...orderList, userCart];
        console.log(newList);
        fetch(`http://localhost:3000/users/${userId}`, {
            method: "PATCH",
            body: JSON.stringify({
                orders: newList
            }),
            headers: {
                'Content-type': 'application/json; charset=UTF-8', 
            },
        })
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            dispatch(emptyCart());
            alert("Items have been successfully ordered");
        });
    }

    return (
        <div>
            <Navbar />
            <div className="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
            <h1 className="display-4">Order Summary</h1>
            <div className="container">
                <div className="card-deck mb-3 text-center">
                {userCart.map((item) => {
                    aggregatePrice += item.price;
                    // setTotalPrice((i) => i += item.price);
                    return (
                    <div key={item.name} className="card mb-4 box-shadow">
                        <div className="card-header">
                            <h4 className="my-0 font-weight-normal">{item.name}</h4>
                        </div>
                        <div className="card-body">
                            <h1 className="card-title pricing-card-title">${item.price}</h1>
                        </div>
                    </div>
                )})}
                </div>
                <h3 className="display-7">Subtotal: ${aggregatePrice}</h3>
                <button type="button" className="btn btn-lg btn-block btn-outline-primary" onClick={() => checkoutHandler()} >Submit Order</button>
            </div>
            </div>
        </div>
        
    )
}
