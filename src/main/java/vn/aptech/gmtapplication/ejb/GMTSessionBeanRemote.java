package vn.aptech.gmtapplication.ejb;

import jakarta.ejb.Remote;

/**
 * Remote interface dùng cho GMTSessionBean.
 * Đảm bảo khai báo mọi phương thức mà servlet & client gọi tới.
 */
@Remote
public interface GMTSessionBeanRemote {

    /** Chuyển đổi múi giờ – trả về chuỗi định dạng: "China: 22:30 - Vietnam 21:30" … */
    String convertGMT(String fromCountry, String toCountry);

    /** Lưu “giờ hiện tại” (được nhập từ form) vào bean stateful */
    void setCurrentTime(String currentTime);

    /** Trả về “giờ hiện tại” đang lưu trong bean */
    String getCurrentTime();

    /** Lấy offset GMT của 1 quốc gia (China → 8, Vietnam → 7 …) */
    int getGMTOffset(String country);

    /** Danh sách quốc gia cho combobox trong JSP */
    String[] getAvailableCountries();
}
