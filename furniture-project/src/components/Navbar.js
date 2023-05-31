import { Link } from "react-router-dom";

export default function Navbar() {
    return (
        <div className="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
        <h5 className="my-0 mr-md-auto font-weight-normal" style={{marginRight: "15px"}}><Link to="/">Furniture App</Link></h5>
        <nav className="my-2 my-md-0 mr-md-3">
            <Link className="p-2 text-dark" to="/checkout">Checkout</Link>
            <Link className="p-2 text-dark" to="/history" style={{marginRight: "15px"}}>Order History</Link>
            {/* <Link className="p-2 text-dark" to="">Support</Link> */}
            {/* <Link className="p-2 text-dark" to="">Pricing</Link> */}
        </nav>
        <Link className="btn btn-outline-primary" to="/signin">Sign In</Link>
    </div>
    )
}
