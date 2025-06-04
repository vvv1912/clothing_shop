import ApexCharts from "https://cdn.jsdelivr.net/npm/apexcharts@3.43.2-0/+esm";
import fakerJs from "https://cdn.jsdelivr.net/npm/faker@5.5.3/+esm";
let mainChartColors = {
  borderColor: "#F3F4F6",
  labelColor: "#6B7280",
  opacityFrom: 0.45,
  opacityTo: 0,
};
const getMainChartOptions = () => {
  return {
    chart: {
      height: 420,
      type: "area",
      fontFamily: "Inter, sans-serif",
      foreColor: mainChartColors.labelColor,
      toolbar: {
        show: false,
      },
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
        name: "Doanh thu",
        data: [6356, 6218, 6156, 6526, 6356, 6256, 6056],
        color: "#1A56DB",
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
      categories: ["01 Feb", "02 Feb", "03 Feb", "04 Feb", "05 Feb", "06 Feb", "07 Feb"],
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
          return "$" + value;
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

const categories = ["Áo thun", "Áo sơ mi", "Quần jean", "Quần short", "Váy", "Đầm"];
function generateData(timeGap = 7) {
  // last 7 days
  let data = [];
  const min = timeGap * 50;
  const max = timeGap * 200;

  categories.forEach((category) => {
    let value = fakerJs.random.number({ min: min, max: max });

    data.push({
      category: category,
      value: value,
    });
  });

  return data;
}
// Tổng khách truy cập

// Khách hàng
const customers = fakerJs.random.number({ min: 100, max: 500 });
const totalVisitors = fakerJs.random.number({ min: customers + 50, max: 1000 });

// Tỷ lệ chuyển đổi

export function renderTotalVisitors() {
  var el = document.querySelector("#total-visitors-chart");
  var options = {
    chart: {
      type: "pie",
    },
    series: [totalVisitors - customers, customers],
    labels: ["Khách vãng lai", "Khách hàng"],
    responsive: [
      {
        breakpoint: 480,
        options: {
          chart: {
            width: 200,
          },
          legend: {
            position: "bottom",
          },
        },
      },
    ],
  };

  var chart = new ApexCharts(el, options);
  chart.render();
}

export function renderCategoryChart(amount = 7) {
  const fakeData = generateData(amount);
  var chart = new ApexCharts(document.getElementById("category-chart"), {
    chart: {
      type: "bar",
    },
    plotOptions: {
      bar: {
        horizontal: false,
      },
    },
    dataLabels: {
      enabled: false,
    },
    series: [
      {
        name: "Số lượng",
        data: fakeData.map((item) => item.value),
      },
    ],
    xaxis: {
      categories: fakeData.map((item) => item.category),
    },
  });

  chart.render();
}

export function renderMainChart() {
  if (document.getElementById("main-chart")) {
    const chart = new ApexCharts(document.getElementById("main-chart"), getMainChartOptions());
    chart.render();
  }
}
