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
    if (date_input.length > 0) {  // Check if element exists
        const container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
        const options = {
            format: 'yyyy-mm-dd',
            container: container,
            todayHighlight: true,
            autoclose: true,
        };
        console.log("date_input initialized: ", date_input);
        date_input.datepicker(options);
    } else {
        console.log("date_input not found");
    }
}

initializeDatePicker();
// end of Date Picker dropdown

function populateReadingLevelDropdown() {
    const alphabetDropdown = document.querySelector("#letter");
    const alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for (let letter of alphabet) {
        let optionHtmlElement = document.createElement("option");
        optionHtmlElement.value = letter;
        optionHtmlElement.textContent = letter;
        if (alphabetDropdown) {
            alphabetDropdown.append(optionHtmlElement);
        }
    }
}

populateReadingLevelDropdown();
// end of populating the reading level dropdown menu on forms

// for selectpicker dropdown
// $('select').selectpicker();


// preloader
var loader = document.getElementById("preloader")

window.addEventListener("load", function () {
    loader.style.display = "none";
})
// end of preloader

// show/hide subject level dropdown on the add meeting form based on the selected meeting type
document.addEventListener('DOMContentLoaded', function() {
    const meetingTypeSelect = document.getElementById('meetingType');
    const subjectLevelSelect = document.getElementById('subjectLevel');
    const subjectLevelDiv = document.getElementById('subjectLevelDiv');

    function toggleSubjectLevelVisibility() {
        if (!meetingTypeSelect || !subjectLevelDiv) {
            return;
        }
        const selectedType = meetingTypeSelect.value;
        if (selectedType === 'Guided Reading' || selectedType === '1:1 - Reading') {
            subjectLevelDiv.style.display = 'block';
            subjectLevelSelect.disabled = false;
        } else {
            subjectLevelDiv.style.display = 'none';
            subjectLevelSelect.disabled = true;
            subjectLevelSelect.value = '';
        }
    }

    // Initial check on page load
    toggleSubjectLevelVisibility();

    // Add change event listener
    if (!meetingTypeSelect) {
        return;
    }
    meetingTypeSelect.addEventListener('change', toggleSubjectLevelVisibility);
});
// end of show/hide subject level dropdown on the add meeting form based on the selected meeting type



