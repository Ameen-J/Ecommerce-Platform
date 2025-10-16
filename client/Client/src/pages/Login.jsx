import { useState } from "react";

import axios from "../api/api.js";
import { BrowserRouter as Router, Routes, Route, Link, useNavigate } from "react-router-dom";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await axios.post("/users/login", { email, password });
      localStorage.setItem("token", res.data);
      alert("Logged in successfully!");
      navigate("/"); 
    } catch (err) {
      alert("Login failed");
      console.error(err);
    }
  };

  return (
    <div className="container">
      <h2>Login</h2>
      <input placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} />
      <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} />
      <button onClick={handleLogin}>Login</button>
    </div>
  );
}
