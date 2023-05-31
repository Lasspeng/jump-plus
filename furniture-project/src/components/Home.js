import { Link } from "react-router-dom"
import { useDispatch, useSelector } from 'react-redux';
import { addToCart } from "../redux/cart";
import Navbar from "./Navbar";

export default function Home() {

    const dispatch = useDispatch();

    const furnitureItems = [
        {name: "Chime 12 Inch Mattress",price: 229.99}, 
        {name: "Safavieh Rime Mirror", price: 62.99},
        {name: "Shavon Table Lamp", price: 109.99}, 
        {name: "Altari Sofa", price: 399.99}, 
        {name: "Wystfield TV Stand", price: 730.00}
    ];

    function addToCartHandler(item) {
        dispatch(addToCart(item));
        console.log(item); 
        alert("Item has been successfully added to your cart. Proceed to checkout when ready to order");
    }
    return (
        <div>
            <Navbar />
            <div className="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
                <h1 className="display-4">Furniture Catalog</h1>
            </div>
          <div className="container">
            <div className="card-deck mb-3 text-center">
                {furnitureItems.map((item) => { return (
                    <div key={item.name} className="card mb-4 box-shadow">
                        <div className="card-header">
                            <h4 className="my-0 font-weight-normal">{item.name}</h4>
                        </div>
                        <div className="card-body">
                            <h1 className="card-title pricing-card-title">${item.price}</h1>
                            <button type="button" className="btn btn-lg btn-block btn-outline-primary" onClick={() => addToCartHandler(item)} >Add to Cart</button>
                        </div>
                    </div>
                )})}
            </div>
          </div> 
        </div>
    
    )
}
