console.log("new_student.js loaded");

let classroomIdJS = document.querySelector('#classroom-data').dataset.classroomId;
console.log("classroomIdJS: ", classroomIdJS);

// update the value of the hidden input field with the selected classroom ID
document.addEventListener("DOMContentLoaded", function () {
    const classroomSelect = document.getElementById('classroom-select');
    const classroomIdInput = document.getElementById('classroom-id');

    // Update the value of the hidden input field with the selected classroom ID
    classroomSelect.addEventListener('change', () => {
        classroomIdInput.value = classroomSelect.value;
    });
    console.log("classroomIdInput.value: ", classroomIdInput.value);
});

// //for the classroom dropdown menu
// document.querySelectorAll('.dropdown-menu .dropdown-item').forEach(function (item) {
//     item.addEventListener('click', function (event) {
//         event.preventDefault();
//         const value = this.getAttribute('data-value');
//         // for the add new classroom option
//         if (value === 'new') {
//             window.location.href = '/showNewClassroomForm';
//         } else {
//             const hiddenInput = document.querySelector('input[name="selectedClassroomId"]');
//             hiddenInput.value = value;
//             hiddenInput.setAttribute('name', 'selectedClassroomId');
//             document.getElementById('classroom-form').submit();
//         }
//     });
// });