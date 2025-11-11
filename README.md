# 🎓 ỨNG DỤNG QUẢN LÝ SINH VIÊN (QLSV)

Ứng dụng **Java Swing + JDBC** quản lý danh sách sinh viên, thao tác CRUD với MySQL.
Dự án được xây dựng để thực hành lập trình hướng đối tượng, GUI và kết nối CSDL.

---

## 📁 Cấu trúc thư mục

```
QLSV_PROJECT/
│
├── .vscode/                      # Cấu hình VS Code (classpath, settings)
│
├── database/
│   └── setup_mysql.sql           # Script tạo database + bảng SinhVien + dữ liệu mẫu
│
├── lib/
│   └── mysql-connector-j-9.5.0.jar   # Thư viện JDBC (MySQL Connector)
│
├── src/
│   ├── DBConnection.java         # Kết nối CSDL (JDBC)
│   └── SinhVienFrame.java        # Giao diện JFrame + xử lý CRUD
│
└── README.md
```

---

## ⚙️ 1. Cấu hình CSDL MySQL

Mở **MySQL Workbench** và chạy file [`database/setup_mysql.sql`](database/setup_mysql.sql)
Hoặc copy trực tiếp:

```sql
CREATE DATABASE QLSV;
USE QLSV;

CREATE TABLE SinhVien (
    MaSV   VARCHAR(20)  PRIMARY KEY,
    HoTen  VARCHAR(255) NOT NULL,
    Lop    VARCHAR(20)  NOT NULL,
    GPA    DECIMAL(10,2)
);

INSERT INTO SinhVien (MaSV, HoTen, Lop, GPA) VALUES
('B23DCCN720', 'Nguyễn Duy Sơn', 'D23CQCN06', 3.10),
('B23DCCN001', 'Trần Minh Khôi', 'D23CQCN01', 3.75),
('B23DCCN002', 'Lê Thu Trang',  'D23CQCN02', 3.40),
('B23DCCN003', 'Phạm Đức Huy',  'D23CQCN03', 2.85),
('B23DCCN004', 'Nguyễn Thị Linh', 'D23CQCN04', 3.95),
('B23DCCN005', 'Hoàng Gia Bảo',  'D23CQCN05', 2.60),
('B23DCCN006', 'Vũ Thanh Hằng',  'D23CQCN06', 3.20),
('B23DCCN007', 'Đỗ Văn Nam',     'D23CQCN07', 2.95),
('B23DCCN008', 'Phan Ngọc Anh',  'D23CQCN08', 3.55),
('B23DCCN009', 'Nguyễn Hoàng Yến', 'D23CQCN09', 3.80);
```

---

## ⚙️ 2. Cấu hình kết nối trong Java

**File:** `src/DBConnection.java`

```java
private static final String URL  = "jdbc:mysql://localhost:3306/QLSV";
private static final String USER = "root";
private static final String PASS = "Son21042005@"; // điền mật khẩu của bạn
```

> 🔧 Thay đổi `USER` và `PASS` cho phù hợp với tài khoản MySQL cá nhân.

---

## 💻 3. Cách chạy project (VS Code)

### ▶️ Chạy trực tiếp

1. Mở file `SinhVienFrame.java`
2. Nhấn **Ctrl + F5** hoặc chọn nút **Run ▶️**
3. Nếu không thấy MySQL hoạt động, kiểm tra file `.vscode/settings.json`:

```json
{
  "java.project.referencedLibraries": ["lib/**/*.jar"]
}
```

---

## 🪟 4. Giao diện chương trình

* **4 ô nhập liệu:** Mã SV, Họ tên, Lớp, GPA
* **1 bảng hiển thị:** danh sách sinh viên
* **5 nút chức năng:**

| Nút         | Chức năng                   |
| ----------- | --------------------------- |
| 🧾 Hiển thị | Đọc toàn bộ dữ liệu từ CSDL |
| ➕ Thêm      | Thêm sinh viên mới          |
| ✏️ Cập nhật | Sửa thông tin sinh viên     |
| ❌ Xóa       | Xóa sinh viên theo mã       |
| 🔄 Reset    | Làm mới form nhập và bảng   |

---

## 📚 5. Mô tả kỹ thuật

| Thành phần       | Mô tả                                |
| ---------------- | ------------------------------------ |
| **Ngôn ngữ**     | Java 21                              |
| **IDE**          | Visual Studio Code                   |
| **Database**     | MySQL 8.x                            |
| **JDBC Driver**  | mysql-connector-j-9.5.0.jar          |
| **GUI**          | Java Swing                           |
| **Kết nối DB**   | JDBC (PreparedStatement + ResultSet) |
| **Kiểu dữ liệu** | VARCHAR, DECIMAL(10,2)               |

---

## 🧠 6. Tác giả

* **Họ tên:** Nguyễn Duy Sơn
* **MSSV:** B23DCCN720
* **Lớp:** D23CQCN06 – Học viện Công nghệ Bưu chính Viễn thông (PTIT)

---

## 🚀 7. Hướng mở rộng

* Thêm chức năng **tìm kiếm** theo tên hoặc lớp
* Kiểm tra hợp lệ GPA (0.0 → 4.0)
* **Xuất dữ liệu** ra file `.csv` / `.xlsx`
* Tách lớp DAO + Model theo chuẩn **MVC**

---

> 📅 **Hoàn thành:** 2025
> 💬 **Mục tiêu:** Thực hành JDBC, GUI và CRUD trong Java
> ❤️ Made with love by Sơn

