package dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Client;

class ClientDaoImpTest {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private ClientDaoImp dao;

    @BeforeEach
    void setup() {
        try {
            conn = mock(Connection.class);
            ps = mock(PreparedStatement.class);
            rs = mock(ResultSet.class);

            dao = new ClientDaoImp(conn);
        } catch (Exception e) {
            fail("Erreur setup: " + e.getMessage());
        }
    }

    @Test
    void testAddClient() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        Client c = new Client();
        c.setNom("Ali");
        c.setPrenom("Ben");
        c.setEmail("ali@mail.com");
        c.setTelephone("0600000000");
        c.setMotDePasse("pwd");

        assertTrue(dao.add(c));
    }

    @Test
    void testUpdateClient() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        Client c = new Client();
        c.setId(1);
        c.setNom("Sara");
        c.setPrenom("Ali");
        c.setEmail("sara@mail.com");
        c.setTelephone("0611111111");

        assertTrue(dao.update(c));
    }

    @Test
    void testDeleteClient() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        assertTrue(dao.delete(1));
    }

    @Test
    void testCountClients() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(5);

        assertEquals(5, dao.countClients());
    }

    @Test
    void testGetClientById() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getString("nom")).thenReturn("Ali");
        when(rs.getString("prenom")).thenReturn("Ben");
        when(rs.getString("email")).thenReturn("ali@mail.com");
        when(rs.getString("telephone")).thenReturn("0600000000");
        when(rs.getString("mot_de_passe")).thenReturn("pwd");

        Client client = dao.getClientById(1);
        assertNotNull(client);
        assertEquals("Ali", client.getNom());
        assertEquals("Ben", client.getPrenom());
    }

}
