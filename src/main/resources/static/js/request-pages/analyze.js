import {
    confirmToken,
} from '../utils/func-ipa.js';

confirmToken();

const token = sessionStorage.getItem('token');

class ChartManager {
    constructor(endpoint, chartId, label, bgColor, borderColor, titleText, chartType = 'bar') {
        this.endpoint = endpoint;
        this.chartId = chartId;
        this.label = label;
        this.bgColor = bgColor;
        this.borderColor = borderColor;
        this.titleText = titleText;
        this.chartType = chartType;
        this.fullMonthName = [
            'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
            'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'
        ];
    }

    async fetchData() {
        try {
            const response = await fetch(this.endpoint, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            const data = await response.json();
            this.renderChart(data);
        } catch (error) {
            console.error(`Erro ao obter os dados de ${this.label.toLowerCase()}:`, error);
        }
    }

    renderChart(data) {
        const months = Object.keys(data);
        const values = Object.values(data);

        const chartData = this.getChartData(values, months.length);
        const chartOptions = this.getChartOptions(months.length);

        this.createChart(chartData, chartOptions);
    }

    getChartData(values, monthCount) {
        return {
            labels: this.fullMonthName.slice(0, monthCount),
            datasets: [{
                label: this.label,
                backgroundColor: this.bgColor,
                borderColor: this.borderColor,
                borderWidth: 1,
                data: values,
                fill: this.chartType === 'line' ? false : true
            }]
        };
    }

    getChartOptions(monthCount) {
        return {
            responsive: true,
            plugins: {
                legend: {
                    display: true,
                    position: 'top',
                    labels: {
                        color: 'rgb(0,3,97)'
                    }
                },
                title: {
                    display: true,
                    text: this.titleText,
                    color: 'rgb(0,3,97)',
                    font: {
                        size: 20
                    }
                }
            },
            scales: {
                x: {
                    type: 'category',
                    labels: this.fullMonthName.slice(0, monthCount),
                    ticks: {
                        color: 'rgb(0,3,97)'
                    }
                },
                y: {
                    beginAtZero: true,
                    ticks: {
                        color: 'rgb(0,3,97)'
                    }
                }
            }
        };
    }

    createChart(chartData, chartOptions) {
        const ctx = document.getElementById(this.chartId).getContext('2d');
        new Chart(ctx, {
            type: this.chartType,
            data: chartData,
            options: chartOptions
        });
    }
}

class ComparisonChartManager extends ChartManager {
    constructor(expenseEndpoint, revenueEndpoint, chartId, titleText, fullMonthName, expenseLabel = 'Despesas', revenueLabel = 'Faturamento', expenseColor = 'rgba(255, 99, 132, 0.2)', expenseBorderColor = 'rgba(255, 99, 132, 1)', revenueColor = 'rgba(75, 192, 192, 0.2)', revenueBorderColor = 'rgba(75, 192, 192, 1)') {
        super(expenseEndpoint, chartId, 'Comparação', '', '', 'line');
        this.revenueEndpoint = revenueEndpoint;
        this.titleText = titleText;
        this.fullMonthName = fullMonthName;
        this.expenseLabel = expenseLabel;
        this.revenueLabel = revenueLabel;
        this.expenseColor = expenseColor;
        this.expenseBorderColor = expenseBorderColor;
        this.revenueColor = revenueColor;
        this.revenueBorderColor = revenueBorderColor;
    }

    async fetchData() {
        try {
            const [expenseResponse, revenueResponse] = await Promise.all([
                fetch(this.endpoint, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                }),
                fetch(this.revenueEndpoint, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                })
            ]);
            const expenseData = await expenseResponse.json();
            const revenueData = await revenueResponse.json();
            this.renderChart(expenseData, revenueData);
        } catch (error) {
            console.error('Erro ao obter os dados:', error);
        }
    }

    renderChart(expenseData, revenueData) {
        const months = Object.keys(expenseData);
        const expenseValues = Object.values(expenseData);
        const revenueValues = Object.values(revenueData);

        const chartData = this.getComparisonChartData(expenseValues, revenueValues, months.length);
        const chartOptions = this.getChartOptions(months.length);

        this.createChart(chartData, chartOptions);
    }

    getComparisonChartData(expenseValues, revenueValues, monthCount) {
        return {
            labels: this.fullMonthName.slice(0, monthCount),
            datasets: [
                {
                    label: this.expenseLabel,
                    backgroundColor: this.expenseColor,
                    borderColor: this.expenseBorderColor,
                    borderWidth: 1,
                    fill: false,
                    data: expenseValues,
                },
                {
                    label: this.revenueLabel,
                    backgroundColor: this.revenueColor,
                    borderColor: this.revenueBorderColor,
                    borderWidth: 1,
                    fill: false,
                    data: revenueValues,
                }
            ]
        };
    }
}

function initializeCharts() {
    const fullMonthName = [
        'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
        'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'
    ];

    const charts = [
        new ChartManager('/ipa-analyze/list-profit-months', 'profit', 'Lucro', 'rgba(55,255,0,0.58)', 'rgb(0,0,0)', 'Lucro Mensal'),
        new ComparisonChartManager(
            '/ipa-analyze/list-expenditure-months',
            '/ipa-analyze/list-sale-months',
            'comparison-expenditure-sale',
            'Comparação Mensal: Despesa vs Faturamento',
            fullMonthName,
            'Despesas',
            'Faturamento',
            'rgba(255, 99, 132, 0.2)',
            'rgba(255, 99, 132, 1)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(75, 192, 192, 1)'
        ),
        new ComparisonChartManager(
            '/ipa-analyze/list-capex-months',
            '/ipa-analyze/list-opex-months',
            'comparison-capex-opex',
            'Comparação Mensal: CAPEX vs OPEX',
            fullMonthName,
            'CAPEX',
            'OPEX',
            'rgba(54, 162, 235, 0.2)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(255, 206, 86, 1)'
        )
    ];
    charts.forEach(chart => chart.fetchData());
}

document.addEventListener('DOMContentLoaded', initializeCharts);
