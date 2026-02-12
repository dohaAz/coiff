package servlet;

import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdminFideliteServletTest {

    private AdminFideliteServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setup() {
        servlet = new AdminFideliteServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    void redirectionLoginSiUtilisateurNull() throws Exception {
        when(session.getAttribute("user")).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    void redirectionLoginSiUtilisateurNonAdmin() throws Exception {
        User user = new User();
        user.setRole("coiffeur");

        when(session.getAttribute("user")).thenReturn(user);

        servlet.doGet(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    void forwardVersJspSiUtilisateurAdmin() throws Exception {
        User user = new User();
        user.setRole("admin");

        when(session.getAttribute("user")).thenReturn(user);
        when(request.getRequestDispatcher("/admin/fidelite.jsp"))
                .thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("listeFidelite"), any());
        verify(dispatcher).forward(request, response);
    }
}
