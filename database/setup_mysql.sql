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
