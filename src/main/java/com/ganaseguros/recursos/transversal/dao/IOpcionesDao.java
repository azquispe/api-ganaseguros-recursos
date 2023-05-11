package com.ganaseguros.recursos.transversal.dao;

import com.ganaseguros.recursos.transversal.entity.OpcionesEntity;
import com.ganaseguros.recursos.usuarios.dto.PersonaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IOpcionesDao extends JpaRepository<OpcionesEntity,Long> {
    @Query("SELECT o  "
            + " FROM UsuarioEntity u "
            + " LEFT JOIN UsuarioRolesEntity ur ON ur.usuarioId = u.usuarioId and ur.estadoId = 1000 "
            + " LEFT JOIN RolesOpcionesEntity ro ON ro.rolId = ur.rolId and ro.estadoId = 1000 "
            + " LEFT JOIN OpcionesEntity o ON o.opcionId = ro.opcionId and o.estadoId = 1000"
            + " WHERE u.estadoId=1000 AND u.usuarioId=:pUsuarioId  ")
    public List<OpcionesEntity> findOpcionesByUsuarioId(@Param("pUsuarioId") Long pUsuarioId);

    @Query("SELECT o FROM OpcionesEntity o WHERE o.opcionId = :pOpcionId AND o.estadoId = 1000 ")
    public Optional<OpcionesEntity> findOpcionesByOpcionId(@Param("pOpcionId") Long pOpcionId);

    @Query("SELECT o FROM OpcionesEntity o WHERE o.opcionOrigenId = :pOpcionOrigenId AND o.estadoId = 1000 ")
    public List<OpcionesEntity> findOpcionesByOpcionOrigenId(@Param("pOpcionOrigenId") Long pOpcionOrigenId);

    @Query("SELECT o FROM OpcionesEntity o WHERE o.opcionId IN :pOpcionIdLst  AND  o.estadoId = 1000 ")
    public List<OpcionesEntity> findOpcionesByOpcionIdList(@Param("pOpcionIdLst") List<Long> pOpcionIdLst);


}
