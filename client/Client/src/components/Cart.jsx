import axios from "../api/api.js";

export default function Cart({ cart, clearCart }) {
  const placeOrder = async () => {
    if (!cart.length) return alert("Cart is empty");

    const order = { items: cart.map(item => ({ product: { id: item.id }, quantity: item.quantity })) };

    try {
      await axios.post("/orders/place", order);
      alert("Order placed successfully!");
      clearCart();
    } catch (err) {
      alert("Order failed: " + (err.response?.data || err.message));
      console.error(err);
    }
  };

  return (
    <div>
      <h2>Cart</h2>
      {cart.map(item => (
        <div key={item.id}>
          <p>{item.name} x {item.quantity}</p>
        </div>
      ))}
      <button onClick={placeOrder}>Place Order</button>
    </div>
  );
}
