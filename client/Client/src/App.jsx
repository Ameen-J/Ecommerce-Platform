import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Home from "./pages/Home.jsx";
import Login from "./pages/Login.jsx";
import Register from "./pages/Register.jsx";

function App() {
  return (
    <Router>
      <nav style={{ padding: "10px", textAlign: "center" }}>
        <Link to="/" style={{ margin: "0 10px" }}>Home</Link>
        <Link to="/login" style={{ margin: "0 10px" }}>Login</Link>
        <Link to="/register" style={{ margin: "0 10px" }}>Register</Link>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </Router>
  );
}

export default App;
