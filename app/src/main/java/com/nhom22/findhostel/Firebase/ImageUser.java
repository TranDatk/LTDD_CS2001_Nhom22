package com.nhom22.findhostel.Firebase;

public class ImageUser {
    private int userId;
    private String base64Image;

    public ImageUser() {
        // Cần một constructor mặc định để Firebase có thể đọc và chuyển đổi dữ liệu
    }

    public ImageUser(int userId, String base64Image) {
        this.userId = userId;
        this.base64Image = base64Image;
    }

    public int getUserId() {
        return userId;
    }

    public String getBase64Image() {
        return base64Image;
    }
}
