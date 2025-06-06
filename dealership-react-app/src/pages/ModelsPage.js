import React from 'react';
import './ModelsPage.css';

function ModelsPage() {
    return (
        <>
            <h2>Our Car Models</h2>
            <div className="model-list">
                <div className="model-item">
                    {/* Image placeholder */}
                    <img src="/images/camry-model.jpg" alt="Toyota Camry" style={{width:'300px', height:'200px', backgroundColor:'#f0f0f0', objectFit:'cover'}} />
                    <h3>Toyota Camry</h3>
                    <p>A reliable and stylish sedan.</p>
                </div>
                <div className="model-item">
                    {/* Image placeholder */}
                    <img src="/images/rav4-model.jpg" alt="Toyota RAV4" style={{width:'300px', height:'200px', backgroundColor:'#f0f0f0', objectFit:'cover'}} />
                    <h3>Toyota RAV4</h3>
                    <p>A versatile SUV for all your adventures.</p>
                </div>
                <div className="model-item">
                    {/* Image placeholder */}
                    <img src="/images/tacoma-model.jpg" alt="Toyota Tacoma" style={{width:'300px', height:'200px', backgroundColor:'#f0f0f0', objectFit:'cover'}} />
                    <h3>Toyota Tacoma</h3>
                    <p>A rugged and capable pickup truck.</p>
                </div>
            </div>
        </>
    );
}
export default ModelsPage;
