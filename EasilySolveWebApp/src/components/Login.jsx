import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import authentication from '../services/api/authentication'; 

const Login = () => {
    const [email, setEmail] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            await authentication.login(email); 
            navigate('/dashboard'); 
        } catch (error) {
            console.error('Login failed', error);
        }
    };

    return (
        <form onSubmit={handleLogin}>
            <input
                type="email"
                placeholder="Email"
                value={email}
                onChange={(e) =>setEmail(e.target.value)}
            />
            <button type="submit">Login</button>
        </form>
    );
};

export default Login;