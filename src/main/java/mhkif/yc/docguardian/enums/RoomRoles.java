package mhkif.yc.docguardian.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public enum RoomRoles {
    ADMIN(Set.of()),

    EDITOR(Collections.emptySet()),
    FILE_SHARER(Set.of()),
    VIEWER(Set.of());
    @Getter
    private final Set<RoomPermission> permissions;
}