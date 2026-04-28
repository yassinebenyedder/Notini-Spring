import React, { useState } from 'react';
import './Login.css';
import { useCookies } from 'react-cookie';
import Axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import img1 from '../assets/login.webp';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [, setCookies] = useCookies(['access_token']);
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const navigate = useNavigate();
 
  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMessage('');
    setSuccessMessage('');
  
    try {
      const res = await Axios.post(`${process.env.REACT_APP_API_BASE_URL}/authentication/login`, {
        email: email,
        password: password,
      });
      
      console.log("Response status:", res.status);
      console.log("Response data:", res.data);
      
      // Check if login was successful (Spring returns null for failed login)
      if (res.status === 200 && res.data && res.data.token) {
        setCookies('access_token', res.data.token, { path: '/',maxAge: 3600});
        window.localStorage.setItem('userID', res.data.id);
        window.localStorage.setItem('username', res.data.username);
        setSuccessMessage('Login successful!');
        
        console.log("Login successful, navigating to /");

        // Redirect after setting cookies
        setTimeout(() => {
          // Clear the cookie
          setCookies('access_token', '', { path: '/', maxAge: -1 }); 
          // Redirect to login page
          navigate('/login'); 
        }, 3600000);//after one hour the token expire so you have to login again
        
        navigate('/');
      } else {
        console.log("Login failed - no data or token");
        setErrorMessage('Invalid email or password.');
      }
    } catch (error) {
      console.error("Login error:", error);
      if (error.response && error.response.data && error.response.data.message) {
        setErrorMessage(error.response.data.message);
      } else {
        setErrorMessage('Invalid email or password.');
      }
      console.log("login failed");
    }
  };
  return (
    <div className="login-page">
      <div className="login-container">
        <img src={img1} alt='login img' className="login-image" />
        <form className="login-form" onSubmit={handleSubmit}>
          <h2>Welcome Back</h2>
          <p>Please login to your account</p>

          {errorMessage && <p className="error-message" style={{ color: 'red' }}>{errorMessage}</p>}
          {successMessage && <p className="success-message" style={{ color: 'green' }}>{successMessage}</p>}
          
          <div className="input-group">
            <label htmlFor="email">Email</label>
            <input 
              type="email" 
              id="email" 
              value={email} 
              onChange={(e) => setEmail(e.target.value)} 
              required 
              placeholder="Enter your email"
            />
          </div>

          <div className="input-group">
            <label htmlFor="password">Password</label>
            <input 
              type="password" 
              id="password" 
              value={password} 
              onChange={(e) => setPassword(e.target.value)} 
              required 
              placeholder="Enter your password"
            />
            <h5 className="register-text">
              You don't have an account? 
              <Link to="/register" className="register-link"> Create one</Link>
            </h5>
          </div>

          <button type="submit" className="login-button">Login</button>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
