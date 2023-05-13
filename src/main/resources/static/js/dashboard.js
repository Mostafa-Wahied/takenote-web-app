console.log("dashboard.js loaded");

let colors = ['#6ba7ba', '#385A64', '#1A2E35', '#ffb703', '#fc5a41'];
let colors2 = ['#fc5a41', '#ffb703', '#385A64'];

// Reading meetings count per student by type bar chart
google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawReadingMeetingCountByTypeBarChart);

function drawReadingMeetingCountByTypeBarChart() {
    fetch('/dashboard/getReadingMeetingCountByStudentAndType')
        .then(response => response.json())
        .then(data => {
            console.log("reading type: ", data);
            const chartData = new google.visualization.DataTable();
            chartData.addColumn('string', 'Students');
            chartData.addColumn('number', '1:1 - Reading');
            chartData.addColumn('number', 'Guided Reading');
            chartData.addColumn('number', 'Strategy Group - Reading');
            // chartData.addColumn('number', '1:1 - Writing');
            // chartData.addColumn('number', 'Strategy Group - Writing');


            for (const [student, meetingCounts] of Object.entries(data)) {
                chartData.addRow(
                    [student,
                        meetingCounts['1:1 - Reading'] || 0,
                        meetingCounts['Guided Reading'] || 0,
                        meetingCounts['Strategy Group - Reading'] || 0,
                        // meetingCounts['1:1 - Writing'] || 0,
                        // meetingCounts['Strategy Group - Writing'] || 0,

                    ]);
            }
            const options = {
                chart: {
                    title: 'Number of Reading Meetings per Student', subtitle: 'By Type'
                }, chartArea: {width: '80%', height: '80%'}, bars: 'vertical', hAxis: {title: 'Students'}, vAxis: {
                    title: 'Meeting Count', format: 'decimal'
                },
                height: 300,
                colors: colors2
            };
            const chart = new google.charts.Bar(document.getElementById('reading_meetings_type_bar_chart'));
            chart.draw(chartData, options);

            // Resize the chart when the window is resized
            window.addEventListener('resize', () => {
                chart.draw(chartData, options);
            });
        });
}

// Writing meetings count per student by type bar chart
google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawWritingMeetingCountByTypeBarChart);

function drawWritingMeetingCountByTypeBarChart() {
    fetch('/dashboard/getWritingMeetingCountByStudentAndType')
        .then(response => response.json())
        .then(data => {
            console.log("writing type: ", data);
            const chartData = new google.visualization.DataTable();
            chartData.addColumn('string', 'Students');
            // chartData.addColumn('number', '1:1 - Reading');
            // chartData.addColumn('number', 'Guided Reading');
            // chartData.addColumn('number', 'Strategy Group - Reading');
            chartData.addColumn('number', '1:1 - Writing');
            chartData.addColumn('number', 'Strategy Group - Writing');


            for (const [student, meetingCounts] of Object.entries(data)) {
                chartData.addRow(
                    [student,
                        // student, meetingCounts['1:1 - Reading'] || 0,
                        //     meetingCounts['Guided Reading'] || 0,
                        //     meetingCounts['Strategy Group - Reading'] || 0,
                        meetingCounts['1:1 - Writing'] || 0,
                        meetingCounts['Strategy Group - Writing'] || 0,

                    ]);
            }
            const options = {
                chart: {
                    title: 'Number of Writing Meetings per Student', subtitle: 'By Type'
                }, chartArea: {width: '80%', height: '80%'}, bars: 'vertical', hAxis: {title: 'Students'}, vAxis: {
                    title: 'Meeting Count', format: 'decimal'
                },
                height: 300,
                colors: colors2
            };
            const chart = new google.charts.Bar(document.getElementById('writing_meetings_type_bar_chart'));
            chart.draw(chartData, options);

            // Resize the chart when the window is resized
            window.addEventListener('resize', () => {
                chart.draw(chartData, options);
            });
        });
}

//  meeting count by type pie chart
// google.charts.load('current', {packages: ['corechart']});
google.charts.setOnLoadCallback(drawMeetingCountByTypePieChart);

function drawMeetingCountByTypePieChart() {
    fetch('/dashboard/getMeetingCountByType')
        .then(response => response.json())
        .then(data => {
            // Define the desired order of the 'type' field
            const typeOrder = ['1:1 - Reading', 'Guided Reading', 'Strategy Group - Reading', '1:1 - Writing', 'Strategy Group - Writing'];

            // Sort the data array based on the desired order
            data.sort((a, b) => typeOrder.indexOf(a.type) - typeOrder.indexOf(b.type));

            const chartData = new google.visualization.DataTable();
            chartData.addColumn('string', 'Type');
            chartData.addColumn('number', 'Count');

            data.forEach(row => {
                chartData.addRow([row.type, row.count]);
            });

            const options = {
                // title: 'Meeting Count by Type',
                chartArea: {width: '80%', height: '80%'}, legend: {
                    alignment: 'center', textStyle: {
                        color: 'grey', fontSize: 11
                    },
                }, pieHole: 0.4,
                height: 221,
                colors: colors
            };

            const chart = new google.visualization.PieChart(document.getElementById('meetings_type_pie_chart'));
            chart.draw(chartData, options);

            // Resize the chart when the window is resized
            window.addEventListener('resize', () => {
                chart.draw(chartData, options);
            });
        });
}

// Reading meetings count by student bar chart
google.charts.setOnLoadCallback(drawReadingMeetingCountBySubjectBarChart);

function drawReadingMeetingCountBySubjectBarChart() {
    fetch('/dashboard/getReadingMeetingsCountBySubject')
        .then(response => response.json())
        .then(data => {
            console.log("reading_meetings: ", data);
            const chartData = new google.visualization.DataTable();
            chartData.addColumn('string', 'Students');
            chartData.addColumn('number', 'Count');

            data.forEach(row => {
                var key = Object.keys(row)[0];
                var value = row[key];
                chartData.addRow([key, value]);
            });

            const options = {
                title: 'Number of Reading Meetings by Student', bars: 'vertical', legend: {
                    alignment: 'center', textStyle: {
                        color: 'grey',
                    },
                },
                // height: 300,
                colors: "#fc5a41"
            };

            const chart = new google.charts.Bar(document.getElementById('reading_meetings_subject_bar_chart'));
            chart.draw(chartData, options);

            // Resize the chart when the window is resized
            window.addEventListener('resize', () => {
                chart.draw(chartData, options);
            });
        });
}

// Reading meetings count by student bar chart
google.charts.setOnLoadCallback(drawWritingMeetingCountBySubjectBarChart);

function drawWritingMeetingCountBySubjectBarChart() {
    fetch('/dashboard/getWritingMeetingsCountBySubject')
        .then(response => response.json())
        .then(data => {
            console.log("writing_meetings: ", data);
            const chartData = new google.visualization.DataTable();
            chartData.addColumn('string', 'Students');
            chartData.addColumn('number', 'Count');

            data.forEach(row => {
                var key = Object.keys(row)[0];
                var value = row[key];
                chartData.addRow([key, value]);
            });

            const options = {
                title: 'Number of Writing Meetings by Student', bars: 'vertical', legend: {
                    alignment: 'center', textStyle: {
                        color: 'grey',
                    },
                },
                // height: 300,
                colors: "#fc5a41"
            };
            const chart = new google.charts.Bar(document.getElementById('writing_meetings_subject_bar_chart'));
            chart.draw(chartData, options);

            // Resize the chart when the window is resized
            window.addEventListener('resize', () => {
                chart.draw(chartData, options);
            });
        });
}


// average reading subject level progress line chart
google.charts.load('current', {'packages': ['corechart']});
google.charts.setOnLoadCallback(drawAverageSubjectLevelProgressLineChart);

function drawAverageSubjectLevelProgressLineChart() {
    fetch('/dashboard/getAverageReadingSubjectLevel')
        .then(response => response.json())
        .then(data => {
            // Get average reading level for all meetings for the logged in user
            let avgReadingLevel = document.querySelector('#average_reading_level_float').dataset.avgLevel;
            console.log("avgReadingLevel: " + avgReadingLevel);
            let letter = String.fromCharCode('Z'.charCodeAt(0) - avgReadingLevel + 1);
            // Select the <p> element by its id
            let averageSubjectLevelElement = document.querySelector('#average-subject-level');
            // Update the content of the <p> element with the current average subject level value
            averageSubjectLevelElement.textContent = letter;
            // end of getting current average subject level value
            console.log("average_reading_subject_level: ", data);
            const chartData = new google.visualization.DataTable();
            chartData.addColumn('date', 'Date');
            chartData.addColumn('number', 'Average Subject Level');
            // Add a column for tooltip content
            chartData.addColumn({type: 'string', role: 'tooltip', p: {html: true}});
            data.forEach(row => {
                let date = new Date(row.date);
                let avgSubjectLevel = row.avgSubjectLevel;
                // Add the tooltip content for each row
                // Use the format function to show the letter
                let letter = String.fromCharCode('Z'.charCodeAt(0) - avgSubjectLevel + 1);
                let tooltip = `<div>Average Subject Level on ${date.toLocaleDateString()}: ${letter}</div>`;
                chartData.addRow([date, avgSubjectLevel, tooltip]);
            });
            const options = {
                // title: 'Average Reading Level Progress',
                legend: {
                    position: 'none'
                },
                hAxis: {
                    textPosition: 'none',
                    format: 'M/d/yy',
                    gridlines: {
                        color: 'transparent'
                    },
                },
                height: 100,
                vAxis: {
                    textPosition: 'none',
                    // ticks: [1, 5, 10, 15, 20, 25],
                    format: function (value) {
                        return String.fromCharCode('Z'.charCodeAt(0) - value + 1);
                    },
                    gridlines: {
                        color: 'transparent'
                    },
                    viewWindow: {
                        min: 1,
                        max: 26
                    },
                    // Set the format option to display whole numbers
                    format: '#',
                    viewWindow: {
                        min: 1,
                        max: 30
                    },
                    // Set the format option to show the letter for each tick
                    ticks: [{v: 1, f: 'Z'},
                        {v: 2, f: 'Y'},
                        {v: 3, f: 'X'},
                        {v: 4, f: 'W'},
                        {v: 5, f: 'V'},
                        {v: 6, f: 'U'},
                        {v: 7, f: 'T'},
                        {v: 8, f: 'S'},
                        {v: 9, f: 'R'},
                        {v: 10, f: 'Q'},
                        {v: 11, f: 'P'},
                        {v: 12, f: 'O'},
                        {v: 13, f: 'N'},
                        {v: 14, f: 'M'},
                        {v: 15, f: 'L'},
                        {v: 16, f: 'K'},
                        {v: 17, f: 'J'},
                        {v: 18, f: 'I'},
                        {v: 19, f: 'H'},
                        {v: 20, f: 'G'},
                        {v: 21, f: 'F'},
                        {v: 22, f: 'E'},
                        {v: 23, f: 'D'},
                        {v: 24, f: 'C'},
                        {v: 25, f: 'B'},
                        {v: 26, f: 'A'}]
                },
                colors: ['#fc5a41'],
                // Set the tooltip option to use HTML
                tooltip: {isHtml: true}
            };
            const chart = new google.visualization.LineChart(document.getElementById('average_reading_subject_level_line_chart'));
            chart.draw(chartData, options);

            // Resize the chart when the window is resized
            window.addEventListener('resize', () => {
                chart.draw(chartData, options);
            });
        });
}

// update the active tab when the user clicks on a tab & redraw the charts
function updateTypeActiveTab() {
    const activeButton = document.querySelector('#meetings-type-bar-pills-tab button.active');
    activeButton.classList.toggle('btn-primary');
    activeButton.classList.remove('color-1');

    const tabsButton = document.querySelectorAll('#meetings-type-bar-pills-tab button');
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
            const activeTab = document.querySelector('#meetings-type-bar-pills-tab button.active');
            activeTab.classList.add('btn-primary');
            activeTab.classList.remove('color-1');

            // Redraw the writing bar chart here
            drawReadingMeetingCountByTypeBarChart();

            // Redraw the reading bar chart here
            drawWritingMeetingCountByTypeBarChart();
        });
    });
}

// Call the function to update the active tab and redraw the charts
updateTypeActiveTab();

// update the active tab when the user clicks on a tab & redraw the charts
function updateSubjectActiveTab() {
    const activeButton = document.querySelector('#meetings-subject-bar-pills-tab button.active');
    activeButton.classList.toggle('btn-primary');
    activeButton.classList.remove('color-1');

    const tabsButton = document.querySelectorAll('#meetings-subject-bar-pills-tab button');
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
            const activeTab = document.querySelector('#meetings-subject-bar-pills-tab button.active');
            activeTab.classList.add('btn-primary');
            activeTab.classList.remove('color-1');

            // Redraw the writing bar chart here
            drawReadingMeetingCountBySubjectBarChart();

            // Redraw the reading bar chart here
            drawWritingMeetingCountBySubjectBarChart();
        });
    });
}

// Call the function to update the active tab and redraw the charts
updateSubjectActiveTab();

