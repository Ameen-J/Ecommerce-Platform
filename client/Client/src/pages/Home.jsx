import { useState } from "react";
import ProductList from "../components/ProductList.jsx";
import Cart from "../components/Cart.jsx";

export default function Home() {
  const [cart, setCart] = useState([]);

  const addToCart = (product) => {
    setCart(prev => {
      const exists = prev.find(p => p.id === product.id);
      if (exists) {
        return prev.map(p => p.id === product.id ? { ...p, quantity: p.quantity + 1 } : p);
      }
      return [...prev, { ...product, quantity: 1 }];
    });
  };

  const clearCart = () => setCart([]);

  return (
    <div className="container">
      <h1>Products</h1>
      <ProductList addToCart={addToCart} />
      <Cart cart={cart} clearCart={clearCart} />
    </div>
  );
}
