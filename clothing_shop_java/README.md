# Website bán quần áo

Đây là một website bán quần áo được xây dựng bằng Spring boot

## Công nghệ sử dụng

- Spring boot - Framework để xây dựng ứng dụng web Java
- Spring Data JPA(Hibernate) - Để tương tác với cơ sở dữ liệu sử dụng JPA
- MySQL - Cơ sở dữ liệu để lưu trữ dữ liệu
- Thymeleaf - Template engine để xây dựng giao diện người dùng
- Tailwind CSS - Framework CSS để styling giao diện người dùng

## Hướng dẫn cài đặt

### Yêu cầu

- JDK 17
- MySQL
- Node.js > v18

### Các bước cài đặt

1. Clone repository

```bash
git clone https://github.com/Mirai3103/clothing_shop_java.git
```

2. Cấu hình cơ sở dữ liệu MySQL

> - Tạo database mới
> - Tạo user mới với quyền truy cập vào database đó

3. Cấu hình các thông tin kết nối đến CSDL trong file application.properties
4. Mở project bằng IDE
5. Mở terminal và truy cập vào thư mục src/main/resources

```bash
cd src/main/resources
```

6. Cài đặt các package cần thiết cho Node.js

```bash
npm install
```

7. Chạy lệnh để build CSS

```bash
npm run watch:css
```

8. Chạy ứng dụng
9. Truy cập vào đường dẫn http://localhost:8000

## To-do list (Các chức năng cần làm)

| Chức năng                  |     |
| -------------------------- | --- |
| Báo cáo theo sản phẩm      |     |
| Quản lý đánh giá           |     |
| Trang dashboard            |     |
| Xác thực quyền             |     |
