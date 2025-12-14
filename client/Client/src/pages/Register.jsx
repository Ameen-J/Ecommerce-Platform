import { useState } from "react";
import { registerUser } from "../services/authService";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import "../styles/Register.css";

function Register() {
  const { login } = useAuth();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    email: "",
    password: ""
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const token = await registerUser(form);
      await login(token);  
      navigate("/");
    } catch (err) {
      alert("Registration failed. Email may already exist.");
    }
  };

  return (
    <div className="auth-container">
      <form className="auth-form" onSubmit={handleRegister}>
        <h2>Create Account</h2>

        <input
          type="text"
          name="name"
          placeholder="Full Name"
          value={form.name}
          onChange={handleChange}
          required
        />

        <input
          type="email"
          name="email"
          placeholder="Email Address"
          value={form.email}
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
          required
        />

        <button type="submit">Register</button>

        <p onClick={() => navigate("/login")} className="switch-link">
          Already have an account? Login
        </p>
      </form>
    </div>
  );
}

export default Register;
