type Product = object;

interface Query {
  categoryIds: number[] | undefined;
  minPrice: number | undefined;
  maxPrice: number | undefined;
  page: number | undefined;
  pageSize: number | undefined;
  sortField: string | undefined;
  sortDir: "asc" | "desc" | undefined;
  keyword: string | undefined;
  colorIds: number[] | undefined;
  sizes: string[] | undefined;
}
interface Data {
  data: Product[];
  page: number;
  pageSize: number;
  totalPage: number;
  totalElement: number;
  hasPrev: boolean;
  hasNext: boolean;
}
function getProducts(query: Query): Data {
  // dữ liệu giả lập
  return {
    data: [],
    page: 1,
    pageSize: 10,
    totalPage: 1,
    totalElement: 0,
    hasPrev: false,
    hasNext: false,
  };
}
