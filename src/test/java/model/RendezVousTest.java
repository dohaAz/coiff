package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;

class RendezVousTest {

    @Test
    void testConstructorAndGetters() {
        Date date = Date.valueOf("2024-01-01");
        Time time = Time.valueOf("10:00:00");

        RendezVous r = new RendezVous(
                1, 2, 3,
                date, time,
                "confirme",
                "Ali", "Ben",
                5, "Coupe",
                50.0
        );

        assertEquals(1, r.getIdRdv());
        assertEquals(2, r.getIdClient());
        assertEquals(3, r.getIdCoiffeur());
        assertEquals(date, r.getDateRdv());
        assertEquals(time, r.getHeureRdv());
        assertEquals("confirme", r.getStatut());
        assertEquals("Ali", r.getNomClient());
        assertEquals("Ben", r.getPrenomClient());
        assertEquals(5, r.getIdService());
        assertEquals("Coupe", r.getNomService());
        assertEquals(50.0, r.getPrix());
    }

    @Test
    void testSetters() {
        RendezVous r = new RendezVous();

        r.setIdRdv(10);
        r.setStatut("annule");
        r.setPrix(100);

        assertEquals(10, r.getIdRdv());
        assertEquals("annule", r.getStatut());
        assertEquals(100, r.getPrix());
    }
}
