package madcamp.week4.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;
    private String name;

    public UserResponseDto(Long userId, String name){
        this.userId = userId;
        this.name = name;
    }
}