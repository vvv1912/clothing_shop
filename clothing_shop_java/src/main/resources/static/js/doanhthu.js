import ApexCharts from "https://cdn.jsdelivr.net/npm/apexcharts@3.43.2-0/+esm";

window.ApexCharts = ApexCharts;

let mainChartColors = {
    borderColor: "#F3F4F6",
    labelColor: "#6B7280",
    opacityFrom: 0.45,
    opacityTo: 0,
};


function isDateEqual(date1, date2) {
    return date1.getDate() === date2.getDate() && date1.getMonth() === date2.getMonth() && date1.getFullYear() === date2.getFullYear();
}

function isMonthEqual(date1, date2) {
    return date1.getMonth() === date2.getMonth() && date1.getFullYear() === date2.getFullYear();
}

export const getMainChartOptions = ({soldReports, importReports, groupBy, startDay, endDay}) => {
    let grouped = [];

    if (groupBy === 'day') {
        for (let d = new Date(startDay); d <= endDay; d.setDate(d.getDate() + 1)) {
            const obj = {
                key: d.getDate() + "/" + (d.getMonth() + 1),
            }
            const totalRevenue = soldReports.filter(x => isDateEqual(x.date, d)).reduce((a, b) => a + b.totalRevenue, 0);
            const totalCost = importReports.filter(x => isDateEqual(x.date, d)).reduce((a, b) => a + b.totalCost, 0);
            obj.totalRevenue = totalRevenue;
            obj.totalCost = totalCost;
            grouped.push(obj);
        }
    }
    if (groupBy === 'month') {
        for (let d = new Date(startDay); d <= endDay; d.setMonth(d.getMonth() + 1)) {
            const obj = {
                key: d.getMonth()+1 + "/" + d.getFullYear(),
            }
            const totalRevenue = soldReports.filter(x => isMonthEqual(x.date, d)).reduce((a, b) => a + b.totalRevenue, 0);
            const totalCost = importReports.filter(x => isMonthEqual(x.date, d)).reduce((a, b) => a + b.totalCost, 0);
            obj.totalRevenue = totalRevenue;
            obj.totalCost = totalCost;
            grouped.push(obj);
        }

    }

    return {
        chart: {
            height: 520,
            type: "area",
            fontFamily: "Inter, sans-serif",
            foreColor: mainChartColors.labelColor,
            toolbar: {
                show: true,
            },
        },
        title: {
            text: "Tổng quan doanh thu",
            align: "left",
            style: {
                color: "black",
                fontSize: 20,
                fontWeight: 600,
            }
        },
        fill: {
            type: "gradient",
            gradient: {
                enabled: true,
                opacityFrom: mainChartColors.opacityFrom,
                opacityTo: mainChartColors.opacityTo,
            },
        },
        dataLabels: {
            enabled: false,
        },
        tooltip: {
            style: {
                fontSize: "14px",
                fontFamily: "Inter, sans-serif",
            },
        },
        grid: {
            show: true,
            borderColor: mainChartColors.borderColor,
            strokeDashArray: 1,
            padding: {
                left: 35,
                bottom: 15,
            },
        },
        series: [
            {
                name: "Doanh thu bán hàng",
                data: grouped.map(x => x.totalRevenue),
                color: "#1A56DB",
            },
            {
                name: "Chi phí nhập hàng",
                data: grouped.map(x => x.totalCost),
                color: "#F87171",
            },
        ],
        markers: {
            size: 5,
            strokeColors: "#ffffff",
            hover: {
                size: undefined,
                sizeOffset: 3,
            },
        },
        xaxis: {
            categories: grouped.map(x => x.key),
            labels: {
                style: {
                    colors: [mainChartColors.labelColor],
                    fontSize: "14px",
                    fontWeight: 500,
                },
            },
            axisBorder: {
                color: mainChartColors.borderColor,
            },
            axisTicks: {
                color: mainChartColors.borderColor,
            },
            crosshairs: {
                show: true,
                position: "back",
                stroke: {
                    color: mainChartColors.borderColor,
                    width: 1,
                    dashArray: 10,
                },
            },
        },
        yaxis: {
            labels: {
                style: {
                    colors: [mainChartColors.labelColor],
                    fontSize: "14px",
                    fontWeight: 500,
                },
                formatter: function (value) {
                    return   VND.format(Number(value));
                },
            },
        },
        legend: {
            fontSize: "14px",
            fontWeight: 500,
            fontFamily: "Inter, sans-serif",
            labels: {
                colors: [mainChartColors.labelColor],
            },
            itemMargin: {
                horizontal: 10,
            },
        },
        responsive: [
            {
                breakpoint: 1024,
                options: {
                    xaxis: {
                        labels: {
                            show: false,
                        },
                    },
                },
            },
        ],
    };
};


