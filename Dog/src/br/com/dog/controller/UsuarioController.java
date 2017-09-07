package br.com.dog.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.dog.controller.util.NavegacaoUtil;
import br.com.dog.model.dao.UsuarioDAO;
import br.com.dog.model.dao.impl.jdbc.UsuarioDao;
import br.com.dog.model.entity.Usuario;

/**
 * Servlet implementation class UsuarioController
 */
@WebServlet("/UsuarioController.do")
public class UsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public void init(ServletConfig config) throws ServletException {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String forward = "";
		String action = request.getParameter("action");

	}

	/**
	 * @throws IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		Usuario user = null;
		UsuarioDAO userDAO = new UsuarioDao();
		if (action.equalsIgnoreCase("inserir")) {

			Boolean exist = null;
			try {
				exist = userDAO.buscarLogin(request.getParameter("email"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!exist) {
				user = new Usuario();
				user.setNome(request.getParameter("nome"));
				user.setEmail(request.getParameter("email"));
				user.setSexo(request.getParameter("optradio").toUpperCase());
				user.setSenha(request.getParameter("senha"));
				user.setBairro(request.getParameter("bairro"));
				user.setLogradouro(request.getParameter("logradouro"));
				user.setNumero(Integer.parseInt(request.getParameter("numero")));
				user.setCep(Integer.parseInt(request.getParameter("cep")));
				String stdata = null;
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Calendar caldata = new GregorianCalendar();
					caldata.setTime(sdf.parse(request.getParameter("dataNascimento")));
					user.setdataNascimento(caldata);
				} catch (ParseException e) {
					e.printStackTrace();

				}
				try {
					userDAO.create(user);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RequestDispatcher view = request.getRequestDispatcher(NavegacaoUtil.LOGIN);
				view.forward(request, response);
			} else {
				RequestDispatcher view = request.getRequestDispatcher(NavegacaoUtil.LOGIN);

				view.forward(request, response);

			}

		}
	}

}
