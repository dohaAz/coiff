package dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Service;

class ServiceDaoImpTest {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private ServiceDaoImp dao;

    @BeforeEach
    void setup() {
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);
        dao = new ServiceDaoImp(conn);
    }

    @Test
    void testGetAllServices() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        // 2 services dans le ResultSet
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("id_service")).thenReturn(1, 2);
        when(rs.getString("nom_service")).thenReturn("Coupe", "Coloration");
        when(rs.getString("description")).thenReturn("Desc1", "Desc2");
        when(rs.getInt("duree")).thenReturn(30, 45);
        when(rs.getDouble("prix")).thenReturn(50.0, 100.0);
        when(rs.getString("photo")).thenReturn("img1.jpg", "img2.jpg");

        List<Service> services = dao.getAllServices();

        assertEquals(2, services.size());
        assertEquals("Coupe", services.get(0).getNom());
        assertEquals("Coloration", services.get(1).getNom());
    }

    @Test
    void testGetAllServicesEmpty() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        List<Service> services = dao.getAllServices();
        assertTrue(services.isEmpty());
    }

    @Test
    void testGetServiceByIdFound() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id_service")).thenReturn(1);
        when(rs.getString("nom_service")).thenReturn("Coupe");
        when(rs.getString("description")).thenReturn("Desc");
        when(rs.getInt("duree")).thenReturn(30);
        when(rs.getDouble("prix")).thenReturn(50.0);
        when(rs.getString("photo")).thenReturn("img.jpg");

        Service s = dao.getServiceById(1);
        assertNotNull(s);
        assertEquals("Coupe", s.getNom());
    }

    @Test
    void testGetServiceByIdNotFound() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        Service s = dao.getServiceById(99);
        assertNull(s);
    }

    @Test
    void testAddServiceSuccess() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        Service s = new Service();
        s.setNom("Coloration");
        s.setDescription("Desc");
        s.setDuree(45);
        s.setPrix(100);
        s.setPhoto("img2.jpg");

        boolean added = dao.addService(s);
        assertTrue(added);
    }

    @Test
    void testAddServiceFails() throws Exception {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        Service s = new Service();
        boolean added = dao.addService(s);
        assertFalse(added);
    }
}
