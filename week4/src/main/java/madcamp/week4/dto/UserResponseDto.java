package madcamp.week4.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;
    private String userName;
    private String name;

    public UserResponseDto(Long userId, String userName, String name){
        this.userId = userId;
        this.userName = userName;
        this.name = name;
    }
}