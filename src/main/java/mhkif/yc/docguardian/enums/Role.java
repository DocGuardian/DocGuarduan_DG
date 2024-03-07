package mhkif.yc.docguardian.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public enum Role {
    ROLE_SYSADMIN(Collections.emptySet()),
    ROLE_ADMIN(Set.of(
            Permission.ADMIN_READ,
            Permission.ADMIN_CREATE,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_DELETE,
            Permission.USER_READ,
            Permission.USER_CREATE,
            Permission.USER_UPDATE,
            Permission.USER_DELETE

    )),
    ROLE_MANAGER(Collections.emptySet()),
    ROLE_USER(Set.of(
            Permission.USER_READ,
            Permission.USER_CREATE,
            Permission.USER_UPDATE,
            Permission.USER_DELETE
    ));
    @Getter
    private final Set<Permission> permissions;
}
