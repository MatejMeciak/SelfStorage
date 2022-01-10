package com.appslab.selfstorage.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.appslab.selfstorage.dto.LocalUser;
import com.appslab.selfstorage.dto.SocialProvider;
import com.appslab.selfstorage.dto.UserInfo;
import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.models.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;



public class GeneralUtils {
    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public static SocialProvider toSocialProvider(String providerId) {
        for (SocialProvider socialProvider : SocialProvider.values()) {
            if (socialProvider.getProviderType().equals(providerId)) {
                return socialProvider;
            }
        }
        return SocialProvider.LOCAL;
    }

    public static UserInfo buildUserInfo(LocalUser localUser) {
        List<String> roles = localUser.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        CustomUser user = localUser.getUser();
        return new UserInfo(user.getId().toString(), user.getUsername(), user.getEmail(), roles);
    }
}
