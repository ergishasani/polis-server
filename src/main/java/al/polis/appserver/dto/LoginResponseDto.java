package al.polis.appserver.dto;

public class LoginResponseDto {
    private String status;
    private String role;

    // ✅ Required no-args constructor
    public LoginResponseDto() {}

    // ✅ Required full constructor
    public LoginResponseDto(String status, String role) {
        this.status = status;
        this.role = role;
    }

    // ✅ Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
