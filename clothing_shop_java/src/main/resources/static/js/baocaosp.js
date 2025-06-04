import ApexCharts from "https://cdn.jsdelivr.net/npm/apexcharts@3.43.2-0/+esm";

window.ApexCharts = ApexCharts;

let mainChartColors = {
    borderColor: "#F3F4F6",
    labelColor: "#6B7280",
    opacityFrom: 1,
    opacityTo: 1,
};


export const getMainChartOptions = ({data}) => {
    console.log(data.length);
    let sortData = [...data];
    sortData.sort((a, b) => Number(b.totalSold) - Number(a.totalSold));
    let top10 = sortData.slice(0, 10);
    let categories = top10.map(x => x.product.name);
    return {
        chart: {
            height: 520,
            type: "bar",
            fontFamily: "Inter, sans-serif",
            foreColor: mainChartColors.labelColor,
            toolbar: {
                show: true,
            },
        },
        title: {
            text: "Top 10 sản phẩm bán chạy nhất",
            align: "left",
            style: {
                color: "black",
                fontSize: 20,
                fontWeight: 600,
            }
        },
        fill: {
            opacity: 1,
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
                name: "Tổng số lượng bán",
                data: top10.map(x => Number(x.totalSold)),
                color: "#6366F1",
            }
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
            categories: categories,
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
                    return value;
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


