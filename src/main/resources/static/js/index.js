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

// // get references to the modal buttons
// function addClickListenersToModalButtons() {
//     const noteTakerBtn = document.getElementById("noteTakerBtn");
//     const noteBookBtn = document.getElementById("noteBookBtn");
//     console.log(noteTakerBtn);
//     console.log(noteBookBtn);
//
//     // noteTakerBtn.addEventListener("click", () => {
//     //     window.location.href = "/noteTaker"; // replace with the actual URL of the NoteTaker page
//     // });
//     //
//     // noteBookBtn.addEventListener("click", () => {
//     //     window.location.href = "/noteBook"; // replace with the actual URL of the NoteBook page
//     // });
// }
// document.addEventListener("DOMContentLoaded", () => {
//     addClickListenersToModalButtons();
// });
// // end of get references to the modal buttons


