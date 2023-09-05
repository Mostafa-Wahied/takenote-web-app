console.log("classroom_dropdown.js loaded");

//for the classroom dropdown menu
document.querySelectorAll('.dropdown-menu .dropdown-item').forEach(function (item) {
    item.addEventListener('click', function (event) {
        event.preventDefault();
        const value = this.getAttribute('data-value');
        // for the add new classroom option
        if (value === 'new') {
            window.location.href = '/showNewClassroomForm';
        } else {
            const hiddenInput = document.querySelector('input[name="selectedClassroomId"]');
            hiddenInput.value = value;
            hiddenInput.setAttribute('name', 'selectedClassroomId');
            document.getElementById('classroom-form').submit();
        }
    });
});


function confirmDeleteClassroom(classroomId) {
    // Check if a classroom has been selected from the dropdown menu
    const selectButton = document.querySelector("#classroomDropdown");
    if (selectButton.innerText === 'Select Classroom ') {
        alert('Please select a default classroom first before attempting to delete a classroom.');
        return;
    }
    if (confirm("Are you sure you want to delete this classroom?")) {
        const deleteForm = document.querySelector("#delete-classroom-form");
        deleteForm.querySelector("input[name='id']").value = classroomId;
        deleteForm.setAttribute("action", `/classroom/delete/${classroomId}`);
        deleteForm.submit();
    }
}