package pharma.lms.PharmaLMS.user.domain;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static pharma.lms.PharmaLMS.user.domain.UserPermissions.*;

public enum UserRole {
    USER(Sets.newHashSet(COURSE_READ, PRESENTATION_READ)),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_ADD, PRESENTATION_ADD, PRESENTATION_READ));

    private final Set<UserPermissions> permissions;

    UserRole(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermissions> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
