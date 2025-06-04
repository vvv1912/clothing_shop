document.addEventListener('alpine:init', () => {
    Alpine.data('tableData', (queryData = {}) => ({
        data: [],
        total: 0,
        totalElement: 0,
        hasNextPage: true,
        hasPreviousPage: true,
        loading: false,
        queryData: {
            ...queryData,
            page: 1,
            pageSize: 10,
            sortField: 'createdDate',
            sortDir: 'desc',
            keyword: undefined,
        },
        toggleSortDir() {
            if (this.queryData.sortDir === 'desc') {
                this.queryData.sortDir = 'asc';
                console.log(`#${this.queryData.sortField}Icon`)
                document.querySelector(`#${this.queryData.sortField}Icon`).innerHTML = `${sortUpSVG}`
            } else {
                this.queryData.sortDir = 'desc';
                console.log(`#${this.queryData.sortDir}Icon`)

                document.querySelector(`#${this.queryData.sortField}Icon`).innerHTML = `${sortDownSVG}`
            }
        },
    }));
});

const sortUpSVG = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 22 22" stroke-width="1.5" stroke="currentColor" class="w-3 h-3">
  <path stroke-linecap="round" stroke-linejoin="round" d="M4.5 10.5L12 3m0 0l7.5 7.5M12 3v18" />
</svg>
`
const sortDownSVG = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 22 22" stroke-width="1.5" stroke="currentColor" class="w-3 h-3">
  <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 13.5L12 21m0 0l-7.5-7.5M12 21V3" />
</svg>
`