import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ProtectedRoute from "./components/ProtectedRoute";
import Navbar from "./components/Navbar.jsx"

import { AuthProvider } from "./context/AuthContext";
import { CartProvider } from "./context/CartContext";

import Login from "./pages/Login.jsx";
import Register from "./pages/Register";
import Home from "./pages/Home.jsx";
import Cart from "./pages/Cart.jsx";
import Orders from "./pages/Orders.jsx"

export default function App() {
  return (
    <AuthProvider>
      <CartProvider>
        <Router>
          <Navbar />
          <Routes>

            <Route path="/register" element={<Register />} />
            <Route path="/login" element={<Login />} />

            <Route path="/" element={<ProtectedRoute> <Home /> </ProtectedRoute>}/>

            <Route path = "/cart" element = {<ProtectedRoute> <Cart/> </ProtectedRoute>}></Route>
            <Route path = "/orders" element = {<ProtectedRoute> <Orders/> </ProtectedRoute>}></Route>


            

          </Routes>
        </Router>

      </CartProvider>
    </AuthProvider>

    
  );
}

