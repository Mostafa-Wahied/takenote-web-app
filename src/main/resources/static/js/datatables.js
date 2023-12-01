console.log("datatables.js loaded...")

// using jQuery DataTables
$(document).ready(function () {
    $('#students-table').DataTable();
    const tbody = $('#students-table tbody');


    tbody.on('click', 'td.editable', function () {
        const currentLevel = $(this).find('span.reading-level-text').text();
        const dropdown = $(this).find('select');
        dropdown.val(currentLevel);
        $(this).find('span').hide();
        dropdown.show();
    });

    tbody.on('change', 'select', function () {
        const newLevel = $(this).val();
        const studentId = $(this).closest('td').attr('data-studentid');
        console.log('newLevel', newLevel);
        console.log('studentId', studentId);
        // $(this).siblings('span').text(newLevel).show();

        $(this).hide();
        const td = $(this).closest('td');
        td.find('span.reading-level-text').text(newLevel).show();
        td.find('a.reading-level-edit-button span').show();
        // td.find('a.reading-level-edit-button').show();
        updateReadingLevel(studentId, newLevel);
    });

    function updateReadingLevel(studentId, newLevel) {
        console.log('updateReadingLevel() called');
        // Get the CSRF token
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        // AJAX call to update the reading level
        $.ajax({
            url: '/updateReadingLevel',
            type: 'post',
            data: {studentId: studentId, newLevel: newLevel},
            beforeSend: function (xhr) {
                // Include the CSRF token in the header
                xhr.setRequestHeader(header, token);
            }
        }).done(function (data) {
            // $('td[data-studentid="' + studentId + '"] span.reading-level-text').text(newLevel).show();
            console.log("Reading level updated successfully");
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error("Error updating reading level: " + textStatus + ", " + errorThrown);
        });
    }
});
$(document).ready(function () {
    $('#students-reading-table').DataTable({
        bAutoWidth: false,
        aoColumns: [
            {sWidth: "20%"},
            {sWidth: "15%"},
            {sWidth: "60%"},
            {sWidth: "5%"},
        ]
    });
});
$(document).ready(function () {
    $('#students-writing-table').DataTable({
        bAutoWidth: false,
        aoColumns: [
            {sWidth: "20%"},
            {sWidth: "15%"},
            {sWidth: "60%"},
            {sWidth: "5%"},
        ]
    });
});
$(document).ready(function () {
    $('#student-reading-table').DataTable({
        "order": [[0, "desc"]],
        // 0 is the index of the column you want to sort by default

    });
});
$(document).ready(function () {
    $('#student-writing-table').DataTable({
        "order": [[0, "desc"]] // 0 is the index of the column you want to sort by default
    });
});


// update the active tab when the user clicks on a tab & redraw the charts
function updateStudentActiveTab() {
    const activeButton = document.querySelector('#student-reading-table-pills-tab button.active');
    if (!activeButton) {
        return;
    }
    activeButton.classList.toggle('btn-primary');
    activeButton.classList.remove('color-1');

    const tabsButton = document.querySelectorAll('#student-reading-table-pills-tab button');
    tabsButton.forEach(function (tab) {
        // Add the color-1 class to all inactive tabs
        if (!tab.classList.contains('active')) {
            tab.classList.add('color-1');
        }

        tab.addEventListener('shown.bs.tab', function () {
            // Remove the btn-primary class from all tabs
            tabsButton.forEach(function (tab) {
                tab.classList.remove('btn-primary');
                tab.classList.add('color-1');
            });

            // Add the btn-primary class to the active tab and remove the color-1 class
            const activeTab = document.querySelector('#student-reading-table-pills-tab button.active');
            activeTab.classList.add('btn-primary');
            activeTab.classList.remove('color-1');
        });
    });
}

// Call the function to update the active tab and redraw the charts
updateStudentActiveTab();