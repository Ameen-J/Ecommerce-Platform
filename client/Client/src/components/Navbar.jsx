import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { useCart } from "../context/CartContext";
import "../styles/Navbar.css";

export default function Navbar() {
  const { user, logout } = useAuth();
  const { cart } = useCart();

  return (
    <nav className="navbar">
      <Link to="/" className="logo">ShopEase</Link>

      <div className="nav-links">
        <Link to="/">Home</Link>
        {user && <Link to="/orders">Orders</Link>}
        <Link to="/cart">Cart ({cart?.items?.length || 0})</Link>
      </div>

      <div className="auth-section">
        {user ? (
          <>
            <span className="username">{user.name}</span>
            <button className="logout-btn" onClick={logout}>Logout</button>
          </>
        ) : (
          <Link to="/login" className="login-btn">Login</Link>
        )}
      </div>
    </nav>
  );
}
