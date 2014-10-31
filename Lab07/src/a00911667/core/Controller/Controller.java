package a00911667.core.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import a00911667.core.Database.Database;
import a00911667.core.Database.DatabaseAccessObject;

/**
 * Servlet implementation class Controller
 */

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DatabaseAccessObject dao;
	private Properties props;
	private Database database;

	/**
	 * @see Servlet#init() loads database driver, creates a connection and loads
	 *      properties
	 */
	public void init() throws ServletException {
		dao = new DatabaseAccessObject();
		database = new Database(dao);
		props = new Properties();
		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("dbprops.properties");
		try {
			props.load(stream);
			dao.loadDatabaseDriver(props.getProperty("Driver"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			dao.connect(props.getProperty("URL"), props.getProperty("User"),
					props.getProperty("Password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// reading the input
		String query = request.getParameter("queryString");

		if (query.startsWith("select") && query != null
				&& !query.trim().equals("")) {
			// Create a new session
			HttpSession session = request.getSession();
			// get current query that is inside the session
			String currentQuery = (String) session.getAttribute("selectQuery");

			// Check if query already exist
			if (!query.equals(currentQuery)) {
				session.setAttribute("selectQuery", query);
				Cookie queryCookie = new Cookie("query", query);
				queryCookie.setMaxAge(60 * 60 * 24 * 365); // 1 year
				response.addCookie(queryCookie);
			}

			int columnCount = database.getColumnCount(query);
			ResultSetMetaData rsmt = database.getResultSetMetaData(query);
			List<String> data = database.select(query);
			request.setAttribute("data", data);
			request.setAttribute("ResultSetMetaData", rsmt);
			request.setAttribute("numberOfColumns", columnCount);

			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/output/index.jsp");
			dispatcher.forward(request, response);
		} else {
			response.setHeader("Refresh", "0; URL=" + "welcome.jsp");
		}
	}

}
