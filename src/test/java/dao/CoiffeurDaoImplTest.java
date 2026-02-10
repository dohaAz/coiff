package dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Coiffeur;

class CoiffeurDaoImplTest {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private CoiffeurDaoImpl dao;

    @BeforeEach
    void setup() throws Exception {
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);

        dao = new CoiffeurDaoImpl(conn);
    }

    @Test
    void testGetCoiffeurById() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        when(rs.getInt("id_coiffeur")).thenReturn(1);
        when(rs.getString("nom")).thenReturn("Ali");

        Coiffeur c = dao.getCoiffeurById(1);

        assertNotNull(c);
        assertEquals(1, c.getIdCoiffeur());
        assertEquals("Ali", c.getNom());
    }

    @Test
    void testAddCoiffeur() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        Coiffeur c = new Coiffeur();
        c.setNom("Sara");
        c.setAdresse("Casa");
        c.setEmail("s@mail.com");
        c.setTelephone("0600");
        c.setMotDePasse("pwd");

        assertTrue(dao.add(c));
    }

    @Test
    void testDeleteCoiffeur() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        assertTrue(dao.delete(1));
    }

    @Test
    void testCountCoiffeurs() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(5);

        assertEquals(5, dao.countCoiffeurs());
    }
}
