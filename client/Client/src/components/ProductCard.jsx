import { useState } from "react";
import { useCart } from "../context/CartContext";
import "../styles/ProductCard.css"

function ProductCard({ product }) {
  const { addToCart } = useCart();
  const [qty, setQty] = useState(1);

  
  const handleAdd = () => {
    if (qty < 1) return;
    addToCart(product.id, qty);
  };

  return (
    <div className="product-card">
      <img src={product.imageUrl} alt={product.name} className="product-img" />
      
      <h3>{product.name}</h3>
      <p>₹{product.price}</p>

    <div className="qty-row">
        <label>Qty:</label>
        <input
          type="number"
          min="1"
          value={qty}
          onChange={(e) => setQty(parseInt(e.target.value))}
          className="qty-input"
        />
      </div>

      <button onClick={handleAdd} className="add-btn">
        Add to Cart
      </button>
    </div>
  );
}

export default ProductCard;
