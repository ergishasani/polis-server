package al.polis.appserver.dto;

public class LoginRequestDto {
    private String username;
    private String password;

    // ✅ Manually add getters & setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
