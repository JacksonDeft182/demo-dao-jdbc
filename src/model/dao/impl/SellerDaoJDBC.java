package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO seller\r\n" 
					+	"(Name, Email, BirthDate, BaseSalary, DepartmentId) " 
					+	"VALUES  (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Erro insperado! Nenhuma linha alterada");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			st.setInt(1, id);
			st.executeUpdate();	
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName\r\n" 
					+	"FROM seller INNER JOIN department\r\n" 
					+	"ON seller.DepartmentId = department.Id\r\n" 
					+	"WHERE seller.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs,dep);
				return seller;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}
	
	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Seller> seller = new ArrayList<>();
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\r\n"
					+	"FROM seller INNER JOIN department\r\n" 
					+	"ON seller.DepartmentId = department.Id\r\n" 
					+	"ORDER BY Id");
			rs = st.executeQuery();
			
			while(rs.next()) {
				Department dep = instantiateDepartment(rs);
				seller.add(instantiateSeller(rs,dep));
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		return seller;
	}
	

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId")); 
		dep.setName(rs.getString("DepName"));
		return dep;
	}
	
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setDepartment(dep);
		return seller;
	}



}
