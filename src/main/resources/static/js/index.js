console.log("index.js file loaded");

// current year
function displayCurrentYear() {
    console.log("displayCurrentYear() called");
    const year = new Date().getFullYear();
    const element = document.querySelector('#year');
    element.textContent = String(year);
}

// Call the function to display the current year
displayCurrentYear();

// toggle scroll indicator
function toggleScrollIndicator() {
    window.addEventListener('scroll', function () {
        const scrollIndicator = document.querySelector('.scroll-indicator');
        if (window.pageYOffset > 100) { // Adjust '100' to your preference
            scrollIndicator.style.display = 'none';
        } else {
            scrollIndicator.style.display = 'block';
        }
    });
}

// Call the function when the page loads
toggleScrollIndicator();
// end of toggle scroll indicator

// check if modal has been shown
// check if modal has been shown
document.addEventListener("DOMContentLoaded", function () {
    console.log("window.location.origin: " + window.location.origin);
    fetch(`${window.location.origin}/whats-new/content`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const storedVersion = localStorage.getItem('whatsNewVersion');
            if (storedVersion !== data.currentVersion) {
                const modalBody = document.getElementById('whatsNewModal').querySelector('.modal-body');
                let content = ``;
                data.updates.forEach(update => {
                    content += `<p class="fw-bold mb-0">${update.version}</p>
                                <p>${update.date}</p>
                                <ul>`;
                    update.details.forEach(change => {
                        content += `<li>${change}</li>`;
                    });
                    content += `</ul>`;
                });
                modalBody.innerHTML = content;
                const whatsNewModalElement = document.getElementById('whatsNewModal');

                const whatsNewModal = new bootstrap.Modal(document.getElementById('whatsNewModal'), {});
                whatsNewModal.show();

                whatsNewModalElement.addEventListener('hidden.bs.modal', function () {
                    const modalBackdrop = document.querySelector('.modal-backdrop');
                    if (modalBackdrop) {
                        modalBackdrop.parentNode.removeChild(modalBackdrop);
                    }
                    localStorage.setItem('whatsNewVersion', data.currentVersion);
                });
            }
        })
        .catch(error => console.error('Error loading what\'s new content:', error));
});
// end of check if modal has been shown

// end of check if modal has been shown

// a method to remove the overlay when the modal is closed
// document.addEventListener("DOMContentLoaded", function () {
//         const whatsNewModal = document.getElementById('whatsNewModal');
//         if (whatsNewModal) {
//             whatsNewModal.addEventListener('hidden.bs.modal', function () {
//                 const modalBackdrop = document.querySelector('.modal-backdrop');
//                 if (modalBackdrop) {
//                     modalBackdrop.parentNode.removeChild(modalBackdrop);
//                 }
//             });
//         }
//     }
// );
// end of a method to remove the overlay when the modal is closed