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