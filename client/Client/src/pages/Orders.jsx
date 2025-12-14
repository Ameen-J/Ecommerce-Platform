import { useEffect, useState } from "react";
import { getUserOrders } from "../services/orderService";
import "../styles/Orders.css";

const Orders = () => {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    loadOrders();
  }, []);

  const loadOrders = async () => {
    try {
      const data = await getUserOrders();
      setOrders(data);
    } catch {
      alert("Failed to load orders");
    }
  };

  return (
    <div className="orders-container">
      <h2>Your Orders</h2>

      {orders.length === 0 ? (
        <p>No orders yet.</p>
      ) : (
        orders.map((order) => (
          <div key={order.id} className="order-card">
            <div className="order-header">
              <span><strong>Order ID:</strong> {order.id}</span>
              <span><strong>Date:</strong> {order.createdAt.split("T")[0]}</span>
              <span><strong>Total:</strong> ₹{order.totalPrice.toFixed(2)}</span>
            </div>

            <div className="order-items">
              {order.items.map((item) => (
                <div key={item.id} className="order-item">
                  <img src={item.product.imageUrl} alt={item.product.name} />
                  <div>
                    <p>{item.product.name}</p>
                    <p>Qty: {item.quantity}</p>
                    <p>₹{item.totalPrice.toFixed(2)}</p>
                  </div>
                </div>
              ))}
            </div>

          </div>
        ))
      )}
    </div>
  );
};

export default Orders;
