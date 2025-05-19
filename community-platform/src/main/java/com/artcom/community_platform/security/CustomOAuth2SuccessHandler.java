package com.artcom.community_platform.security;

import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.entity.UserProfile;
import com.artcom.community_platform.repository.UserRepository;
import com.artcom.community_platform.repository.UserProfileRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public CustomOAuth2SuccessHandler(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = (String) attributes.get("email");
        String googleId = (String) attributes.get("sub");
        String name = (String) attributes.get("name");

        // Try to find existing user by email or Google ID
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.findByGoogleId(googleId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setGoogleId(googleId);
                            newUser.setUsername(name);
                            newUser.setCreatedAt(java.time.LocalDateTime.now());
                    return userRepository.save(newUser);
                        }));

        // Update Google ID if it's not set
        if (user.getGoogleId() == null) {
            user.setGoogleId(googleId);
            userRepository.save(user);
        }

        // Create profile if it doesn't exist
        UserProfile profile = user.getUserProfile();
        if (profile == null) {
            profile = new UserProfile();
            profile.setUser(user);
            profile.setUsername(name);
            profile.setRole(UserProfile.UserRole.COMMUNITY); // Default role
            profile = userProfileRepository.save(profile);
            user.setUserProfile(profile);
            userRepository.save(user);
        }

        // Set up authorities
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + profile.getRole()));
        
        // Create new authentication with proper authorities
        Authentication newAuth = new org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken(
            oauth2User,
            authorities,
            "google"
        );
        
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        
        // Redirect to profile page
        getRedirectStrategy().sendRedirect(request, response, "/profile");
    }
} 