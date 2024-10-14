    package io.job.my_app.dto;


    import io.job.my_app.Entity.User;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserDto {

        private Integer userId;

        private String name;

        private String email;

        private String address;

        private String status;

        private String moNumber;

        private String password;

        private String confirmPassword;

        private String role;

        public UserDto(User user) {
            this.userId = user.getUserId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.address = user.getAddress();
            this.moNumber = user.getMoNumber();
            this.password = user.getPassword();
            this.confirmPassword=user.getConfirmPassword();
            this.status = user.getStatus();
            this.role = user.getRole();
        }

    }
