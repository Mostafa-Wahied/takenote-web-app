console.log("reset_password.js loaded");

function checkPasswordMatch(element) {
    var password = document.getElementById("password").value;
    var confirmPassword = element.value;
    var submitButton = document.querySelector('input[type="submit"]');
    var form = document.querySelector('form');

    if (password !== confirmPassword) {
        // Display an error message
        element.setCustomValidity("Passwords do not match");
        // Show the error message
        form.reportValidity();
        // Disable the submit button
        submitButton.disabled = true;
    } else {
        // Clear any error messages
        element.setCustomValidity("");
        // Enable the submit button
        submitButton.disabled = false;
    }
}
