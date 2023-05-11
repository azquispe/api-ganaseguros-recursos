package com.ganaseguros.recursos.usuarios.dao;

import com.ganaseguros.recursos.usuarios.dto.PersonaDto;
import com.ganaseguros.recursos.usuarios.entity.PersonaEntity;
import com.ganaseguros.recursos.usuarios.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPersonaDao extends JpaRepository<PersonaEntity,Long> {
    /*@Query("select a from PersonaEntity a where a.estadoId = 1000 and a.personaId = :pPersonaID ")
    public Optional<PersonaEntity> findByPersonaId(Long pPersonaID);*/

    /*Long personaId, Long generoId, String nombres, String apellidoPaterno, String apellidoMaterno, String apellidoEsposo,
    String numeroDocumento, String complemento, Long ciudadExpedidoId, String numeroCelular, String correoElectronico, Date fechaNacimiento, Date fechaRegistro, Date fechaModificacion,
    Long usuarioId, String ciudadExpedido, String genero*/
    @Query("SELECT new com.ganaseguros.recursos.usuarios.dto.PersonaDto (p.personaId, p.generoId, p.nombres, p.apellidoPaterno, p.apellidoMaterno, p.apellidoEsposo, "
            + " p.numeroDocumento,p.complemento, p.ciudadExpedidoId, p.numeroCelular, p.correoElectronico, p.fechaNacimiento, p.fechaRegistro, p.fechaModificacion, "
            + " u.usuarioId,exp.descripcion,genero.descripcion) "
            + " FROM UsuarioEntity u "
            + " INNER JOIN PersonaEntity p ON u.personaId = p.personaId "
            + " LEFT JOIN DominioEntity exp ON exp.dominioId = p.ciudadExpedidoId "
            + " LEFT JOIN DominioEntity genero ON genero.dominioId = p.generoId "
            + " WHERE p.estadoId=1000 AND u.usuarioId=:pUsuarioId AND u.estadoId = 1000 ")
    public List<PersonaDto> findPersonaByUduarioId(@Param("pUsuarioId") Long pUsuarioId);

}
