import React, { useEffect, useState } from "react";
import axios from "axios";
import "../styles/ProductList.css";

export default function ProductList() {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState({}); // { productId: quantity }

  useEffect(() => {
    axios.get("http://localhost:8080/api/products")
      .then(res => setProducts(res.data))
      .catch(err => console.error(err));
  }, []);

  const addToCart = (product) => {
    setCart(prev => ({
      ...prev,
      [product.id]: prev[product.id] ? prev[product.id] + 1 : 1
    }));
  };

  const totalItems = Object.values(cart).reduce((a, b) => a + b, 0);

  return (
    <div>
      <div className="cart-bar">
        <h3>Cart: {totalItems} item{totalItems !== 1 ? "s" : ""}</h3>
      </div>

      <div className="product-grid">
        {products.map(product => (
          <div key={product.id} className="product-card">
            <img src={product.imageUrl} alt={product.name} />
            <h3>{product.name}</h3>
            <p className="price">${product.price}</p>
            <p className={`stock ${product.stock > 0 ? "in-stock" : "out-of-stock"}`}>
              {product.stock > 0 ? `Stock: ${product.stock}` : "Out of stock"}
            </p>
            <button
              disabled={product.stock === 0}
              onClick={() => addToCart(product)}
            >
              Add to Cart
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
