import React from 'react';
import { Link } from 'react-router-dom';
// We will import its CSS later, e.g., import './Header.css';

function Header() {
    return (
        <header>
            <h1>Toyota Dealership</h1>
            <nav>
                <ul>
                    <li><Link to="/">Home</Link></li>
                    <li><Link to="/models">Models</Link></li>
                    <li><Link to="/about">About Us</Link></li>
                </ul>
            </nav>
        </header>
    );
}

export default Header;
