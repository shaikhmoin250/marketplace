import React, { useState } from 'react'; // Imported useState
import './HomePage.css';

function HomePage() {
    const images = [
        "/images/sample-car1.jpg", // Assuming images will be in public/images/
        "/images/sample-car2.jpg",
        "/images/sample-car3.jpg"
    ];

    const [currentImageIndex, setCurrentImageIndex] = useState(0);

    const goToPrevious = () => {
        setCurrentImageIndex(prevIndex =>
            prevIndex === 0 ? images.length - 1 : prevIndex - 1
        );
    };

    const goToNext = () => {
        setCurrentImageIndex(prevIndex =>
            prevIndex === images.length - 1 ? 0 : prevIndex + 1
        );
    };

    // Dynamic alt text based on image name
    const currentImageName = images[currentImageIndex].split('/').pop().split('.')[0];

    return (
        <>
            <section id="carousel-section">
                <h2>Latest Models</h2>
               <div className="carousel-container">
                   <img
                        src={images[currentImageIndex]}
                        alt={`Latest Model: ${currentImageName}`} // Updated alt text
                        id="carousel-image"
                        style={{width:'100%', height:'auto', maxHeight: '400px', objectFit: 'cover'}}
                    />
                   <button id="prev-btn" onClick={goToPrevious}>Previous</button>
                   <button id="next-btn" onClick={goToNext}>Next</button>
               </div>
            </section>

            <section id="services-section">
                <h2>Our Services</h2>
                <ul>
                    <li>New Car Sales</li>
                    <li>Used Car Sales</li>
                    <li>Financing</li>
                    <li>Maintenance and Repair</li>
                    <li>Parts and Accessories</li>
                </ul>
            </section>

            <section id="testimonials-section">
                <h2>Customer Testimonials</h2>
                <div className="testimonial">
                    <p>"Great experience buying my new Camry! The staff was friendly and helpful." - John D.</p>
                </div>
                <div className="testimonial">
                    <p>"Excellent service department. They fixed my RAV4 quickly and at a fair price." - Jane S.</p>
                </div>
            </section>
        </>
    );
}
export default HomePage;
