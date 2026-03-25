package com.example.Sistema_Requisicao.services;

import com.example.Sistema_Requisicao.dto.RequisicaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

@Service
public class RequisicaoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Transactional
    public void criarRequisicao(RequisicaoDTO dto) {
        jdbcTemplate.execute((Connection conn) -> {

            // 1. Cria CallableStatement genérico
            CallableStatement cs =
                    conn.prepareCall("{call pc_NovaRequisicao(?, ?, ?, ?)}");

            // 2. Faz unwrap para o driver correto do SQL Server
            SQLServerCallableStatement sqlCs =
                    cs.unwrap(SQLServerCallableStatement.class);

            // 3. Cria o TVP
            SQLServerDataTable dataTable = new SQLServerDataTable();
            dataTable.addColumnMetadata("materialId", Types.INTEGER);
            dataTable.addColumnMetadata("quantidade", Types.INTEGER);

            for (RequisicaoDTO.ItemDTO item : dto.getItens()) {
                dataTable.addRow(item.getMaterialId(), item.getQuantidade());
            }

            // 4. Parâmetros
            sqlCs.setInt(1, dto.getIdRequisitante());
            sqlCs.setStructured(2, "TipoListaMateriais", dataTable);
            sqlCs.setString(3, dto.getObservacao());

            if (dto.getIdDepartamento() != null) {
                sqlCs.setInt(4, dto.getIdDepartamento());
            } else {
                sqlCs.setNull(4, Types.INTEGER);
            }

            // 5. Executa
            sqlCs.execute();

            return null;
        });
    }
}