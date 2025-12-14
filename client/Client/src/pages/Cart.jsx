import { useCart } from "../context/CartContext";
import { useNavigate } from "react-router-dom";
import "../styles/Cart.css";


export default function Cart() {
  const { cart, addToCart, removeFromCart, clearCart, checkout } = useCart();
  const navigate = useNavigate();

  if (!cart || cart.items.length === 0) {
    return <h2 style={{ textAlign: "center", marginTop: "40px" }}>Your cart is empty 🛍️</h2>;
  }

const handleCheckout = async() =>{
  await checkout();
  navigate("/orders")
}

const handleAddToCart = (productId,offset,quantity) =>{
  if(quantity + offset === 0){
    alert("Quantity should be more than 0");
  }
  else{
    addToCart(productId,offset);
  }
}

  return (
    <div className="cart-container">
      <h1 className="cart-title">Your Cart</h1>

      {cart.items.map((item) => (
        <div key={item.id} className="cart-item">
          <div className="cart-item-left">
            <img src={item.product.imageUrl || "https://placehold.co/80"} alt={item.product.name} />
            <div>
              <h3>{item.product.name}</h3>
              <p>₹{item.product.price}</p>
            </div>
          </div>

          <div className="cart-qty-controls">
            <button className="cart-btn" onClick={() => handleAddToCart(item.product.id, -1,item.quantity)}>-</button>
            <span>{item.quantity}</span>
            <button className="cart-btn" onClick={() => handleAddToCart(item.product.id, 1,item.quantity)}>+</button>
          </div>

          <button className="cart-btn remove-btn" onClick={() => removeFromCart(item.id)}>
            Remove
          </button>
        </div>
      ))}

      <h2 className="cart-total">Total: ₹{cart.totalPrice}</h2>
      <div className="btn-container">
        <button className="clear-cart-btn" onClick={clearCart}>
          Clear Cart
        </button>

        <button className="checkout-btn" onClick={handleCheckout}>
          Ckeckout
        </button>
      </div>
      

    </div>
  );
}
