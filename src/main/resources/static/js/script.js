$(document).ready(function () {
    var date_input = $('input[name="date"]'); //our date input has the name "date"
    var container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
    var options = {
        format: 'mm/dd/yyyy',
        container: container,
        todayHighlight: true,
        autoclose: true,
    };
    date_input.datepicker(options);
})


// populating the reading level dropdown menu on forms
const alphabetDropdown = document.querySelector("#letter");
const alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
for (let letter of alphabet) {
    let optionHtmlElement = document.createElement("option");
    optionHtmlElement.value = letter.toLowerCase();
    optionHtmlElement.textContent = letter;
    alphabetDropdown.append(optionHtmlElement);
}

// setting active state to navbar pages
// Get the container element
var btnContainer = document.getElementsByClassName("nav-item");

// Get all buttons with class="btn" inside the container
var btns = btnContainer.getElementsByClassName("nav-link");

// Loop through the buttons and add the active class to the current/clicked button
for (var i = 0; i < btns.length; i++) {
    btns[i].addEventListener("click", function() {
        var current = document.getElementsByClassName("active");

        // If there's no active class
        if (current.length > 0) {
            current[0].className = current[0].className.replace(" active", "");
        }

        // Add the active class to the current/clicked button
        this.className += " active";
    });
}