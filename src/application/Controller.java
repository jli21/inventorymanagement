package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import store.Computer;
import store.ComputerDAO;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
	
	private ComputerDAO dao;
	
	public void init( ) {
		final String url = getServletContext().getInitParameter("JDBC-URL");
		final String username = getServletContext().getInitParameter("JDBC-USERNAME");
		final String password = getServletContext().getInitParameter("JDBC-PASSWORD");
		
		dao = new ComputerDAO(url, username, password);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String action = request.getServletPath();
		
		try {
			switch (action) {
			case "/add": 
			case "/edit": showEditForm(request, response); break;
			case "/insert": insertComputer(request, response); break;
			case "/update": updateComputer(request, response); break;
			case "/search": searchProducts(request, response); break;
			default: viewComputers(request, response); break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private void viewComputers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String action = request.getParameter("action") != null
				? request.getParameter("action")
				: "null";
		List<Computer> computers = dao.getComputers();
		request.setAttribute("computers", computers);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertComputer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		int price = Integer.parseInt(request.getParameter("price"));
		
		dao.insertComputer(name,  type,  quantity,  price);
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void updateComputer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String action = request.getParameter("action") != null
			? request.getParameter("action")
			: request.getParameter("submit").toLowerCase();
		final int id = Integer.parseInt(request.getParameter("id"));
		
		Computer computer = dao.getComputer(id);
		switch (action) {
			case "rent": computer.buyMe(); break;
			case "save":
				String name =request.getParameter("name");
				String type = request.getParameter("type");
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				int price = Integer.parseInt(request.getParameter("price"));
				
				computer.setName(name);
				computer.setType(type);
				computer.setQuantity(quantity);
				computer.setPrice(price);
				break;
			case "delete": deleteComputer(id, request, response); return;
		}
		dao.updateComputer(computer);
		
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		try {
			final int id = Integer.parseInt(request.getParameter("id"));
			
			Computer computer = dao.getComputer(id);
			request.setAttribute("computer", computer);
		} catch (NumberFormatException e) {
			
		} finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("computerform.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	private void deleteComputer(final int id, HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		dao.deleteComputer(dao.getComputer(id));
		
		response.sendRedirect(request.getContextPath() + "/");
	}

	private void searchProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String category = request.getParameter("search_attribute") != null
				? request.getParameter("search_attribute")
				: null;
				
		final String query = request.getParameter("search_bar") != null
				? request.getParameter("search_bar").toLowerCase()
				: "";
		
		List<Computer> results = dao.searchComputer(category, query);
		
		request.setAttribute("computers", results);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
		dispatcher.forward(request,  response);
	}
}