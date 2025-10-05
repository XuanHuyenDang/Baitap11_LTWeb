package vn.iotstar.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	private String token;
	private long expiresIn;

	public String getToken() {
		return token;
	}
}
