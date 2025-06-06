import React from 'react';
import './AboutPage.css';

function AboutPage() {
    return (
        <>
            <h2>About Our Dealership</h2>
            <section id="dealership-info">
                <h3>Our Location</h3>
                <p>123 Toyota Drive, Anytown, USA 12345</p>
                <div id="map-placeholder" style={{width:'600px', height:'400px', backgroundColor:'#f0f0f0', textAlign:'center', lineHeight:'400px'}}>
                    [Google Maps Placeholder]
                </div>

                <h3>Contact Us</h3>
                <p><strong>Sales:</strong> (555) 123-4567</p>
                <p><strong>Service:</strong> (555) 123-4568</p>
                <p><strong>Parts:</strong> (555) 123-4569</p>

                <h3>Hours of Operation</h3>
                <p>Monday - Friday: 9:00 AM - 7:00 PM</p>
                <p>Saturday: 9:00 AM - 6:00 PM</p>
                <p>Sunday: Closed</p>
            </section>
        </>
    );
}
export default AboutPage;
