import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import HomePage from './pages/HomePage';
import ModelsPage from './pages/ModelsPage';
import AboutPage from './pages/AboutPage';
// We will import global CSS later, e.g., import './App.css';

function App() {
    return (
        <Router>
            <Layout>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/models" element={<ModelsPage />} />
                    <Route path="/about" element={<AboutPage />} />
                </Routes>
            </Layout>
        </Router>
    );
}

export default App;
