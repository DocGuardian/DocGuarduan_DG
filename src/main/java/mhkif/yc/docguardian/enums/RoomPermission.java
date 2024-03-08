package mhkif.yc.docguardian.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoomPermission {

    // ROOM ADMIN Permissions
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    // MANAGER Permissions
    MANAGER_READ("manager:read"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_CREATE("manager:create"),
    MANAGER_DELETE("manager:delete"),

    // USER Permissions
    EDITOR_READ("editor:read"),
    EDITOR_UPDATE("editor:update"),
    EDITOR_CREATE("editor:create"),
    EDITOR_DELETE("editor:delete"),

    // USER Permissions
    FILE_SHARER_READ("file_sharer:read"),
    FILE_SHARER_UPDATE("file_sharer:update"),
    FILE_SHARER_CREATE("file_sharer:create"),
    FILE_SHARER_DELETE("file_sharer:delete");

    @Getter
    private final String permission;
}
