import { createContext, useContext, useState, useEffect } from "react";
import { useAuth } from "./AuthContext.jsx";
import { getCart, addToCartService, removeFromCartService, clearCartService } from "../services/cartService.js";
import { checkoutCart } from "../services/orderService.js";


const CartContext = createContext();

export function CartProvider({ children }) {
  const { user } = useAuth();
  const [cart, setCart] = useState({ items: [], totalPrice: 0 });

  useEffect(() => {
    if (user) {
      loadCart();
    } else {
      setCart({ items: [], totalPrice: 0 });
    }
  }, [user]);

 
  const loadCart = async () => {
    try {
      const cartData = await getCart();
      setCart(cartData);
    } catch (err) {
      console.error("Failed to load cart");
    }
  };

  const addToCart = async (productId, quantity = 1) => {    
    const updatedCart = await addToCartService(productId, quantity);
    setCart(updatedCart);
  };

  const removeFromCart = async (itemId) => {
    const updatedCart = await removeFromCartService(itemId);
    setCart(updatedCart);
  };

  const clearCart = async () => {
    await clearCartService();
    setCart({ items: [], totalPrice: 0 });
  };

  const checkout = async () =>{
    setCart ({ items: [], totalPrice: 0 });
    await checkoutCart();
  }


  return (
    <CartContext.Provider value={{ cart, addToCart, removeFromCart, clearCart , checkout}}>
      {children}
    </CartContext.Provider>
  );
}

export function useCart() {
  return useContext(CartContext);
}
