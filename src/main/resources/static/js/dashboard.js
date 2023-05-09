console.log("dashboard.js loaded");

let colors = ['#6ba7ba', '#385A64', '#1A2E35', '#ffb703', '#fc5a41'];

google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawMeetingCountByTypeBarChart);

function drawMeetingCountByTypeBarChart() {
    fetch('/dashboard/getMeetingCountByStudentAndType')
        .then(response => response.json())
        .then(data => {
            console.log("bar-data: ", data);
            const chartData = new google.visualization.DataTable();
            chartData.addColumn('string', 'Students');
            chartData.addColumn('number', '1:1 - Reading');
            chartData.addColumn('number', 'Guided Reading');
            chartData.addColumn('number', 'Strategy Group - Reading');
            chartData.addColumn('number', '1:1 - Writing');
            chartData.addColumn('number', 'Strategy Group - Writing');


            for (const [student, meetingCounts] of Object.entries(data)) {
                chartData.addRow([student, meetingCounts['1:1 - Reading'] || 0, meetingCounts['Guided Reading'] || 0, meetingCounts['Strategy Group - Reading'] || 0, meetingCounts['1:1 - Writing'] || 0, meetingCounts['Strategy Group - Writing'] || 0,

                ]);
            }
            const options = {
                chart: {
                    title: 'Number of Meetings per Student', subtitle: 'By Type'
                }, chartArea: {width: '80%', height: '80%'}, bars: 'vertical', hAxis: {title: 'Students'}, vAxis: {
                    title: 'Meeting Count', format: 'decimal'
                }, height: 300, colors: colors
            };
            const chart = new google.charts.Bar(document.getElementById('meetings_type_bar_chart'));
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
                title: 'Meeting Count by Type', chartArea: {width: '80%', height: '80%'}, legend: {
                    alignment: 'center', textStyle: {
                        color: 'grey', // fontSize: 13
                    },
                }, pieHole: 0.4, height: 300, colors: colors
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
google.charts.setOnLoadCallback(drawReadingMeetingCountByStudentBarChart);

function drawReadingMeetingCountByStudentBarChart() {
    fetch('/dashboard/getReadingMeetingsCountByStudent')
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
                }, height: 300, colors: "#fc5a41"
            };

            const chart = new google.charts.Bar(document.getElementById('reading_meetings_student_bar_chart'));
            chart.draw(chartData, options);

            // Resize the chart when the window is resized
            window.addEventListener('resize', () => {
                chart.draw(chartData, options);
            });
        });
}

// Reading meetings count by student bar chart
google.charts.setOnLoadCallback(drawWritingMeetingCountByStudentBarChart);

function drawWritingMeetingCountByStudentBarChart() {
    fetch('/dashboard/getWritingMeetingsCountByStudent')
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
                }, height: 300, colors: "#fc5a41"
            };
            const chart = new google.charts.Bar(document.getElementById('writing_meetings_student_bar_chart'));
            chart.draw(chartData, options);

            // Resize the chart when the window is resized
            window.addEventListener('resize', () => {
                chart.draw(chartData, options);
            });
        });
}

// Add an event listener to the carousel so it redraws the charts when the slide changes
const carousel = document.querySelector('#carouselExampleDark');
carousel.addEventListener('slid.bs.carousel', function () {
    console.log("slid.bs.carousel event triggered!");
    // Redraw the writing bar chart here
    drawWritingMeetingCountByStudentBarChart();

    // Redraw the reading bar chart here
    drawReadingMeetingCountByStudentBarChart();
});