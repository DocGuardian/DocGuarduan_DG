package mhkif.yc.docguardian.repositories;

import mhkif.yc.docguardian.entities.Role;
import mhkif.yc.docguardian.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByName(String name);
}
