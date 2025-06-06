document.addEventListener('DOMContentLoaded', function() {
    function loadHTML(filePath, elementId) {
        const element = document.getElementById(elementId);
        if (element) {
            fetch(filePath)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok for ' + filePath);
                    }
                    return response.text();
                })
                .then(data => {
                    element.innerHTML = data;
                    // If loading header, re-attach nav event listeners if any were dynamically added
                    // (not an issue with current simple nav links, but good practice for complex components)
                    if (elementId === 'header-placeholder') {
                        // Example: if nav links were added dynamically and needed JS event listeners
                        // initializeNavigation();
                    }
                })
                .catch(error => {
                    console.error('Error loading HTML:', error);
                    element.innerHTML = `<p>Error loading content from ${filePath}. Please check console.</p>`;
                });
        }
    }

    loadHTML('partials/header.html', 'header-placeholder');
    loadHTML('partials/footer.html', 'footer-placeholder');
});
