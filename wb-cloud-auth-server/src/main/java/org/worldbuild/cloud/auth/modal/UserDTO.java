package org.worldbuild.cloud.auth.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base32;
import org.worldbuild.cloud.auth.JPA.entity.UserInfo;
import org.worldbuild.cloud.auth.utils.StringUtils;

@Data
@NoArgsConstructor
public class UserDTO {

    Integer id;
    String username;
    String password;
    String confirm;
    String name;
    String email;
    String phone;
    String address;
    String secretKey;
    boolean enabled;
    boolean googleAuth;

    public static UserDTO generateUserDTO(UserInfo user) {
        UserDTO userDTO= new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        user.setUsername(user.getUsername());
        userDTO.setAddress(user.getAddress());
        String encodedSecret = new Base32().encodeToString(user.getSecretKey().getBytes());
        userDTO.setSecretKey(encodedSecret);
        //
        userDTO.setEnabled(user.isEnabled());
        userDTO.setGoogleAuth(user.isGoogleAuth());
        return  userDTO;
    }
}
