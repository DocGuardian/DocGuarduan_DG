package mhkif.yc.docguardian.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoomPermission {

    // ROOM ADMIN Permissions
    ROOM_ADMIN_READ("room_admin:read"),
    ROOM_ADMIN_UPDATE("room_admin:update"),
    ROOM_ADMIN_CREATE("room_admin:create"),
    ROOM_ADMIN_DELETE("room_admin:delete"),

    // VIEWER Permissions
    ROOM_VIEWER_READ("viewer:read"),
    ROOM_VIEWER_UPDATE("viewer:update"),
    ROOM_VIEWER_CREATE("viewer:create"),
    ROOM_VIEWER_DELETE("viewer:delete"),

    // Editor Permissions
    ROOM_EDITOR_READ("editor:read"),
    ROOM_EDITOR_UPDATE("editor:update"),
    ROOM_EDITOR_CREATE("editor:create"),
    ROOM_EDITOR_DELETE("editor:delete"),

    // File Sharer Permissions
    ROOM_FILE_SHARER_READ("file_sharer:read"),
    ROOM_FILE_SHARER_UPDATE("file_sharer:update"),
    ROOM_FILE_SHARER_CREATE("file_sharer:create"),
    ROOM_FILE_SHARER_DELETE("file_sharer:delete");

    @Getter
    private final String permission;
}
