console.log("students.js loaded")

// filter students table based on the selected classroom
document.addEventListener("DOMContentLoaded", function () {
    const classroomSelect = document.getElementById('classroom-select');
    const studentsTable = document.getElementById('students-table');
    // Hide all rows except those for the selected classroom
    for (const row of studentsTable.rows) {
        if (row.dataset.classroomId !== classroomSelect.value && classroomSelect.value !== 'Classroom' && !row.querySelector('th')) {
            row.style.display = 'none';
        }
    }
    // Show the table after hiding rows
    studentsTable.style.display = '';

    classroomSelect.addEventListener('change', () => {
        const selectedClassroomId = classroomSelect.value;
        for (const row of studentsTable.rows) {
            if (row.dataset.classroomId === selectedClassroomId || selectedClassroomId === 'Classroom') {
                row.style.display = '';
            } else if (!row.querySelector('th')) {
                row.style.display = 'none';
            }
        }
    });
});
// update the href attribute of the add student button with the selected classroom ID
document.addEventListener("DOMContentLoaded", function() {
    const classroomSelect = document.getElementById('classroom-select');
    const addStudentBtn = document.getElementById('add-student-btn');

    // Update the href attribute of the add student button with the selected classroom ID
    classroomSelect.addEventListener('change', () => {
        const selectedClassroomId = classroomSelect.value;
        addStudentBtn.href = `/showNewStudentForm?classroomId=${selectedClassroomId}`;
    });
});