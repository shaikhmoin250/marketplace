document.addEventListener('DOMContentLoaded', function() {
    const carouselImage = document.getElementById('carousel-image');
    const prevBtn = document.getElementById('prev-btn');
    const nextBtn = document.getElementById('next-btn');

    if (!carouselImage || !prevBtn || !nextBtn) {
        // Elements not found, likely not on the homepage or HTML structure changed
        console.log("Carousel elements not found. Ensure you are on the homepage and HTML is correct.");
        return;
    }

    const images = [
        "images/sample-car1.jpg", // Placeholder path
        "images/sample-car2.jpg", // Placeholder path
        "images/sample-car3.jpg"  // Placeholder path
    ];
    let currentImageIndex = 0;

    function showImage() {
        carouselImage.src = images[currentImageIndex];
        carouselImage.alt = `Image of ${images[currentImageIndex].split('/').pop().split('.')[0]}`; // Basic alt text
    }

    prevBtn.addEventListener('click', function() {
        currentImageIndex = (currentImageIndex - 1 + images.length) % images.length;
        showImage();
    });

    nextBtn.addEventListener('click', function() {
        currentImageIndex = (currentImageIndex + 1) % images.length;
        showImage();
    });

    // Initial image display
    showImage();

    // Optional: Auto-cycle (can be combined with manual navigation)
    // setInterval(() => {
    //     nextBtn.click();
    // }, 5000); // Auto-cycle every 5 seconds

    console.log("Carousel script loaded and updated for image tags.");
});
