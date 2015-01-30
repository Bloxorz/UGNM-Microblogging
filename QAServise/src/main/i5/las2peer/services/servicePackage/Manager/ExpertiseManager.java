package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.ExpertiseDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.Exceptions.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marv on 12.11.2014.
 */
public class ExpertiseManager extends  AbstractManager {

    /**
     * Returns a Collection of all Expertises stored in the Databse
     * @param conn the Connection
     * @return A Collection of Expertises
     * @throws SQLException unknown Server error, see msg for further detail
     */
    public List<ExpertiseDTO> getExpertiseList(Connection conn) throws SQLException, CantFindException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<ExpertiseDTO>> h = new BeanListHandler<ExpertiseDTO>(ExpertiseDTO.class);
        List<ExpertiseDTO> expertises = qr.query(conn, "SELECT idExpertise, text FROM Expertise ORDER BY idExpertise", h);
        if(expertises.size() == 0)
            throw new CantFindException("Can not find any expertises.");
        return expertises;
    }

    public long addExpertise(Connection conn, ExpertiseDTO expertise/*, List<HashtagDTO> hashtags*/) throws SQLException, NotWellFormedException, CantInsertException {
        //TODO clean up

        final String insertExpertiseSQL = "INSERT INTO Expertise (text) VALUES (?)";

        if(expertise.getText() == null) {
            throw new NotWellFormedException("Missing text!");

        }

        try(PreparedStatement pstmt = conn.prepareStatement(insertExpertiseSQL, Statement.RETURN_GENERATED_KEYS); ) {

            pstmt.setString(1, expertise.getText());
            pstmt.executeUpdate();
            
            long expertiseId = 0;
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                expertiseId = rs.getLong(1);
            } else {
                throw  new CantInsertException("Can't insert into Expertise");
            }
/*
            //add all Hashtags that do not exist yet
            List<Long> hashTagIds = new ArrayList<Long>();
            for(HashtagDTO hashtag : hashtags) {
                if(ManagerFacade.getInstance().existsHashtag(conn, hashtag.getText())) {
                    Long id = ManagerFacade.getInstance().addHashtag(conn, hashtag.getText());
                    hashTagIds.add(id);
                } else {
                    hashTagIds.add( new Long(hashtag.getId()));
                }
            }

            //add expertise - hashtag relations

            String sql = "INSERT INTO HashtagToExpertise (idHashtag, idExpertise) VALUES(?,?)";

            try(PreparedStatement qstmt = conn.prepareStatement(sql); ) {
               qstmt.setLong(2, expertiseId);
                for(Long hashtagId: hashTagIds) {
                    qstmt.setLong(1, hashtagId.longValue());
                    if(qstmt.executeUpdate() == 0)
                        throw  new CantInsertException("hashtag could not been added to Databse");
                }
            }*/

            return expertiseId;
        }
    }

    public ExpertiseDTO getExpertise(Connection conn, long expertiseId) throws SQLException, CantFindException {
        final String sql = "SELECT text as text FROM Expertise e WHERE e.idExpertise = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setLong(1, expertiseId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                ExpertiseDTO expertise = new ExpertiseDTO();
                expertise.setText(rs.getString("text"));
                expertise.setIdExpertise(expertiseId);
                return expertise;
            } else {
                throw new CantFindException();
            }
        }
    }

    public void editExpertise(Connection conn, ExpertiseDTO expertise) throws NotWellFormedException, SQLException, CantUpdateException {
        final String sql = "UPDATE Expertise e SET e.text = ? WHERE e.idExpertise = ?;";

        if(expertise.getText() == null) {
            throw new NotWellFormedException("Missing text!");
        }
        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setString(1, expertise.getText());
            pstmt.setLong(2,expertise.getIdExpertise());

            if(pstmt.executeUpdate() == 0) {
                throw new CantUpdateException("Could not update Data");
            }
        }
    }

    public void deleteExpertise(Connection conn, long expertiseId) throws SQLException, CantDeleteException, CantFindException {

        ExpertiseDTO expertise = new ExpertiseDTO();
        expertise.setIdExpertise(expertiseId);

        final String sqlRequest = "DELETE FROM Expertise WHERE idExpertise = ?";


        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlRequest);
            pstmt.setLong(1, expertiseId);
            int rowsAffected = pstmt.executeUpdate();
            if( rowsAffected == 0 ) {
                throw new CantFindException();
            };
        } catch(SQLException e) {
            throw new CantDeleteException(e.toString());
        }
    }



}
