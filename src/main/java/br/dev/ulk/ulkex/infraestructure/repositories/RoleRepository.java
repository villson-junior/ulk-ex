package br.dev.ulk.ulkex.infraestructure.repositories;

import br.dev.ulk.ulkex.core.domain.entities.Role;
import br.dev.ulk.ulkex.core.domain.enumerations.RoleEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleEnum name);

}