console.log("datatables.js loaded...")

// using jQuery DataTables
$(document).ready(function () {
    $('#students-table').DataTable();
});

$(document).ready(function () {
    $('#student-reading-table').DataTable();
});

$(document).ready(function () {
    $('#student-writing-table').DataTable();
});


// update the active tab when the user clicks on a tab & redraw the charts
function updateStudentActiveTab() {
    const activeButton = document.querySelector('#student-reading-table-pills-tab button.active');
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