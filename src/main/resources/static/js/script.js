console.log("script.js file loaded");

// Date Picker dropdown
// $(document).ready(function () {
//     var date_input = $('input[name="date"]'); //our date input has the name "date"
//     var container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
//     var options = {
//         format: 'yyyy-mm-dd',
//         container: container,
//         todayHighlight: true,
//         autoclose: true,
//     };
//     date_input.datepicker(options);
// })
// current year

function displayCurrentYear() {
    console.log("displayCurrentYear() called");
    const year = new Date().getFullYear();
    const element = document.querySelector('#year');
    element.textContent = String(year);
}

// Call the function to display the current year
displayCurrentYear();

function initializeDatePicker() {
    const date_input = $('input[name="date"]');
    const container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
    const options = {
        format: 'yyyy-mm-dd',
        container: container,
        todayHighlight: true,
        autoclose: true,
    };
    date_input.datepicker(options);
}
initializeDatePicker();
// end of Date Picker dropdown

// // populating the reading level dropdown menu on forms
// const alphabetDropdown = document.querySelector("#letter");
// const alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
// for (let letter of alphabet) {
//     let optionHtmlElement = document.createElement("option");
//     optionHtmlElement.value = letter;
//     optionHtmlElement.textContent = letter;
//     alphabetDropdown.append(optionHtmlElement);
// }
function populateReadingLevelDropdown() {
    const alphabetDropdown = document.querySelector("#letter");
    const alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for (let letter of alphabet) {
        let optionHtmlElement = document.createElement("option");
        optionHtmlElement.value = letter;
        optionHtmlElement.textContent = letter;
        alphabetDropdown.append(optionHtmlElement);
    }
}
populateReadingLevelDropdown();
// end of populating the reading level dropdown menu on forms

// setting active state to navbar pages
// Get the container element
const btnContainer = document.getElementsByClassName("nav-item");
// Get all buttons with class="btn" inside the container
const btns = document.getElementsByClassName("nav-link");
// Loop through the buttons and add the active class to the current/clicked button
for (let i = 0; i < btns.length; i++) {
    btns[i].addEventListener("click", function () {
        const current = document.getElementsByClassName("active");
        // If there's no active class
        if (current.length > 0) {
            current[0].className = current[0].className.replace(" active", "");
        }
        // Add the active class to the current/clicked button
        this.className += " active";
    });
}

// for selectpicker dropdown
$('select').selectpicker();


// preloader
var loader = document.getElementById("preloader")

window.addEventListener("load", function () {
    loader.style.display = "none";
})

// delete student confirmation function
function confirmDelete(event) {
    if (!confirm('Are you sure you want to delete this student?')) {
        event.preventDefault();
    } else {
        document.querySelector('[name="confirm"]').value = 'true';
    }
}
// end of delete student confirmation function



