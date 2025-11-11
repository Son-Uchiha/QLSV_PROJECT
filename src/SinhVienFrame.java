
// SinhVienFrame.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.sql.*;

public class SinhVienFrame extends JFrame {

    private JTextField txtMaSV, txtHoTen, txtLop, txtGPA;
    private JButton btnHienThi, btnThem, btnCapNhat, btnXoa, btnReset;
    private JTable tblSinhVien;
    private DefaultTableModel model;

    public SinhVienFrame() {
        setTitle("Quản lý SinhVien");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null); // hiện giữa màn hình

        initUI();
        initEvents();
    }

    // Khởi tạo giao diện
    private void initUI() {
        // Panel nhập dữ liệu
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));

        txtMaSV = new JTextField();
        txtHoTen = new JTextField();
        txtLop = new JTextField();
        txtGPA = new JTextField();

        pnlForm.add(new JLabel("Mã SV:"));
        pnlForm.add(txtMaSV);
        pnlForm.add(new JLabel("Họ tên:"));
        pnlForm.add(txtHoTen);
        pnlForm.add(new JLabel("Lớp:"));
        pnlForm.add(txtLop);
        pnlForm.add(new JLabel("GPA:"));
        pnlForm.add(txtGPA);

        // Bảng
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] { "Mã SV", "Họ tên", "Lớp", "GPA" });
        tblSinhVien = new JTable(model);
        JScrollPane scroll = new JScrollPane(tblSinhVien);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách sinh viên"));

        // Panel nút
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnHienThi = new JButton("Hiển thị");
        btnThem = new JButton("Thêm");
        btnCapNhat = new JButton("Cập nhật");
        btnXoa = new JButton("Xóa");
        btnReset = new JButton("Reset");

        pnlButton.add(btnHienThi);
        pnlButton.add(btnThem);
        pnlButton.add(btnCapNhat);
        pnlButton.add(btnXoa);
        pnlButton.add(btnReset);

        // Layout tổng
        setLayout(new BorderLayout(5, 5));
        add(pnlForm, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);
    }

    // Gắn sự kiện cho các nút + bảng
    private void initEvents() {
        // nút Hiển thị
        btnHienThi.addActionListener(e -> loadData());

        // nút Thêm
        btnThem.addActionListener(e -> addStudent());

        // nút Cập nhật
        btnCapNhat.addActionListener(e -> updateStudent());

        // nút Xóa
        btnXoa.addActionListener(e -> deleteStudent());

        // nút Reset
        btnReset.addActionListener(e -> resetForm());

        // click chọn 1 dòng trên bảng -> đổ lên form
        tblSinhVien.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int row = tblSinhVien.getSelectedRow();
                        if (row >= 0) {
                            txtMaSV.setText(model.getValueAt(row, 0).toString());
                            txtHoTen.setText(model.getValueAt(row, 1).toString());
                            txtLop.setText(model.getValueAt(row, 2).toString());
                            txtGPA.setText(model.getValueAt(row, 3).toString());
                        }
                    }
                });
    }

    // HIỂN THỊ: đọc toàn bộ bảng SinhVien
    private void loadData() {
        model.setRowCount(0); // xóa hết dòng cũ
        try (Connection conn = DBConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM SinhVien")) {

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("MaSV"),
                        rs.getString("HoTen"),
                        rs.getString("Lop"),
                        rs.getDouble("GPA") // DECIMAL(10,2) lấy bằng getDouble ok
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + ex.getMessage());
        }
    }

    // THÊM MỚI
    private void addStudent() {
        String ma = txtMaSV.getText().trim();
        String ten = txtHoTen.getText().trim();
        String lop = txtLop.getText().trim();
        String gpaStr = txtGPA.getText().trim();

        if (ma.isEmpty() || ten.isEmpty() || lop.isEmpty() || gpaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống!");
            return;
        }

        double gpa;
        try {
            gpa = Double.parseDouble(gpaStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "GPA phải là số!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // kiểm tra mã trùng
            PreparedStatement chk = conn.prepareStatement(
                    "SELECT MaSV FROM SinhVien WHERE MaSV = ?");
            chk.setString(1, ma);
            ResultSet rs = chk.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Mã SV đã tồn tại!");
                return;
            }

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO SinhVien(MaSV, HoTen, Lop, GPA) VALUES (?,?,?,?)");
            ps.setString(1, ma);
            ps.setString(2, ten);
            ps.setString(3, lop);
            ps.setDouble(4, gpa);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi thêm: " + ex.getMessage());
        }
    }

    // CẬP NHẬT
    private void updateStudent() {
        String ma = txtMaSV.getText().trim();
        String ten = txtHoTen.getText().trim();
        String lop = txtLop.getText().trim();
        String gpaStr = txtGPA.getText().trim();

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chọn 1 sinh viên để cập nhật!");
            return;
        }

        double gpa;
        try {
            gpa = Double.parseDouble(gpaStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "GPA phải là số!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE SinhVien SET HoTen = ?, Lop = ?, GPA = ? WHERE MaSV = ?");
            ps.setString(1, ten);
            ps.setString(2, lop);
            ps.setDouble(3, gpa);
            ps.setString(4, ma);

            int n = ps.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy Mã SV để cập nhật!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật: " + ex.getMessage());
        }
    }

    // XÓA
    private void deleteStudent() {
        String ma = txtMaSV.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chọn 1 sinh viên để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this, "Xóa sinh viên " + ma + " ?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM SinhVien WHERE MaSV = ?");
            ps.setString(1, ma);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi xóa: " + ex.getMessage());
        }
    }

    // RESET form + bảng
    private void resetForm() {
        txtMaSV.setText("");
        txtHoTen.setText("");
        txtLop.setText("");
        txtGPA.setText("");
        model.setRowCount(0); // xoá hết dữ liệu trên JTable
        tblSinhVien.clearSelection();
    }

    // Hàm main để chạy app
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SinhVienFrame().setVisible(true);
        });
    }
}
