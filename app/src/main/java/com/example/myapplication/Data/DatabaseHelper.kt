import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.ColorSpace.Model
import androidx.compose.runtime.snapshots.readable
import com.example.myapplication.Data.Mode.Model_customer
import com.example.myapplication.Data.Model.Model_CTPhieuNhap
import com.example.myapplication.Data.Model.Model_Order
import com.example.myapplication.Data.Model.Model_OrderDetail
import com.example.myapplication.Data.Model.Model_OrderStatus
import com.example.myapplication.Data.Model.Model_Phieunhap
import com.example.myapplication.Data.Model.Model_Phieunhap_status
import com.example.myapplication.Data.Model.Model_account
import com.example.myapplication.Data.Model.Model_cart
import com.example.myapplication.Data.Model.Model_inventory
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.Data.Model.Model_producttype
import com.example.myapplication.Data.Model.Model_staff
import com.example.myapplication.Data.Model.Model_wishlist
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "37"

        // Bảng và cột login
        private const val TABLE_ACCOUNT = "Account"
        private const val idaccount = "account_id"
        private const val accname = "accname"
        private const val accemail = "accemail"
        private const val accsdt = "accsdt"
        private const val accpass = "accpass"

        // Bảng và cột sản phẩm
        private const val TABLE_PRODUCTS = "Products"
        private const val COLUMN_ID = "id"
        private const val COLUMN_MASP = "masp"
        private const val COLUMN_TENSP = "tensp"
        private const val COLUMN_GIA = "gia"
        private const val COLUMN_MALOAI = "maloai"
        private const val COLUMN_DONVI = "donvi"
        private const val COLUMN_IMAGE = "image"

        // Bảng khách hàng và cột (chưa dùng)
        private const val TABLE_KH = "customer"
        private const val KH_ID = "idkh"
        private const val KH_TENKH = "tenkh"
        private const val KH_DIACHI = "diachikh"
        private const val KH_NGAYSINH = "ngaysinhkh"
        private const val KH_GIOITINH = "gioitinh"
        private const val KH_SODT = "sdtkh"
        private const val KH_GHICHU = "ghichu"
        private const val KH_KHOANGOAI = "account_id"

        // Bảng nhân viên và cột
        private const val TABLE_NHANVIEN = "nhanvien"
        private const val NV_ID = "idnv"
        private const val NV_TEN = "tennv"
        private const val NV_GIOITINH = "gtnv"
        private const val NV_NAMSINH = "nsnv"
        private const val NV_CHUCVU = "cvunv"
        private const val NV_PHONGBAN = "pbanvn"
        private const val NV_NGAYLAM = "ngaylam"
        private const val NV_DIACHI = "dcnv"
        private const val NV_SDT = "sdtnv"
        private const val NV_EMAIL = "emailnv"

        // Bảng loại sản phẩm
        private const val TABLE_LOAISANPHAM = "loaisp"
        private const val lsp_id = "idlsp"
        private const val tenlsp = "tenlsp"
        private const val imglsp = "imglsp"

        // Bảng cart
        private const val TABLE_CART = "cart"
        private const val cartid = "cartid"
        private const val quantity = "quantity"
        private const val total = "total"
        private const val product_id = "product_id"
        private const val customer_id = "customer_id"
        private const val imgproduct = "imgproduct"

        // Bảng đơn hàng
        private const val TABLE_ORDERS = "orders"
        private const val order_id = "order_id"
        private const val order_date = "order_date"
        private const val order_address = "order_address"
        private const val order_customer_id = "customer_id"
        private const val order_totalall = "totalall"
        private const val order_idstatus = "order_idstatus"

        // Bảng chi tiết đơn hàng
        private const val TABLE_ORDER_DETAILS = "order_details"
        private const val order_details_id = "order_details_id"
        private const val order_id_fk = "order_id"
        private const val product_id_fk = "product_id"
        private const val order_quantity = "quantity"
        private const val order_total = "total"

        // Bảng ton kho (inventory)
        private const val TABLE_INVENTORY = "inventory"
        private const val inventory_id = "inventory_id"
        private const val inventory_product_id = "product_id"
        private const val inventory_quantity = "quantity"
        // Bảng phiếu nhập và cột
        private const val TABLE_PHIEU_NHAP = "import_receipts"
        private const val PN_ID = "id"
        private const val PN_NGAY_NHAP = "ngay_nhap"
        private const val PN_NSX = "nsx"
        private const val PN_THANH_TIEN = "thanh_tien"
        private const val PN_GHICHU = "ghichu"
        private const val PN_IDSTATUS = "PN_IDSTATUS"

        // Bảng chi tiết nhập và cột
        private const val TABLE_CHI_TIET_NHAP = "import_receipt_details"
        private const val CTN_ID = "id"
        private const val CTN_SO_LUONG = "so_luong"
        private const val CTN_DON_GIA = "don_gia"
        private const val CTN_THANH_TIEN = "thanh_tien"
        private const val CTN_MA_SP = "ma_sp"
        private const val CTN_PN_ID = "pn_id"
        private const val CTN_MA_STATUS = "ma_status"
        // order status
        private const val TABLE_ORDER_STATUS = "order_status"
        private const val OS_id = "id"
        private const val OS_status = "status"

        // phieu nhap status
        private const val TABLE_PHIEUNHAP_STATUS = "phieunhap_status"
        private const val PS_id = "id"
        private const val PS_status = "status"

        private const val TABLE_WISHLIST = "wishlist"
        private const val WL_id = "WL_id"
        private const val WL_produtid = "WL_produtid"
        private const val WL_customerid = "WL_customerid"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_LSP_TABLE = ("CREATE TABLE $TABLE_LOAISANPHAM ("
                + "$lsp_id TEXT PRIMARY KEY,"
                + "$tenlsp TEXT,"
                + "$imglsp TEXT)")
        db?.execSQL(CREATE_LSP_TABLE)

        val loaiSanPhams = listOf(
            "('L01', 'Áo', 'https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Circle-icons-computer.svg/2048px-Circle-icons-computer.svg.png')",
            "('L02', 'Váy', 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/Circle-icons-dolly.svg/1024px-Circle-icons-dolly.svg.png')",
            "('L03', 'Quần', 'https://static-00.iconduck.com/assets.00/clothing-tshirt-hanger-icon-256x256-53qmx7s1.png')",
            "('L04', 'Chân váy', 'https://icon-library.com/images/clothes-icon/clothes-icon-5.jpg')",
            "('L05', 'Áo', 'https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Circle-icons-computer.svg/2048px-Circle-icons-computer.svg.png')",
            "('L06', 'Váy', 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/Circle-icons-dolly.svg/1024px-Circle-icons-dolly.svg.png')",
            "('L07', 'Quần', 'https://static-00.iconduck.com/assets.00/clothing-tshirt-hanger-icon-256x256-53qmx7s1.png')",
            "('L08', 'Chân váy', 'https://icon-library.com/images/clothes-icon/clothes-icon-5.jpg')",
        )
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE $TABLE_PRODUCTS ("
                + "$COLUMN_ID TEXT PRIMARY KEY,"
                + "$COLUMN_MASP TEXT,"
                + "$COLUMN_TENSP TEXT,"
                + "$COLUMN_GIA REAL,"
                + "$COLUMN_MALOAI TEXT,"
                + "$COLUMN_DONVI TEXT,"
                + "$COLUMN_IMAGE TEXT," +
                "FOREIGN KEY ($COLUMN_MALOAI) REFERENCES $TABLE_LOAISANPHAM($lsp_id))")
        db?.execSQL(CREATE_PRODUCTS_TABLE)


        val products = listOf(
            "('1', 'SP001', 'Mẫu Áo Lớp Oversize Màu Xanh Bích Hình In 10A2', 130000, 'L01', 'Khám phá mẫu áo lớp Oversize màu xanh bích cá tính mang phong cách Streetwear đang làm mưa làm gió tại Homies ngay sau đây!', 'https://homies.vn/wp-content/uploads/2021/12/ao-lop-oversize-mau-xanh-bich-hinh-in-10a2-dep.jpg')",
            "('2', 'SP002', 'Áo lớp Oversize màu trắng', 200000, 'L02', 'Đơn vị 2', 'https://www.inlogo.vn/vnt_upload/File/Image/logo_in_ao_1.jpg')",
            "('3', 'SP003', 'Áo Hoodie Nam Nữ trơn form rộng - Basic Casual Hoodie in Black', 339000, 'L03', 'Áo khoác Hoodie Nam Nữ form rộng thời trang đường phố." +
                    "Thiết kế trơn basic simple trẻ trung dễ phối đồ." +
                    "Kiểu áo hoodie có mũ." +
                    "Kiểu dáng: Form rộng." +
                    "Thích hợp dạo phố, du lịch, đi chơi,...', 'https://zizoou.com/cdn/shop/files/Ao-hoodie-1-Black-5-1_Extras-ZiZoou-Store.jpg?v=1685809835')",
            "('4', 'SP004', 'Áo Hoodie Nam Phi Hành Gia', 180000, 'L01', 'Áo hoodie nam phi hành gia là một trong những sản phẩm bán chạy nhất trên TMĐT thời gian gần đây. Item mang đậm phong cách Hàn Quốc, có điểm nhấn là họa tiết chữ và hình in phi hành gia hút mắt ở mặt trước. Thiết kế này giúp các chàng trai toát lên vẻ trẻ trung, năng động chuẩn “soái ca”.', 'https://dony.vn/wp-content/uploads/2022/01/ao-hoodie-nam-4.jpg')",
            "('5', 'SP005', 'Váy kiểu tay bồng nơ lưng in vân nổi', 365000, 'L02', 'Set váy cổ vuông túi hộp 2 lớp mix chân váy xòe" +
                    "Sản phẩm gồm có 1 áo túi hộp được mix với 1 chân váy xòe dễ thương" +
                    "Phù hợp cho dịp đi chơi, du lịch, dạo phố" +
                    "Chất vải: Chất tơ mềm mại 2 lớp tôn dáng vô cùng luôn ạ, chân váy có quần trong tiện lợi ." +
                    "Size: Phù hợp cho các nàng dưới 57kg', 'https://pos.nvncdn.com/a22d92-78054/ps/20220317_Fmi42nBUXOZ02OKdc3mevpQ3.jpg')",
            "('6', 'SP006', 'Quần Baggy Jeans Nam Nữ ống rộng đẹp', 365000, 'L03', 'Quần Jean Baggy ống suông cao cấp thời trang đường phố. Thiết kế trơn basic simple trẻ trung dễ phối đồ. Kiểu quần baggy ống rộng suông, xắn gấu quần.', 'https://zizoou.com/cdn/shop/products/Quan-Baggy-Jean-nam-nu-2b-1-Quan-ong-rong-xanh-classic-ZiZoou-Store.jpg?v=1680283265')",
            "('7', 'SP007', 'Quần âu nam jbagy dáng ôm slimfit vải Tăm Lì Vi tex cao cấp JA0201', 700.0, 'L01', 'Quần âu nam jbagy dáng ôm slimfit vải Tăm Lì Vi tex cao cấp JA0201', 'https://cdn0918.cdn4s1.com/media/ja0101/ja0101-2510/6.jpg')",
            "('8', 'SP008', 'Váy Chống Nắng UV100 Suptex', 800.0, 'L02', 'Váy Chống Nắng UV100 Suptex-Cool CG21084 Siêu Mỏng Nhẹ. Xuất xứ: Taiwan. Chất liệu: 100% Polyester', 'https://bizweb.dktcdn.net/100/428/762/products/vay-chong-nang-uv100-cg21084-8-1700195546055.jpg?v=1720261473417')",
            "('9', 'SP009', 'Bộ Quần Áo Mặc Nhà Thể Thao Nam Mùa Hè', 169000, 'L03', 'Quần áo nam mùa hè nhà Fox được thiết kế dạng cổ tròn, form rộng, tay lỡ đặc biệt tôn dáng, che khuyết điểm bụng bia đùi to. Với chất liệu cotton 100% Loại 1, co giãn 4c, mềm mịn mát, không xù lông, bộ nam hè nhà Fox có thể dùng là bộ đồ ngủ, mặc đi tập gym, chạy bộ. Thiết kế trang nhã của bộ đồ nàm nhà Fox giúp bạn tự tin mặc ra ngoài đi dạo , đi chơi.', 'https://cf.shopee.vn/file/vn-11134201-23030-trfovp5854nv66')",
            "('10', 'SP010', 'Áo thi đấu bóng đá CAHN 2024', 239000, 'L01', 'Áo đấu câu lạc bộ Công An Hà Nội là trang phục thi đấu của câu lạc bộ Công An Hà Nội tại mùa giải V.League 2023/2024, sản phẩm chính thức được Kamito ra mắt với những màu sắc và thiết kế chỉnh chu đến từng chi tiết.', 'https://product.hstatic.net/1000341630/product/3__2__533ad61a075441b0bffe48aee1f43310_master.jpg')"
        )

        products.forEach { product ->
            val insertProduct = "INSERT INTO $TABLE_PRODUCTS ($COLUMN_ID, $COLUMN_MASP, $COLUMN_TENSP, $COLUMN_GIA, $COLUMN_MALOAI, $COLUMN_DONVI, $COLUMN_IMAGE) VALUES $product"
            db?.execSQL(insertProduct)
        }


        val CREATE_STAFF_TABLE = ("CREATE TABLE $TABLE_NHANVIEN ("
                + "$NV_ID TEXT PRIMARY KEY,"
                + "$NV_TEN TEXT,"
                + "$NV_DIACHI TEXT,"
                + "$NV_NAMSINH TEXT,"
                + "$NV_GIOITINH TEXT CHECK ($NV_GIOITINH IN ('Nam', 'Nữ')),"
                + "$NV_SDT TEXT UNIQUE,"
                + "$NV_EMAIL TEXT UNIQUE,"
                + "$NV_NGAYLAM TEXT,"
                + "$NV_PHONGBAN TEXT,"
                + "$NV_CHUCVU TEXT)"
                )
        db?.execSQL(CREATE_STAFF_TABLE)



        val staff = listOf(
            "('NV001', 'Phan Tú Anh', '123 Main St', '1990-01-01', 'Nữ', '016234e56789', 'nguyenvana@example.com', '2022-01-01', 'Sales', 'Manager')",
            "('NV002', 'Nguyễn Nhật Ánh', '456 Elm St', '1991-02-02', 'Nữ', '0t9867654r321', 'tranthib@example.com', '2022-02-01', 'Marketing', 'Assistant')",
            "('NV003', 'Phạm Trường An', '789 Oak Ave', '1985-03-03', 'Nam', '0156423456789', 'hoangvanc@example.com', '2022-03-01', 'IT', 'Developer')",
            "('NV004', 'Nguyễn Thị Vân Giang', '101 Pine Blvd', '1988-04-04', 'Nữ', '069876554321', 'phamthanhd@example.com', '2022-04-01', 'HR', 'Recruiter')",
            "('NV005', 'Nguyễn Văn Hiếu', '222 Cedar Ln', '1987-05-05', 'Nam', '012354566789', 'levane@example.com', '2022-05-01', 'Finance', 'Accountant')",
            "('NV006', 'Nguyễn Bảo Hân', '333 Maple Dr', '1986-06-06', 'Nữ', '048987654321', 'nguyenthif@example.com', '2022-06-01', 'Sales', 'Representative')",
            "('NV007', 'Phan Hải Đăng', '444 Birch Rd', '1989-07-07', 'Nam', '01234564789', 'tranvang@example.com', '2022-07-01', 'Marketing', 'Coordinator')",
            "('NV008', 'Trần Thị Mai', '555 Willow Ave', '1992-08-08', 'Nữ', '098367654321', 'hothih@example.com', '2022-08-01', 'IT', 'Designer')",
            "('NV009', 'Nguyễn Minh Đức', '666 Pine St', '1993-09-09', 'Nam', '012434556789', 'phanvani@example.com', '2022-09-01', 'HR', 'Assistant')",
            "('NV010', 'Đinh Thị Mai', '777 Oak Rd', '1994-10-10', 'Nữ', '09876554321', 'dothik@example.com', '2022-10-01', 'Finance', 'Analyst')"
        )

        staff.forEach { nv ->
            val insertStaff = "INSERT INTO $TABLE_NHANVIEN ($NV_ID, $NV_TEN, $NV_DIACHI, $NV_NAMSINH, $NV_GIOITINH, $NV_SDT, $NV_EMAIL, $NV_NGAYLAM, $NV_PHONGBAN, $NV_CHUCVU) VALUES $nv"
            db?.execSQL(insertStaff)
        }



        loaiSanPhams.forEach { lsp ->
            val insertLoaiSanPham = "INSERT INTO $TABLE_LOAISANPHAM ($lsp_id, $tenlsp, $imglsp) VALUES $lsp"
            db?.execSQL(insertLoaiSanPham)
        }


        val CREATE_ACCOUNT_TABLE = ("CREATE TABLE $TABLE_ACCOUNT ("
                + "$idaccount TEXT PRIMARY KEY,"
                + "$accname TEXT,"
                + "$accemail TEXT,"
                + "$accsdt TEXT,"
                + "$accpass TEXT)")
        db?.execSQL(CREATE_ACCOUNT_TABLE)

        val CREATE_KH_TABLE = ("CREATE TABLE $TABLE_KH ("
                + "$KH_ID TEXT PRIMARY KEY,"
                + "$KH_TENKH TEXT,"
                + "$KH_DIACHI TEXT,"
                + "$KH_NGAYSINH TEXT,"
                + "$KH_GIOITINH TEXT,"
                + "$KH_SODT TEXT,"
                + "$KH_GHICHU TEXT,"
                + "$KH_KHOANGOAI TEXT,"
                + "FOREIGN KEY ($KH_KHOANGOAI) REFERENCES $TABLE_ACCOUNT($idaccount))")
        db?.execSQL(CREATE_KH_TABLE)


        val customers = listOf(
            "('KH001', 'Trần Quốc Đạt', '123 Main St', '1990-01-01', 'Nam', '012456456789', 'Customer 1', NULL)",
            "('KH002', 'Phạm Thu Hà', '456 Elm St', '1991-02-02', 'Nữ', '098765414321', 'Customer 2', NULL)",
            "('KH003', 'Võ Anh Tài', '789 Oak Ave', '1985-03-03', 'Nam', '047123456789', 'Customer 3', NULL)",
            "('KH004', 'Đinh Thị Mai', '101 Pine Blvd', '1988-04-04', 'Nữ', '094387654321', 'Customer 4', NULL)",
            "('KH005', 'Nguyễn Minh Đông', '222 Cedar Ln', '1987-05-05', 'Nam', '012345675689', 'Customer 5', NULL)",
            "('KH006', 'Nguyễn Thị Hạnh', '333 Maple Dr', '1986-06-06', 'Nữ', '023987654321', 'Customer 6', NULL)",
            "('KH007', 'Mã Văn Tài', '444 Birch Rd', '1989-07-07', 'Nam', '012345516789', 'Customer 7', NULL)",
            "('KH008', 'Nguyễn Thùy Linh', '555 Willow Ave', '1992-08-08', 'Nữ', '098765438321', 'Customer 8', NULL)",
            "('KH009', 'Võ Văn Luân', '666 Pine St', '1993-09-09', 'Nam', '012823456789', 'Customer 9', NULL)",
            "('KH010', 'Nguyễn Thị Vân', '777 Oak Rd', '1994-10-10', 'Nữ', '098765284321', 'Customer 10', NULL)"
        )

        customers.forEach { customer ->
            val insertCustomer = "INSERT INTO $TABLE_KH ($KH_ID, $KH_TENKH, $KH_DIACHI, $KH_NGAYSINH, $KH_GIOITINH, $KH_SODT, $KH_GHICHU, $KH_KHOANGOAI) VALUES $customer"
            db?.execSQL(insertCustomer)
        }

        val CREATE_CART_TABLE = ("CREATE TABLE $TABLE_CART ("
                + "$cartid TEXT PRIMARY KEY,"
                + "$quantity INTEGER,"
                + "$total DOUBLE,"
                +"$imgproduct TEXT,"
                + "$product_id TEXT,"
                + "$customer_id TEXT,"
                + "FOREIGN KEY ($product_id) REFERENCES $TABLE_PRODUCTS($COLUMN_MASP),"
                + "FOREIGN KEY ($customer_id) REFERENCES $TABLE_KH($KH_ID))")
        db?.execSQL(CREATE_CART_TABLE)

        val CREATE_ORDERS_TABLE = ("CREATE TABLE $TABLE_ORDERS ("
                + "$order_id TEXT PRIMARY KEY,"
                + "$order_date TEXT,"
                + "$order_totalall DOUBLE,"
                + "$order_idstatus DOUBLE,"
                + "$order_address nvarchar(40),"
                + "$order_customer_id TEXT,"
                + "FOREIGN KEY ($order_idstatus) REFERENCES $TABLE_ORDER_STATUS($OS_id),"
                + "FOREIGN KEY ($order_customer_id) REFERENCES $TABLE_KH($KH_ID))")
        db?.execSQL(CREATE_ORDERS_TABLE)

        

        val CREATE_ORDER_DETAILS_TABLE = ("CREATE TABLE $TABLE_ORDER_DETAILS ("
                + "$order_details_id TEXT PRIMARY KEY,"
                + "$order_id_fk TEXT,"
                + "$product_id_fk TEXT,"
                + "$order_quantity INTEGER CHECK ($order_quantity > 0),"
                + "$order_total DOUBLE CHECK ($order_total > 0),"
                + "FOREIGN KEY ($order_id_fk) REFERENCES $TABLE_ORDERS($order_id),"
                + "FOREIGN KEY ($product_id_fk) REFERENCES $TABLE_PRODUCTS($COLUMN_ID))")
        db?.execSQL(CREATE_ORDER_DETAILS_TABLE)


        val CREATE_INVENTORY_TABLE = ("CREATE TABLE $TABLE_INVENTORY ("
                + "$inventory_id TEXT PRIMARY KEY,"
                + "$inventory_product_id TEXT,"
                + "$inventory_quantity INTEGER CHECK ($inventory_quantity >= 0),"
                + "FOREIGN KEY ($inventory_product_id) REFERENCES $TABLE_PRODUCTS($COLUMN_ID))")
        db?.execSQL(CREATE_INVENTORY_TABLE)



        val inventories = listOf(
            "('INV001', '1', 50)",
            "('INV002', '2', 30)",
            "('INV003', '3', 20)",
            "('INV004', '4', 25)",
            "('INV005', '5', 40)",
            "('INV006', '6', 35)",
            "('INV007', '7', 60)",
            "('INV008', '8', 15)",
            "('INV009', '9', 10)",
            "('INV010', '10', 55)"
        )

        inventories.forEach { inventory ->
            val insertInventory = "INSERT INTO $TABLE_INVENTORY ($inventory_id, $inventory_product_id, $inventory_quantity) VALUES $inventory"
            db?.execSQL(insertInventory)
        }


        val CREATE_PHIEU_NHAP_TABLE = ("CREATE TABLE $TABLE_PHIEU_NHAP ("
                + "$PN_ID TEXT PRIMARY KEY,"
                + "$PN_NGAY_NHAP TEXT,"
                + "$PN_NSX TEXT,"
                + "$PN_GHICHU TEXT,"

                + "$PN_THANH_TIEN REAL,"
                + "$PN_IDSTATUS TEXT,"
                + "FOREIGN KEY ($PN_IDSTATUS) REFERENCES $TABLE_PHIEUNHAP_STATUS($PS_id))")
        db?.execSQL(CREATE_PHIEU_NHAP_TABLE)

        val phieuNhaps = listOf(
            "('PN001', '2024-07-04', 'NSX001', 'Ghi chú phiếu nhập 1', 500.0,'PS001')",
            "('PN002', '2024-07-04', 'NSX002', 'Ghi chú phiếu nhập 2', 700.0,'PS002')",
            "('PN003', '2024-07-04', 'NSX003', 'Ghi chú phiếu nhập 3', 900.0,'PS002')",
            "('PN004', '2024-07-04', 'NSX004', 'Ghi chú phiếu nhập 4', 600.0,'PS001')",
            "('PN005', '2024-07-04', 'NSX005', 'Ghi chú phiếu nhập 5', 800.0,'PS002')",
            "('PN006', '2024-07-04', 'NSX006', 'Ghi chú phiếu nhập 6', 400.0,'PS002')",
            "('PN007', '2024-07-04', 'NSX007', 'Ghi chú phiếu nhập 7', 300.0,'PS001')",
            "('PN008', '2024-07-04', 'NSX008', 'Ghi chú phiếu nhập 8', 200.0,'PS002')",
            "('PN009', '2024-07-04', 'NSX009', 'Ghi chú phiếu nhập 9',100.0, 'PS002')",
            "('PN010', '2024-07-04', 'NSX010', 'Ghi chú phiếu nhập 10',1200.0, 'PS001')"
        )

        phieuNhaps.forEach { phieuNhap ->
            val insertPhieuNhap = "INSERT INTO $TABLE_PHIEU_NHAP ($PN_ID, $PN_NGAY_NHAP, $PN_NSX, $PN_GHICHU, $PN_THANH_TIEN, $PN_IDSTATUS) VALUES $phieuNhap"
            db?.execSQL(insertPhieuNhap)
        }

        val CREATE_CHI_TIET_NHAP_TABLE = ("CREATE TABLE $TABLE_CHI_TIET_NHAP ("
                + "$CTN_ID TEXT PRIMARY KEY,"
                + "$CTN_SO_LUONG INTEGER,"
                + "$CTN_DON_GIA REAL CHECK ($CTN_DON_GIA > 0),"
                + "$CTN_THANH_TIEN REAL,"
                + "$CTN_MA_SP TEXT,"
                + "$CTN_PN_ID TEXT,"
                + "FOREIGN KEY ($CTN_PN_ID) REFERENCES $TABLE_PHIEU_NHAP($PN_ID),"
                + "FOREIGN KEY ($CTN_MA_SP) REFERENCES $TABLE_PRODUCTS($COLUMN_MASP))")
        db?.execSQL(CREATE_CHI_TIET_NHAP_TABLE)

        val chiTietNhaps = listOf(
            "('CTN001', 10, 50.0, 500.0, 'SP001', 'PN001')",
            "('CTN002', 5, 80.0, 400.0, 'SP002', 'PN001')",
            "('CTN003', 15, 30.0, 450.0, 'SP003', 'PN002')",
            "('CTN004', 20, 40.0, 800.0, 'SP004', 'PN002')",
            "('CTN005', 8, 60.0, 480.0, 'SP005', 'PN003')",
            "('CTN006', 12, 70.0, 840.0, 'SP006', 'PN003')",
            "('CTN007', 6, 90.0, 540.0, 'SP007', 'PN004')",
            "('CTN008', 18, 55.0, 990.0, 'SP008', 'PN004')",
            "('CTN009', 3, 65.0, 195.0, 'SP009', 'PN005')",
            "('CTN010', 7, 75.0, 525.0, 'SP010', 'PN005')"
        )

        chiTietNhaps.forEach { chiTietNhap ->
            val insertChiTietNhap = "INSERT INTO $TABLE_CHI_TIET_NHAP ($CTN_ID, $CTN_SO_LUONG, $CTN_DON_GIA, $CTN_THANH_TIEN, $CTN_MA_SP, $CTN_PN_ID) VALUES $chiTietNhap"
            db?.execSQL(insertChiTietNhap)
        }

        val CREATE_ORDER_STATUS_TABLE = ("CREATE TABLE $TABLE_ORDER_STATUS ("
                + "$OS_id TEXT PRIMARY KEY,"
                + "$OS_status TEXT)")
        db?.execSQL(CREATE_ORDER_STATUS_TABLE)

        val orderStatuses = listOf(
            "('OS001', 'Đang giao')",
            "('OS002', 'Đã giao')",
        )

// Insert initial data into the table
        orderStatuses.forEach { orderStatus ->
            val insertOrderStatus = "INSERT INTO $TABLE_ORDER_STATUS ($OS_id, $OS_status) VALUES $orderStatus"
            db?.execSQL(insertOrderStatus)
        }

        val CREATE_PHIEUNHAP_STATUS_TABLE = ("CREATE TABLE $TABLE_PHIEUNHAP_STATUS ("
                + "$PS_id TEXT PRIMARY KEY,"
                + "$PS_status TEXT)")
        db?.execSQL(CREATE_PHIEUNHAP_STATUS_TABLE)

        val phieuNhapStatuses = listOf(
            "('PS001', 'Đang giao')",
            "('PS002',  'Đã giao')",
        )

// Insert initial data into the table
        phieuNhapStatuses.forEach { phieuNhapStatus ->
            val insertPhieuNhapStatus = "INSERT INTO $TABLE_PHIEUNHAP_STATUS ($PS_id,  $PS_status) VALUES $phieuNhapStatus"
            db?.execSQL(insertPhieuNhapStatus)
        }

        val CREATE_WISHLIST_TABLE = ("CREATE TABLE $TABLE_WISHLIST ("
                + "$WL_id NVARCHAR(50) PRIMARY KEY,"
                + "$WL_produtid TEXT,"
                + "$WL_customerid TEXT,"
                + "FOREIGN KEY ($WL_produtid) REFERENCES $TABLE_PRODUCTS($COLUMN_MASP),"
                + "FOREIGN KEY ($WL_customerid) REFERENCES $TABLE_KH($KH_ID))")
        db?.execSQL(CREATE_WISHLIST_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NHANVIEN")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_LOAISANPHAM")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ACCOUNT")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_KH")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CART")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ORDERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ORDER_DETAILS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_INVENTORY")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PHIEU_NHAP")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CHI_TIET_NHAP")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ORDER_STATUS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PHIEUNHAP_STATUS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_WISHLIST")
        onCreate(db)
    }
    private fun generateUniqueId(): String {
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault())
        return dateFormat.format(Date())
    }



//====================Thêm tài khoản ==========================

    fun addAccount(account: Model_account): String {
        val db = this.writableDatabase
        val id = generateUniqueId()
        val values = ContentValues().apply {
            put(idaccount, id)
            put(accname, account.name)
            put(accemail, account.email)
            put(accsdt, account.phone)
            put(accpass, account.pass)
        }
        val accountId = db.insert(TABLE_ACCOUNT, null, values)

        return id
    }

//=====================Thêm thông tin khách hàng===================
    fun addNameCus(customer: Model_customer, idaccount: String): Long{
        val db = this.writableDatabase
        val values = ContentValues()
        val id = generateUniqueId()
        values.put(KH_ID, id)
        values.put(KH_TENKH, customer.namecus)
        values.put(KH_DIACHI, customer.addresscus)
        values.put(KH_NGAYSINH, customer.daycus)
        values.put(KH_GIOITINH, customer.gender)
        values.put(KH_SODT, customer.phonecus)
        values.put(KH_GHICHU, customer.notecus)
        values.put(KH_KHOANGOAI, idaccount)

        val success = db.insert(TABLE_KH, null, values)
        return success
    }
    fun updateCustomerProfile(customer: Model_customer): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KH_TENKH, customer.namecus)
        values.put(KH_DIACHI, customer.addresscus)
        values.put(KH_NGAYSINH, customer.daycus)
        values.put(KH_GIOITINH, customer.gender)
        values.put(KH_SODT, customer.phonecus)
        values.put(KH_GHICHU, customer.notecus)

        // Thay đổi tham số trong mảng selectionArgs
        val affectedRows = db.update(TABLE_KH, values, "$KH_KHOANGOAI = ?", arrayOf(customer.idaccount.toString()))
        return affectedRows
    }

//========================Thêm sản phẩm=====================
    fun addProduct(product: Model_product): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        val uniqueId = generateUniqueId()
        values.put(COLUMN_ID, uniqueId)
        values.put(COLUMN_MASP, product.masp)
        values.put(COLUMN_TENSP, product.tensp)
        values.put(COLUMN_GIA, product.gia)
        values.put(COLUMN_MALOAI, product.maloai)
        values.put(COLUMN_DONVI, product.donvi)
        values.put(COLUMN_IMAGE, product.img)

        // Inserting Row
        val success = db.insert(TABLE_PRODUCTS, null, values)

        return success
    }

    fun updateProduct(product: Model_product, id:Long): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_MASP, product.masp)
        values.put(COLUMN_TENSP, product.tensp)
        values.put(COLUMN_GIA, product.gia)
        values.put(COLUMN_MALOAI, product.maloai)
        values.put(COLUMN_DONVI, product.donvi)
        values.put(COLUMN_IMAGE, product.img)

        // Updating row
        return db.update(TABLE_PRODUCTS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
    fun updateProductType(producttype: Model_producttype, id: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(tenlsp, producttype.tenlsp)
        values.put(imglsp , producttype.imglsp)

        // Updating row
        return db.update(TABLE_LOAISANPHAM, values, "$lsp_id = ?", arrayOf(id.toString()))
    }

    fun updateInventory(inventory: Model_inventory, id: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(inventory_product_id, inventory.productId)
        values.put(inventory_quantity , inventory.quantity)

        // Updating row
        return db.update(TABLE_INVENTORY, values, "$inventory_id = ?", arrayOf(id.toString()))
    }
    fun updateorderStatus(orderstatus: Model_OrderStatus, id: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(OS_status, orderstatus.orderstatusid)

        // Updating row
        return db.update(TABLE_ORDER_STATUS, values, "$OS_id = ?", arrayOf(id))
    }

    fun updatepnStatus(pnstatus: Model_Phieunhap_status, id: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PS_status, pnstatus.pnstatusid)

        // Updating row
        return db.update(TABLE_PHIEUNHAP_STATUS, values, "$PS_id = ?", arrayOf(id))
    }

    fun updateStaff(staff: Model_staff, staffid: String): Int{
        val db = writableDatabase
        val values = ContentValues()
        values.put(NV_TEN, staff.tennv)
        values.put(NV_DIACHI, staff.dcnv)
        values.put(NV_NAMSINH, staff.nsnv)
        values.put(NV_GIOITINH, staff.gtnv)
        values.put(NV_SDT, staff.sdtnv)
        values.put(NV_EMAIL, staff.emailnv)
        values.put(NV_NGAYLAM, staff.ngaylam)
        values.put(NV_PHONGBAN, staff.pbanvn)
        values.put(NV_CHUCVU, staff.cvunv)

        return db.update(TABLE_NHANVIEN, values, "$NV_ID = ?", arrayOf(staffid))
    }
    fun updateCustomer(customer: Model_customer, id: String): Int{
        val db = writableDatabase
        val values = ContentValues()
        values.put(KH_TENKH, customer.namecus)
        values.put(KH_DIACHI, customer.addresscus)
        values.put(KH_NGAYSINH, customer.daycus)
        values.put(KH_GIOITINH, customer.gender)
        values.put(KH_SODT, customer.phonecus)
        values.put(KH_GHICHU, customer.notecus)
        return db.update(TABLE_KH, values, "$KH_ID = ?", arrayOf(id))
    }

    fun updatePhieuNhap(phieunhap: Model_Phieunhap, id: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(PN_NGAY_NHAP, phieunhap.ngayNhap)
            put(PN_NSX, phieunhap.nsx)
            put(PN_GHICHU, phieunhap.ghichu)
            put(PN_THANH_TIEN, phieunhap.thanhTien)
            put(PN_IDSTATUS, phieunhap.idpnstatus)
        }
        return db.update(TABLE_PHIEU_NHAP,values ,"$PN_ID = ? ", arrayOf(id))
    }

    fun updateOrder(order: Model_Order, id: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(order_date, order.orderday)
            put(order_address, order.address)
            put(order_customer_id, order.customerid)
            put(order_totalall, order.thanhtien)
            put(order_idstatus, order.id_status)
        }
        return db.update(TABLE_ORDERS,values ,"$order_id = ? ", arrayOf(id))
    }
    fun updateChiTietNhap(ctphieunhap: Model_CTPhieuNhap): Int {
        val db = this.writableDatabase
        var total = 0.0

        // Retrieve the current total amount for the import receipt
        val cursor = db.query(
            TABLE_PHIEU_NHAP,
            arrayOf(PN_THANH_TIEN),
            "$PN_ID = ?",
            arrayOf(ctphieunhap.pnId),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(cursor.getColumnIndexOrThrow(PN_THANH_TIEN))
        }

        cursor.close()

        // Calculate the new total amount
        val oldChiTietTotal = getOldChiTietTotal(db, ctphieunhap.idctpn)
        val newTotal = total - oldChiTietTotal + ctphieunhap.thanhTien

        val values = ContentValues().apply {
            put(CTN_SO_LUONG, ctphieunhap.soLuong)
            put(CTN_DON_GIA, ctphieunhap.donGia)
            put(CTN_THANH_TIEN, ctphieunhap.thanhTien)
        }

        // Update the specific detail entry
        val updateResult = db.update(
            TABLE_CHI_TIET_NHAP,
            values,
            "$CTN_ID = ?",
            arrayOf(ctphieunhap.idctpn)
        )

        if (updateResult > 0) {
            // Update the total amount in the import receipt
            val updateValues = ContentValues().apply {
                put(PN_THANH_TIEN, newTotal)
            }
            val whereClause = "$PN_ID = ?"
            val whereArgs = arrayOf(ctphieunhap.pnId)
            db.update(TABLE_PHIEU_NHAP, updateValues, whereClause, whereArgs)
        }

        return updateResult
    }

    private fun getOldChiTietTotal(db: SQLiteDatabase, idctpn: String): Double {
        var oldTotal = 0.0
        val cursor = db.query(
            TABLE_CHI_TIET_NHAP,
            arrayOf(CTN_THANH_TIEN),
            "$CTN_ID = ?",
            arrayOf(idctpn),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            oldTotal = cursor.getDouble(cursor.getColumnIndexOrThrow(CTN_THANH_TIEN))
        }

        cursor.close()
        return oldTotal
    }

    fun updateOrderDetail(ctdonhang: Model_OrderDetail): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(product_id_fk, ctdonhang.productid)
            put(order_quantity, ctdonhang.quantity)
            put(order_total, ctdonhang.total)
        }

        // Specify the WHERE clause to identify which row to update
        val whereClause = "$order_details_id = ?"
        val whereArgs = arrayOf(ctdonhang.orderdetailid)

        return db.update(
            TABLE_ORDER_DETAILS,
            values,
            whereClause,
            whereArgs
        )
    }


    fun deleteProduct(productId: String): Int {
        val db = this.writableDatabase

        // Deleting row
        return db.delete(TABLE_PRODUCTS, "$COLUMN_ID = ?", arrayOf(productId))
    }

    @SuppressLint("Range")
    fun getProductId(masp: String): Long? {
        val db = this.readableDatabase
        var productId: Long? = null
        val query = "SELECT $COLUMN_ID FROM $TABLE_PRODUCTS WHERE $COLUMN_MASP = ?"
        val cursor = db.rawQuery(query, arrayOf(masp))

        if (cursor.moveToFirst()) {
            productId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        }
        cursor.close()
        db.close()
        return productId
    }


    //=======================Thêm trạng thái đơn hàng================
    fun addOrderstatus(orderstatus: Model_OrderStatus) : Long{
        val db = this.writableDatabase
        val values = ContentValues()
        val id = generateUniqueId()
        values.put(OS_status, orderstatus.status)
        values.put(OS_id, id)

        val success = db.insert(TABLE_ORDER_STATUS, null, values)

        return success
    }
//    fun updateOrderstatus(product: Model_OrderStatus, id:Long): Int {
//        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put(OS_orderid, product.orderid)
//        values.put(OS_status, product.status)
//
//        // Updating row
//        return db.update(TABLE_ORDER_STATUS, values, "$OS_id = ?", arrayOf(id.toString()))
//    }

    fun deleteOrderstatus(orderid: String): Int {
        val db = this.writableDatabase

        // Deleting row
        return db.delete(TABLE_ORDER_STATUS, "$OS_id = ?", arrayOf(orderid))
    }


//    ============================Thêm trạng thái phiếu nhập==============
fun addPNstatus(pnstatus: Model_Phieunhap_status) : Long{
        val db = this.writableDatabase
        val values = ContentValues()
    val id = generateUniqueId()
        values.put(PS_id, id)
        values.put(PS_status, pnstatus.status)

        val success = db.insert(TABLE_PHIEUNHAP_STATUS, null, values)

        return success
    }
//    fun updateProduct(product: Model_product, id:Long): Int {
//        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put(COLUMN_MASP, product.masp)
//        values.put(COLUMN_TENSP, product.tensp)
//        values.put(COLUMN_GIA, product.gia)
//        values.put(COLUMN_MALOAI, product.maloai)
//        values.put(COLUMN_DONVI, product.donvi)
//        values.put(COLUMN_IMAGE, product.img)
//
//        // Updating row
//        return db.update(TABLE_PRODUCTS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
//    }
//
    fun deletePNStatus(pnstatusid: String): Int {
        val db = this.writableDatabase

        // Deleting row
        return db.delete(TABLE_PHIEUNHAP_STATUS, "$PS_id = ?", arrayOf(pnstatusid))
    }
//    ================Thêm tồn kho==============================

    fun addInventory(invetory: Model_inventory): Long {
        val db = this.writableDatabase
        val id = generateUniqueId()
        val values = ContentValues().apply {
            put(inventory_id, id)
            put(inventory_product_id, invetory.productId)
            put(inventory_quantity, invetory.quantity)
        }
        val result = db.insert(TABLE_INVENTORY, null, values)

        return result
    }
//    fun updateProduct(product: Model_product, id:Long): Int {
        //        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put(COLUMN_MASP, product.masp)
//        values.put(COLUMN_TENSP, product.tensp)
//        values.put(COLUMN_GIA, product.gia)
//        values.put(COLUMN_MALOAI, product.maloai)
//        values.put(COLUMN_DONVI, product.donvi)
//        values.put(COLUMN_IMAGE, product.img)
//
//        // Updating row
//        return db.update(TABLE_PRODUCTS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
//    }
//
    fun deleteInventory(inventoryId: String): Int {
        val db = this.writableDatabase

        // Deleting row
        return db.delete(TABLE_INVENTORY, "$inventory_id = ?", arrayOf(inventoryId))
    }
//    ===================Thêm nhân viên==================
    fun addStaff(staff: Model_staff): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        val id = generateUniqueId()
        values.put(NV_ID, id)
        values.put(NV_TEN, staff.tennv)
        values.put(NV_GIOITINH, staff.gtnv)
        values.put(NV_NAMSINH, staff.nsnv)
        values.put(NV_CHUCVU, staff.cvunv)
        values.put(NV_PHONGBAN, staff.pbanvn)
        values.put(NV_NGAYLAM, staff.ngaylam)
        values.put(NV_DIACHI, staff.dcnv)
        values.put(NV_SDT, staff.sdtnv)
        values.put(NV_EMAIL, staff.emailnv)

        val success = db.insert(TABLE_NHANVIEN, null, values)
        return success
    }
    //    fun updateProduct(product: Model_product, id:Long): Int {
    //        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put(COLUMN_MASP, product.masp)
//        values.put(COLUMN_TENSP, product.tensp)
//        values.put(COLUMN_GIA, product.gia)
//        values.put(COLUMN_MALOAI, product.maloai)
//        values.put(COLUMN_DONVI, product.donvi)
//        values.put(COLUMN_IMAGE, product.img)
//
//        // Updating row
//        return db.update(TABLE_PRODUCTS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
//    }
//
    fun deleteStaff(staffId: String): Int {
        val db = this.writableDatabase

        // Deleting row
        return db.delete(TABLE_NHANVIEN, "$NV_ID = ?", arrayOf(staffId))
    }
//    ===================Thêm loại sản phẩm================

    fun addLoaiSP(lsp: Model_producttype):Long{
        val db = this.writableDatabase
        val values = ContentValues()
        val id = generateUniqueId()
        values.put(lsp_id, id)
        values.put(tenlsp, lsp.tenlsp)
        values.put(imglsp, lsp.imglsp)

        val success = db.insert(TABLE_LOAISANPHAM, null, values)
        return success
    }
    //    fun updateProduct(product: Model_product, id:Long): Int {
    //        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put(COLUMN_MASP, product.masp)
//        values.put(COLUMN_TENSP, product.tensp)
//        values.put(COLUMN_GIA, product.gia)
//        values.put(COLUMN_MALOAI, product.maloai)
//        values.put(COLUMN_DONVI, product.donvi)
//        values.put(COLUMN_IMAGE, product.img)
//
//        // Updating row
//        return db.update(TABLE_PRODUCTS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
//    }
//
    fun deleteProducttype(producttypeId: String): Int {
        val db = this.writableDatabase

        // Deleting row
        return db.delete(TABLE_LOAISANPHAM, "$lsp_id = ?", arrayOf(producttypeId))
    }

//    =================Thêm khách hàng======================

    fun addCustomer(customer: Model_customer): Long{
        val db = this.writableDatabase
        val values = ContentValues()
        val id = generateUniqueId()
        values.put(KH_ID , id)
        values.put(KH_TENKH, customer.namecus)
        values.put(KH_DIACHI, customer.addresscus)
        values.put(KH_NGAYSINH, customer.daycus)
        values.put(KH_GIOITINH, customer.gender)
        values.put(KH_SODT, customer.phonecus)
        values.put(KH_GHICHU, customer.notecus)

        val success = db.insert(TABLE_KH, null, values)

        return success;
    }
    //    fun updateProduct(product: Model_product, id:Long): Int {
    //        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put(COLUMN_MASP, product.masp)
//        values.put(COLUMN_TENSP, product.tensp)
//        values.put(COLUMN_GIA, product.gia)
//        values.put(COLUMN_MALOAI, product.maloai)
//        values.put(COLUMN_DONVI, product.donvi)
//        values.put(COLUMN_IMAGE, product.img)
//
//        // Updating row
//        return db.update(TABLE_PRODUCTS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
//    }
//
    fun deleteCustomer(khid: String): Int {
        val db = this.writableDatabase

        // Deleting row
        return db.delete(TABLE_KH, "$KH_ID = ?", arrayOf(khid))
    }

//    ====================Thêm giỏ hàng====================

    fun addCart(cart: Model_cart): Long{
        val db = this.writableDatabase
        val values = ContentValues()
        val id = generateUniqueId()
        values.put(cartid, id)
        values.put(total, cart.thanhtien)
        values.put(quantity, cart.quantity)
        values.put(product_id, cart.product_id)
        values.put(customer_id, cart.customer_id)
        values.put(imgproduct, cart.imgproduct)

        val success = db.insert(TABLE_CART, null, values)

        return success
    }

    fun addWishList(wishlist: Model_wishlist): Long{
        val db = this.writableDatabase
        val values = ContentValues()
        val id = generateUniqueId()
        values.put(WL_id, id)
        values.put(WL_produtid, wishlist.product_id)
        values.put(WL_customerid, wishlist.customer_id)

        val success = db.insert(TABLE_WISHLIST, null, values)

        return success
    }

    //    fun updateProduct(product: Model_product, id:Long): Int {
    //        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put(COLUMN_MASP, product.masp)
//        values.put(COLUMN_TENSP, product.tensp)
//        values.put(COLUMN_GIA, product.gia)
//        values.put(COLUMN_MALOAI, product.maloai)
//        values.put(COLUMN_DONVI, product.donvi)
//        values.put(COLUMN_IMAGE, product.img)
//
//        // Updating row
//        return db.update(TABLE_PRODUCTS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
//    }
//
//    fun deleteProduct(productId: String): Int {
//        val db = this.writableDatabase
//
//        // Deleting row
//        return db.delete(TABLE_PRODUCTS, "$COLUMN_ID = ?", arrayOf(productId))
//    }

//    ==================Thêm phiếu nhập=====================

    fun addPhieuNhap(phieunhap: Model_Phieunhap): Long {
        val db = this.writableDatabase
        val id = generateUniqueId()
        val values = ContentValues().apply {
            put(PN_ID, id)
            put(PN_NGAY_NHAP, phieunhap.ngayNhap)
            put(PN_NSX, phieunhap.nsx)
            put(PN_GHICHU, phieunhap.ghichu)
            put(PN_THANH_TIEN, phieunhap.thanhTien)
            put(PN_IDSTATUS, phieunhap.idpnstatus)
        }
        return db.insert(TABLE_PHIEU_NHAP, null, values)
    }

    //    fun updateProduct(product: Model_product, id:Long): Int {
    //        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put(COLUMN_MASP, product.masp)
//        values.put(COLUMN_TENSP, product.tensp)
//        values.put(COLUMN_GIA, product.gia)
//        values.put(COLUMN_MALOAI, product.maloai)
//        values.put(COLUMN_DONVI, product.donvi)
//        values.put(COLUMN_IMAGE, product.img)
//
//        // Updating row
//        return db.update(TABLE_PRODUCTS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
//    }
//
    fun deletePhieuNhap(phieunhapId: String): Int {
        val db = this.writableDatabase

        // Deleting row
        return db.delete(TABLE_PHIEU_NHAP, "$PN_ID = ?", arrayOf(phieunhapId))
    }

//    ===============Thêm chi tiết phiếu nhập===================

    fun addChiTietNhap(ctphieunhap: Model_CTPhieuNhap): Long {
        val db = this.writableDatabase
        var total = 0.0;
        val id = generateUniqueId()
        val cursor = db.query(
            TABLE_PHIEU_NHAP,
            arrayOf(PN_THANH_TIEN),
            "$PN_ID = ?",
            arrayOf(ctphieunhap.pnId),
            null,
            null,
            null
        )


        if (cursor.moveToFirst()) {
            total = cursor.getDouble(cursor.getColumnIndexOrThrow(PN_THANH_TIEN))
        }

        cursor.close()

        val newTotal = total + ctphieunhap.thanhTien
        val values = ContentValues().apply {
            put(CTN_ID, id)
            put(CTN_SO_LUONG, ctphieunhap.soLuong)
            put(CTN_DON_GIA, ctphieunhap.donGia)
            put(CTN_THANH_TIEN, ctphieunhap.thanhTien)
            put(CTN_MA_SP, ctphieunhap.maSp)
            put(CTN_PN_ID, ctphieunhap.pnId)
            total = total+ctphieunhap.thanhTien;
        }
        val insertresult =  db.insert(TABLE_CHI_TIET_NHAP, null, values)

        if (insertresult != -1L) {
            // Update the total amount in the import receipt
            val updateValues = ContentValues().apply {
                put(PN_THANH_TIEN, newTotal)
            }
            val whereClause = "$PN_ID = ?"
            val whereArgs = arrayOf(ctphieunhap.pnId)
            db.update(TABLE_PHIEU_NHAP, updateValues, whereClause, whereArgs)
        }
        return  insertresult
    }

    fun deletectphieunhap(ctpnid: String): Int {
        val db = this.writableDatabase

        // Deleting row
        return db.delete(TABLE_CHI_TIET_NHAP, "$CTN_ID = ?", arrayOf(ctpnid))
    }

//    ===============Lấy phiếu nhập====================

    @SuppressLint("Range")
    fun getAllPhieuNhap(): ArrayList<Model_Phieunhap> {
        val phieuNhapList = ArrayList<Model_Phieunhap>()
        val selectQuery = "SELECT * FROM $TABLE_PHIEU_NHAP"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val phieuNhap = Model_Phieunhap(
                    ngayNhap = cursor.getString(cursor.getColumnIndex(PN_NGAY_NHAP)),
                    nsx = cursor.getString(cursor.getColumnIndex(PN_NSX)),
                    thanhTien = cursor.getDouble(cursor.getColumnIndex(PN_THANH_TIEN)),
                    ghichu = cursor.getString(cursor.getColumnIndex(PN_GHICHU)),
                    idpn = cursor.getString(cursor.getColumnIndex(PN_ID)),
                    idpnstatus = cursor.getString(cursor.getColumnIndex(PN_IDSTATUS))

                )
                phieuNhapList.add(phieuNhap)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return phieuNhapList
    }

    //===========================Đơn hàng ==========================
    fun addOrder(order: Model_Order): Long{
        val db = writableDatabase
        val id = generateUniqueId()
        val values = ContentValues().apply {
            put(order_id, id)
            put(order_address, order.address)
            put(order_idstatus, order.id_status)
            put(order_date, order.orderday)
            put(order_customer_id, order.customerid)
            put(order_totalall, order.thanhtien)
        }
        return  db.insert(TABLE_ORDERS, null, values)
    }

    fun deleteOrder(idorder: String): Int{
        val db = writableDatabase
        return db.delete(TABLE_ORDERS, "$order_id = ? ", arrayOf(idorder))
    }

    //======Thêm chi tiết hóa đơn ============
    fun addctdonhang(ctdonhang: Model_OrderDetail): Long {
        val db = writableDatabase
        val id = generateUniqueId()
        val values = ContentValues().apply {
            put(order_details_id, id)
            put(order_id_fk, ctdonhang.orderid)
            put(product_id_fk, ctdonhang.productid)
            put(order_quantity, ctdonhang.quantity)
            put(order_total, ctdonhang.total)
        }

        return db.insert(TABLE_ORDER_DETAILS, null , values)
    }

    //============================L ct phieu nhap ========
    @SuppressLint("Range")
    fun getAllCtPhieuNhap(idpn :  String) :ArrayList<Model_CTPhieuNhap>{
        val db = this.writableDatabase
        val ctphieunhapList = ArrayList<Model_CTPhieuNhap>()
        val selectQuery = "SELECT * FROM $TABLE_CHI_TIET_NHAP WHERE $CTN_PN_ID = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(idpn))
        if(cursor.moveToFirst()){
            do {
                val ctphieunhap = Model_CTPhieuNhap(
                    idctpn = cursor.getString(cursor.getColumnIndex(CTN_ID)),
                    soLuong = cursor.getInt(cursor.getColumnIndex(CTN_SO_LUONG)),
                    donGia = cursor.getDouble(cursor.getColumnIndex(CTN_DON_GIA)),
                    thanhTien = cursor.getDouble(cursor.getColumnIndex(CTN_THANH_TIEN)),
                    maSp = cursor.getString(cursor.getColumnIndex(CTN_MA_SP)),
                    pnId = cursor.getString(cursor.getColumnIndex(CTN_PN_ID)),
                )
                ctphieunhapList.add(ctphieunhap)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return ctphieunhapList
    }
    //============================L ct phieu nhap ========
    @SuppressLint("Range")
    fun getAllCtDonhang(iddh :  String) :ArrayList<Model_OrderDetail>{
        val db = this.writableDatabase
        val ctdonhangList = ArrayList<Model_OrderDetail>()
        val selectQuery = "SELECT *FROM $TABLE_ORDER_DETAILS WHERE $order_id_fk = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(iddh))
        if(cursor.moveToFirst()){
            do {
                val ctdonhang = Model_OrderDetail(
                    orderdetailid = cursor.getString(cursor.getColumnIndex(order_details_id)),
                    orderid = cursor.getString(cursor.getColumnIndex(order_id)),
                    productid = cursor.getString(cursor.getColumnIndex(product_id_fk)),
                    quantity = cursor.getInt(cursor.getColumnIndex(order_quantity)),
                    total = cursor.getDouble(cursor.getColumnIndex(order_total)),
                )
                ctdonhangList.add(ctdonhang)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return ctdonhangList
    }
//===================Lấy trạng thái đơn hàng=======================
    @SuppressLint("Range")
    fun getAllOrderStatus(): ArrayList<Model_OrderStatus> {
        val orderstatuslist = ArrayList<Model_OrderStatus>()
        val selectQuery = "SELECT *FROM $TABLE_ORDER_STATUS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val statusorder = Model_OrderStatus(
                    status = cursor.getString(cursor.getColumnIndex(OS_status)),
                    orderstatusid = cursor.getString(cursor.getColumnIndex(OS_id)),

                )
                orderstatuslist.add(statusorder)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return orderstatuslist
    }
    //===================Lấy trạng thái phhieeus nhập=======================
    @SuppressLint("Range")
    fun getAllPNStatus(): ArrayList<Model_Phieunhap_status> {
        val pnstatuslist = ArrayList<Model_Phieunhap_status>()
        val selectQuery = "SELECT *FROM $TABLE_PHIEUNHAP_STATUS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val statuspn = Model_Phieunhap_status(
                    status = cursor.getString(cursor.getColumnIndex(PS_status)),
                    pnstatusid = cursor.getString(cursor.getColumnIndex(PS_id))

                    )
                pnstatuslist.add(statuspn)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return pnstatuslist
    }
    //===================Lấy tồn kho=======================
    @SuppressLint("Range")
    fun getAllInventory(): ArrayList<Model_inventory>{
        val inventorylist = ArrayList<Model_inventory>()
        val selectQuery = "SELECT *FROM $TABLE_INVENTORY"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do {
                val inventory = Model_inventory(
                    productId = cursor.getString(cursor.getColumnIndex(inventory_product_id)),
                    quantity = cursor.getInt(cursor.getColumnIndex(inventory_quantity)),
                    inventoryid = cursor.getString(cursor.getColumnIndex(inventory_id))
                )
                inventorylist.add(inventory)
            }while (cursor.moveToNext())
        }
        return inventorylist
    }

    fun checkout(cartItems: List<Pair<Model_product, Int>>, customerId: String, address: String,idstatus: String ): String {
        // Mở kết nối đến cơ sở dữ liệu
        val db = this.writableDatabase
        // Bắt đầu giao dịch
        db.beginTransaction()
        // Khởi tạo orderId
        var orderId: String = ""

        try {
            // Tạo đơn hàng
            orderId = generateUniqueId()
            val orderValues = ContentValues().apply {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val todayDate = dateFormat.format(System.currentTimeMillis())
                put(order_id, orderId)
                put(order_date, todayDate) // hoặc sử dụng định dạng ngày thích hợp
                put(order_customer_id, customerId)
                put(order_address, address)
                put(order_idstatus, idstatus )
            }

            // Thêm đơn hàng vào cơ sở dữ liệu và nhận orderId
            val orderInsertResult = db.insert(TABLE_ORDERS, null, orderValues)

            // Nếu orderId khác -1 (không xảy ra lỗi)
            if (orderInsertResult != -1L) {
                var totalAll = 0.0
                // Tạo chi tiết đơn hàng cho từng sản phẩm trong giỏ hàng
                for ((product, quantity) in cartItems) {
                    val productId = getIdProduct(product.productid) // Lấy ID của sản phẩm từ mã sản phẩm
                    val total = product.gia * quantity
                    totalAll += total
                    val orderDetailId = generateUniqueId()
                    val orderDetailValues = ContentValues().apply {
                        put(order_details_id, orderDetailId)
                        put(order_id_fk, orderId)
                        put(product_id_fk, productId)
                        put(order_quantity, quantity)
                        put(order_total, total)

                    }
                    // Thêm chi tiết đơn hàng vào cơ sở dữ liệu
                    db.insert(TABLE_ORDER_DETAILS, null, orderDetailValues)
                }

                val orderUpdateValues = ContentValues().apply {
                    put(order_totalall, totalAll)
                }
                val whereClause = "$order_id = ?"
                val whereArgs = arrayOf(orderId.toString())
                db.update(TABLE_ORDERS, orderUpdateValues, whereClause, whereArgs)

                // Clear the cart after successful order creation
                val cartDeleteWhereClause = "$customer_id = ?"
                val cartDeleteWhereArgs = arrayOf(customerId.toString())
                db.delete(TABLE_CART, cartDeleteWhereClause, cartDeleteWhereArgs)

                // Đặt giao dịch thành công
                db.setTransactionSuccessful()
            }
        } finally {
            // Kết thúc giao dịch
            db.endTransaction()
        }
        // Trả về orderId
        return orderId
    }




    @SuppressLint("Range")
    fun getAllproduct(): ArrayList<Model_product> {
        val productList: ArrayList<Model_product> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_PRODUCTS "
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val product = Model_product(
                    productid = cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                    masp = cursor.getString(cursor.getColumnIndex(COLUMN_MASP)),
                    tensp = cursor.getString(cursor.getColumnIndex(COLUMN_TENSP)),
                    gia = cursor.getDouble(cursor.getColumnIndex(COLUMN_GIA)),
                    maloai = cursor.getString(cursor.getColumnIndex(COLUMN_MALOAI)),
                    donvi = cursor.getString(cursor.getColumnIndex(COLUMN_DONVI)),
                    img = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                )
                productList.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return productList
    }
    @SuppressLint("Range")
    fun getAllProductType(): ArrayList<Model_producttype>{
        val producttypeList: ArrayList<Model_producttype> = ArrayList()
        val selectQuery = "SELECT *FROM $TABLE_LOAISANPHAM"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do {
                val producttype = Model_producttype(
                    tenlsp = cursor.getString(cursor.getColumnIndex(tenlsp)),
                    imglsp = cursor.getString(cursor.getColumnIndex(imglsp)),
                    producttypeid = cursor.getString(cursor.getColumnIndex(lsp_id))
                )
                producttypeList.add(producttype)
            }while (cursor.moveToNext())
        }
        cursor.close()

        return producttypeList
    }
    @SuppressLint("Range")
    fun getAllCustomer(): ArrayList<Model_customer>{
        val customerList: ArrayList<Model_customer> = ArrayList()
        val selecQuery  ="SELECT *FROM $TABLE_KH"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selecQuery, null)
        if (cursor.moveToNext()){
            do {
                val customer = Model_customer(
                    namecus = cursor.getString(cursor.getColumnIndex(KH_TENKH)),
                    addresscus = cursor.getString(cursor.getColumnIndex(KH_DIACHI)),
                    daycus = cursor.getString(cursor.getColumnIndex(KH_NGAYSINH)),
                    gender = cursor.getString(cursor.getColumnIndex(KH_GIOITINH)),
                    phonecus = cursor.getString(cursor.getColumnIndex(KH_SODT)),
                    notecus = cursor.getString(cursor.getColumnIndex(KH_GHICHU)),
                    idaccount = cursor.getString(cursor.getColumnIndex(KH_ID)),
                    customerid = cursor.getString(cursor.getColumnIndex(KH_ID))
                )
                customerList.add(customer)
            }while (cursor.moveToNext())

        }
        cursor.close()

        return customerList
    }

    @SuppressLint("Range")
    fun getAlOrder(): ArrayList<Model_Order>{
        val orderlist: ArrayList<Model_Order> = ArrayList()
        val selecQuery  ="SELECT *FROM $TABLE_ORDERS"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selecQuery, null)
        if (cursor.moveToNext()){
            do {
                val order = Model_Order(
                    orderday = cursor.getString(cursor.getColumnIndex(order_date)),
                    thanhtien = cursor.getDouble(cursor.getColumnIndex(order_totalall)),
                    customerid = cursor.getString(cursor.getColumnIndex(order_customer_id)),
                    id_status = cursor.getString(cursor.getColumnIndex(order_idstatus)),
                    address = cursor.getString(cursor.getColumnIndex(order_address)),
                    order_id = cursor.getString(cursor.getColumnIndex(order_id))
                )
                orderlist.add(order)
            }while (cursor.moveToNext())

        }
        cursor.close()

        return orderlist
    }

    @SuppressLint("Range")
    fun getAllStaff() : ArrayList<Model_staff>{
        val staffList: ArrayList<Model_staff> = ArrayList()
        val selectQuery = "SELECT *FROM $TABLE_NHANVIEN"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do {
                val staff = Model_staff(
                    tennv = cursor.getString(cursor.getColumnIndex(NV_TEN)),
                    dcnv = cursor.getString(cursor.getColumnIndex(NV_DIACHI)),
                    emailnv = cursor.getString(cursor.getColumnIndex(NV_EMAIL)),
                    nsnv = cursor.getString(cursor.getColumnIndex(NV_NAMSINH)),
                    gtnv = cursor.getString(cursor.getColumnIndex(NV_GIOITINH)),
                    sdtnv = cursor.getString(cursor.getColumnIndex(NV_SDT)),
                    ngaylam = cursor.getString(cursor.getColumnIndex(NV_NGAYLAM)),
                    pbanvn = cursor.getString(cursor.getColumnIndex(NV_PHONGBAN)),
                    cvunv = cursor.getString(cursor.getColumnIndex(NV_CHUCVU)),
                    staffid = cursor.getString(cursor.getColumnIndex(NV_ID)),
                )
                staffList.add(staff)
            }while (cursor.moveToNext())
        }
        cursor.close()

        return staffList
    }

    fun checkUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ACCOUNT WHERE $accemail = ? AND $accpass = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))
        val exists = cursor.count > 0
        cursor.close()

        return exists
    }
    fun getAccountIdByEmail(email: String): String {
        val db = this.readableDatabase
        val query = "SELECT $idaccount FROM $TABLE_ACCOUNT WHERE $accemail = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        var accountId: String = "null"
        if (cursor.moveToFirst()) {
            accountId = cursor.getString(cursor.getColumnIndexOrThrow(idaccount))
        }
        cursor.close()

        return accountId
    }

    fun getIDKH(accountId: String): String{
        val db = this.readableDatabase
        val query = "SELECT $KH_ID FROM $TABLE_KH WHERE $KH_KHOANGOAI = ? "
        val cursor = db.rawQuery(query, arrayOf(accountId.toString()))
        var idkh = ""
        if(cursor.moveToFirst()){
            idkh = cursor.getString(cursor.getColumnIndexOrThrow(KH_ID))
        }
        cursor.close()

        return idkh
    }

    fun getIdProduct(masp: String):String{
        val db = this.writableDatabase
        val query = "SELECT $COLUMN_ID FROM $TABLE_PRODUCTS WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(masp))
        var idsp : String= ""
        if(cursor.moveToFirst()){
            idsp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID))
        }
        cursor.close()

        return  idsp
    }

    @SuppressLint("Range")
    fun getCartWithQuantity(customerId: String): List<Pair<Model_product, Int>> {
        val cartProducts: ArrayList<Pair<Model_product, Int>> = ArrayList()
        val query = """
        SELECT $TABLE_PRODUCTS.$COLUMN_MASP, $TABLE_PRODUCTS.$COLUMN_TENSP, 
               $TABLE_PRODUCTS.$COLUMN_GIA, $TABLE_PRODUCTS.$COLUMN_MALOAI, 
               $TABLE_PRODUCTS.$COLUMN_DONVI, $TABLE_PRODUCTS.$COLUMN_IMAGE,
               $TABLE_CART.$quantity
        FROM $TABLE_PRODUCTS
        INNER JOIN $TABLE_CART ON $TABLE_PRODUCTS.$COLUMN_ID = $TABLE_CART.$product_id
        WHERE $TABLE_CART.$customer_id = ?
    """
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, arrayOf(customerId))

        if (cursor.moveToFirst()) {
            do {
                val product = Model_product(
                    masp = cursor.getString(cursor.getColumnIndex(COLUMN_MASP)),
                    tensp = cursor.getString(cursor.getColumnIndex(COLUMN_TENSP)),
                    gia = cursor.getDouble(cursor.getColumnIndex(COLUMN_GIA)),
                    maloai = cursor.getString(cursor.getColumnIndex(COLUMN_MALOAI)),
                    donvi = cursor.getString(cursor.getColumnIndex(COLUMN_DONVI)),
                    img = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)),
                    productid =  cursor.getString(cursor.getColumnIndex(COLUMN_MASP))
                )
                val quantity = cursor.getInt(cursor.getColumnIndex(quantity))
                cartProducts.add(product to quantity)
            } while (cursor.moveToNext())
        }
        cursor.close()

        return cartProducts
    }

    @SuppressLint("Range")
    fun getWishList(customerId: String): ArrayList<Model_product> {
        val Wishlist: ArrayList<Model_product> = ArrayList()
        val query = """
        SELECT $TABLE_PRODUCTS.$COLUMN_MASP, $TABLE_PRODUCTS.$COLUMN_TENSP, 
               $TABLE_PRODUCTS.$COLUMN_GIA, $TABLE_PRODUCTS.$COLUMN_MALOAI, 
               $TABLE_PRODUCTS.$COLUMN_DONVI, $TABLE_PRODUCTS.$COLUMN_IMAGE
        FROM $TABLE_PRODUCTS
        INNER JOIN $TABLE_WISHLIST ON $TABLE_PRODUCTS.$COLUMN_ID = $TABLE_WISHLIST.$WL_produtid
        WHERE $TABLE_WISHLIST.$WL_customerid= ?
    """
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, arrayOf(customerId))

        if (cursor.moveToFirst()) {
            do {
                val product = Model_product(
                    masp = cursor.getString(cursor.getColumnIndex(COLUMN_MASP)),
                    tensp = cursor.getString(cursor.getColumnIndex(COLUMN_TENSP)),
                    gia = cursor.getDouble(cursor.getColumnIndex(COLUMN_GIA)),
                    maloai = cursor.getString(cursor.getColumnIndex(COLUMN_MALOAI)),
                    donvi = cursor.getString(cursor.getColumnIndex(COLUMN_DONVI)),
                    img = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)),
                    productid =  cursor.getString(cursor.getColumnIndex(COLUMN_MASP))
                )
                Wishlist.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()

        return Wishlist
    }

//    ===========================================Dashboard===================================

    fun tongchitheonam(nam: Int): Double{
        val db = this.readableDatabase
        var totalyear = 0.0

        val query = " SELECT SUM($PN_THANH_TIEN) FROM $TABLE_PHIEU_NHAP WHERE strftime('%Y', $PN_NGAY_NHAP) = ? "

        val cursor: Cursor = db.rawQuery(query, arrayOf(nam.toString()))
        if(cursor.moveToFirst()){
            totalyear =   cursor.getDouble(0)
        }
        return totalyear

    }

    fun tongthutheonam(nam: Int): Double{
        val db = this.readableDatabase
        var totalyear = 0.0

        val query = " SELECT SUM($order_totalall) FROM $TABLE_ORDERS WHERE strftime('%Y', $order_date) = ? "

        val cursor: Cursor = db.rawQuery(query, arrayOf(nam.toString()))
        if(cursor.moveToFirst()){
            totalyear =   cursor.getDouble(0)
        }
        return totalyear

    }

    fun tongchitheonamthucte(nam: Int): Double{
        val db = this.readableDatabase
        var totalyear = 0.0
        val query = """
    SELECT SUM($PN_THANH_TIEN) AS total_all 
    FROM $TABLE_PHIEU_NHAP 
    INNER JOIN $TABLE_PHIEUNHAP_STATUS 
    ON $TABLE_PHIEU_NHAP.$PN_IDSTATUS = $TABLE_PHIEUNHAP_STATUS.$PS_id 
    WHERE $PS_status = 'Đã giao' AND strftime('%Y', $PN_NGAY_NHAP) = ?  
""".trimIndent()

        val cursor: Cursor = db.rawQuery(query, arrayOf(nam.toString()))
        if(cursor.moveToFirst()){
            totalyear =   cursor.getDouble(0)
        }
        return totalyear

    }

    fun tongthutheonamthucte(nam: Int): Double{
        val db = this.readableDatabase
        var totalyear = 0.0
        val query = """
    SELECT SUM($order_totalall) AS total_all 
    FROM $TABLE_ORDERS 
    INNER JOIN $TABLE_ORDER_STATUS 
    ON $TABLE_ORDERS.$order_idstatus = $TABLE_ORDER_STATUS.$PS_id 
    WHERE $OS_status = 'Đã giao' AND strftime('%Y', $order_date) = ? 
""".trimIndent()

        val cursor: Cursor = db.rawQuery(query, arrayOf(nam.toString()))
        if(cursor.moveToFirst()){
            totalyear =   cursor.getDouble(0)
        }
        return totalyear

    }

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    fun tongchitheoQuyNam(quy: Int, nam: Int): Double {
        val db = this.readableDatabase
        var totalAmount = 0.0

        // Xác định khoảng thời gian của từng quý
        val startMonth: String
        val endMonth: String

        when (quy) {
            1 -> {
                startMonth = "01"
                endMonth = "03"
            }
            2 -> {
                startMonth = "04"
                endMonth = "06"
            }
            3 -> {
                startMonth = "07"
                endMonth = "09"
            }
            4 -> {
                startMonth = "10"
                endMonth = "12"
            }
            else -> {
                throw IllegalArgumentException("Quý không hợp lệ")
            }
        }

        val query = """
        SELECT SUM($PN_THANH_TIEN)
        FROM $TABLE_PHIEU_NHAP
        WHERE strftime('%Y', $PN_NGAY_NHAP) = ? 
        AND strftime('%m', $PN_NGAY_NHAP) BETWEEN ? AND ?
    """
        val cursor: Cursor = db.rawQuery(query, arrayOf(nam.toString(), startMonth, endMonth))

        if (cursor.moveToFirst()) {
            totalAmount = cursor.getDouble(0)
        }

        cursor.close()
        return totalAmount
    }

    fun tongthutheoQuyNam(quy: Int, nam: Int): Double {
        val db = this.readableDatabase
        var totalAmount = 0.0

        // Xác định khoảng thời gian của từng quý
        val startMonth: String
        val endMonth: String

        when (quy) {
            1 -> {
                startMonth = "01"
                endMonth = "03"
            }
            2 -> {
                startMonth = "04"
                endMonth = "06"
            }
            3 -> {
                startMonth = "07"
                endMonth = "09"
            }
            4 -> {
                startMonth = "10"
                endMonth = "12"
            }
            else -> {
                throw IllegalArgumentException("Quý không hợp lệ")
            }
        }

        val query = """
        SELECT SUM($order_totalall)
        FROM $TABLE_ORDERS
        WHERE strftime('%Y', $order_date) = ? 
        AND strftime('%m', $order_date) BETWEEN ? AND ?
    """
        val cursor: Cursor = db.rawQuery(query, arrayOf(nam.toString(), startMonth, endMonth))

        if (cursor.moveToFirst()) {
            totalAmount = cursor.getDouble(0)
        }

        cursor.close()
        return totalAmount
    }

    fun tongchitheoQuyNamthucte(quy: Int, nam: Int): Double {
        val db = this.readableDatabase
        var totalAmount = 0.0

        // Xác định khoảng thời gian của từng quý
        val startMonth: String
        val endMonth: String

        when (quy) {
            1 -> {
                startMonth = "01"
                endMonth = "03"
            }
            2 -> {
                startMonth = "04"
                endMonth = "06"
            }
            3 -> {
                startMonth = "07"
                endMonth = "09"
            }
            4 -> {
                startMonth = "10"
                endMonth = "12"
            }
            else -> {
                throw IllegalArgumentException("Quý không hợp lệ")
            }
        }
        val query = """
    SELECT SUM($PN_THANH_TIEN) AS total_all 
    FROM $TABLE_PHIEU_NHAP 
    INNER JOIN $TABLE_PHIEUNHAP_STATUS 
    ON $TABLE_PHIEU_NHAP.$PN_IDSTATUS = $TABLE_PHIEUNHAP_STATUS.$PS_id 
    WHERE $PS_status = 'Đã giao' AND strftime('%Y', $PN_NGAY_NHAP) = ? 
        AND strftime('%m', $PN_NGAY_NHAP) BETWEEN ? AND ? 
""".trimIndent()
        val cursor: Cursor = db.rawQuery(query, arrayOf(nam.toString(), startMonth, endMonth))

        if (cursor.moveToFirst()) {
            totalAmount = cursor.getDouble(0)
        }

        cursor.close()
        return totalAmount
    }

    fun tongthutheoQuyNamthucte(quy: Int, nam: Int): Double {
        val db = this.readableDatabase
        var totalAmount = 0.0

        // Xác định khoảng thời gian của từng quý
        val startMonth: String
        val endMonth: String

        when (quy) {
            1 -> {
                startMonth = "01"
                endMonth = "03"
            }
            2 -> {
                startMonth = "04"
                endMonth = "06"
            }
            3 -> {
                startMonth = "07"
                endMonth = "09"
            }
            4 -> {
                startMonth = "10"
                endMonth = "12"
            }
            else -> {
                throw IllegalArgumentException("Quý không hợp lệ")
            }
        }
        val query = """
    SELECT SUM($order_totalall) AS total_all 
    FROM $TABLE_ORDERS 
    INNER JOIN $TABLE_ORDER_STATUS 
    ON $TABLE_ORDERS.$order_idstatus = $TABLE_ORDER_STATUS.$PS_id 
    WHERE $OS_status = 'Đã giao' AND strftime('%Y', $order_date) = ? 
        AND strftime('%m', $order_date) BETWEEN ? AND ?
""".trimIndent()

        val cursor: Cursor = db.rawQuery(query, arrayOf(nam.toString(), startMonth, endMonth))

        if (cursor.moveToFirst()) {
            totalAmount = cursor.getDouble(0)
        }

        cursor.close()
        return totalAmount
    }

    fun tongchitheoThangNam(month: Int, year: Int): Double {
        val db = this.readableDatabase
        var totalAmount = 0.0

        val query = "SELECT SUM($PN_THANH_TIEN) FROM $TABLE_PHIEU_NHAP WHERE strftime('%Y', $PN_NGAY_NHAP) = ? AND strftime('%m', $PN_NGAY_NHAP) = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(year.toString(), String.format("%02d", month)))

        if (cursor.moveToFirst()) {
            totalAmount = cursor.getDouble(0)
        }

        cursor.close()
        return totalAmount
    }

    fun tongdoanhthutheoThangNam(month: Int, year: Int): Double {
        val db = this.readableDatabase
        var totalAmount = 0.0

        val query = "SELECT SUM($order_totalall) FROM $TABLE_ORDERS WHERE strftime('%Y', $order_date) = ? AND strftime('%m', $order_date) = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(year.toString(), String.format("%02d", month)))

        if (cursor.moveToFirst()) {
            totalAmount = cursor.getDouble(0)
        }

        cursor.close()
        return totalAmount
    }

    fun tongchitheoThangNamthucte(month: Int, year: Int): Double {
        val db = this.readableDatabase
        var totalAmount = 0.0
        val query = """
    SELECT SUM($PN_THANH_TIEN) AS total_all 
    FROM $TABLE_PHIEU_NHAP 
    INNER JOIN $TABLE_PHIEUNHAP_STATUS 
    ON $TABLE_PHIEU_NHAP.$PN_IDSTATUS = $TABLE_PHIEUNHAP_STATUS.$PS_id 
    WHERE $PS_status = 'Đã giao' AND strftime('%Y', $PN_NGAY_NHAP) = ? AND strftime('%m', $PN_NGAY_NHAP) = ? 
""".trimIndent()
        val cursor: Cursor = db.rawQuery(query, arrayOf(year.toString(), String.format("%02d", month)))

        if (cursor.moveToFirst()) {
            totalAmount = cursor.getDouble(0)
        }

        cursor.close()
        return totalAmount
    }

    fun tongdoanhthutheoThangNamthucte(month: Int, year: Int): Double {
        val db = this.readableDatabase
        var totalAmount = 0.0
        val query = """
    SELECT SUM($order_totalall) AS total_all 
    FROM $TABLE_ORDERS 
    INNER JOIN $TABLE_ORDER_STATUS 
    ON $TABLE_ORDERS.$order_idstatus = $TABLE_ORDER_STATUS.$PS_id 
    WHERE $OS_status = 'Đã giao' AND strftime('%Y', $order_date) = ? AND strftime('%m', $order_date) = ? 
""".trimIndent()

        val cursor: Cursor = db.rawQuery(query, arrayOf(year.toString(), String.format("%02d", month)))

        if (cursor.moveToFirst()) {
            totalAmount = cursor.getDouble(0)
        }

        cursor.close()
        return totalAmount
    }
    fun tongchitheongay(startDate: String, endDate: String): Double {
        val db = this.readableDatabase
        var totalall = 0.0

        val query = "SELECT SUM($PN_THANH_TIEN)  FROM $TABLE_PHIEU_NHAP WHERE $PN_NGAY_NHAP BETWEEN ? AND ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(startDate, endDate))

        if (cursor.moveToFirst()) {
            val totalAmount = cursor.getDouble(0)

            totalall += totalAmount
        }

        cursor.close()


        return totalall
    }

    fun tongchithuctetheongay(startDate: String, endDate: String): Double {
        val db = this.readableDatabase
        var totalall = 0.0

        val query = """
    SELECT SUM($PN_THANH_TIEN) AS total_all 
    FROM $TABLE_PHIEU_NHAP 
    INNER JOIN $TABLE_PHIEUNHAP_STATUS 
    ON $TABLE_PHIEU_NHAP.$PN_IDSTATUS = $TABLE_PHIEUNHAP_STATUS.$PS_id 
    WHERE $PS_status = 'Đã giao' AND $PN_NGAY_NHAP BETWEEN ? AND ? 
""".trimIndent()
        val cursor: Cursor = db.rawQuery(query, arrayOf(startDate, endDate))

        if (cursor.moveToFirst()) {
            val totalAmount = cursor.getDouble(0)

            totalall += totalAmount
        }

        cursor.close()


        return totalall
    }

    fun tongthuthuctetheongay(startDate: String, endDate: String): Double {
        val db = this.readableDatabase
        var totalall = 0.0

        val query = """
    SELECT SUM($order_totalall) AS total_all 
    FROM $TABLE_ORDERS 
    INNER JOIN $TABLE_ORDER_STATUS 
    ON $TABLE_ORDERS.$order_idstatus = $TABLE_ORDER_STATUS.$PS_id 
    WHERE $OS_status = 'Đã giao' AND $order_date BETWEEN ? AND ? 
""".trimIndent()
        val cursor: Cursor = db.rawQuery(query, arrayOf(startDate, endDate))

        if (cursor.moveToFirst()) {
            val totalAmount = cursor.getDouble(0)

            totalall += totalAmount
        }

        cursor.close()


        return totalall
    }
    fun tongdoanhthutheongay(startDate: String, endDate: String): Double {
        val db = this.readableDatabase
        var totalall = 0.0

        val query = "SELECT SUM($order_totalall)  FROM $TABLE_ORDERS WHERE $order_date BETWEEN ? AND ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(startDate, endDate))

        if (cursor.moveToFirst()) {
            val totalAmount = cursor.getDouble(0)

            totalall += totalAmount
        }

        cursor.close()


        return totalall
    }
    fun tongtonkho():Int{
        val query = "SELECT SUM($inventory_quantity) AS total_all FROM $TABLE_INVENTORY "
        val db = this.writableDatabase
        val  cursor = db.rawQuery(query, null)

        var total = 0;
        if(cursor.moveToFirst()){
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total_all"))
        }
        cursor.close()
        return total

    }
    fun tongsanpham():Int{
        val query = "SELECT COUNT($COLUMN_MASP) AS total_all FROM $TABLE_PRODUCTS "
        val db = this.writableDatabase
        val  cursor = db.rawQuery(query, null)

        var total = 0;
        if(cursor.moveToFirst()){
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total_all"))
        }
        cursor.close()
        return total

    }

    fun tongkhachhang():Int{
        val query = "SELECT COUNT($KH_ID) AS total_all FROM $TABLE_KH "
        val db = this.writableDatabase
        val  cursor = db.rawQuery(query, null)

        var total = 0;
        if(cursor.moveToFirst()){
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total_all"))
        }
        cursor.close()
        return total

    }
    fun tongdoanhthu():Double{
        val query = "SELECT SUM($order_totalall) AS total_all FROM $TABLE_ORDERS "
        val db = this.writableDatabase
        val  cursor = db.rawQuery(query, null)

        var totalall = 0.0;
        if(cursor.moveToFirst()){
            val total = cursor.getDouble(cursor.getColumnIndexOrThrow("total_all"))
            totalall+=total;
        }
        cursor.close()
        return totalall

    }

    fun tongchi():Double{
        val query = "SELECT SUM($PN_THANH_TIEN) AS total_all FROM $TABLE_PHIEU_NHAP "
        val db = this.writableDatabase
        val  cursor = db.rawQuery(query, null)

        var totalall = 0.0;
        if(cursor.moveToFirst()){
            val total = cursor.getDouble(cursor.getColumnIndexOrThrow("total_all"))
            totalall+=total;
        }
        cursor.close()
        return totalall

    }


    fun tongchithucte(): Double{
        val query = """
    SELECT SUM($PN_THANH_TIEN) AS total_all 
    FROM $TABLE_PHIEU_NHAP 
    INNER JOIN $TABLE_PHIEUNHAP_STATUS 
    ON $TABLE_PHIEU_NHAP.$PN_IDSTATUS = $TABLE_PHIEUNHAP_STATUS.$PS_id 
    WHERE $PS_status = 'Đã giao'
""".trimIndent()
        val db = writableDatabase
        val cursor = db.rawQuery(query, null)
        var totalAll = 0.0
        if(cursor.moveToFirst()){
            val total = cursor.getDouble(cursor.getColumnIndexOrThrow("total_all"))
            totalAll += total
        }
    return totalAll
    }
    fun tongthuthucte(): Double{
        val query = """
    SELECT SUM($order_totalall) AS total_all 
    FROM $TABLE_ORDERS 
    INNER JOIN $TABLE_ORDER_STATUS 
    ON $TABLE_ORDERS.$order_idstatus = $TABLE_ORDER_STATUS.$OS_id 
    WHERE $OS_status = 'Đã giao'
""".trimIndent()
        val db = writableDatabase
        val cursor = db.rawQuery(query, null)
        var totalAll = 0.0
        if(cursor.moveToFirst()){
            val total = cursor.getDouble(cursor.getColumnIndexOrThrow("total_all"))
            totalAll += total
        }
        return totalAll
    }
    // Thiết kế các Spinner
    @SuppressLint("Range")
    fun getallproducttypename(): List<String>{
        val producttype = mutableListOf<String>()
        val db = readableDatabase
        val query = "SELECT $tenlsp FROM $TABLE_LOAISANPHAM"
        val cursor = db.rawQuery(query, null)
        if(cursor.moveToFirst()){
            do {
                val tenlsp = cursor.getString(cursor.getColumnIndex(tenlsp))
                producttype.add(tenlsp)
            }while (cursor.moveToNext())
        }
        return producttype
    }



}



