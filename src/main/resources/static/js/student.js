console.log("student.js loaded");

let studentId = document.querySelector('#student-data').dataset.studentId;
console.log("studentId: ", studentId);

// average reading subject level progress line chart
google.charts.load('current', {'packages': ['corechart']});
google.charts.setOnLoadCallback(drawAverageSubjectLevelProgressLineChart);

function drawAverageSubjectLevelProgressLineChart() {
    fetch(`/notebook/student/${studentId}/averageReadingSubjectLevel`)
        .then(response => response.json())
        .then(data => {
            // Get the current average subject level value from the data
            let studentAverageSubjectLevel= 0;
            if (data.length === 0) {
                studentAverageSubjectLevel = 0;
            } else {
                studentAverageSubjectLevel = data[data.length - 1].avgSubjectLevel;
            }

            let letter = String.fromCharCode('Z'.charCodeAt(0) - studentAverageSubjectLevel + 1);
            console.log("studentAverageSubjectLevel: ", studentAverageSubjectLevel);
            // Select the <p> element by its id
            let averageSubjectLevelElement = document.querySelector('#average-subject-level');
            // Update the content of the <p> element with the current average subject level value
            averageSubjectLevelElement.textContent = letter;
            // end of getting current average subject level value
            console.log("student_average_reading_subject_level: ", data);
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
            const chart = new google.visualization.LineChart(document.getElementById('student-average_reading_subject_level_line_chart'));
            chart.draw(chartData, options);

            // Resize the chart when the window is resized
            window.addEventListener('resize', () => {
                chart.draw(chartData, options);
            });
        });
}