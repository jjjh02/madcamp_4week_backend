package madcamp.week4.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRequestDto {
    private String userName;
    private String password;
    private String name;
}
