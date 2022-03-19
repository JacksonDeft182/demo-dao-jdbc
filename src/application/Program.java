package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("========== TEST 1: Seller FindById ==========");
		
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("========== TEST 2: Seller FindALL ==========");
		
		List<Seller> sellerList = new ArrayList<>();
		sellerList = sellerDao.findAll();
		sellerList.forEach(System.out::println);
		
		System.out.println("========== TEST 3: Seller DeleteById ==========");
		System.out.print("Insira ID para exclusão:");
		sellerDao.deleteById(sc.nextInt());
		
		System.out.print("Name: ");
		String name = sc.next();
		System.out.print("Email: ");
		String email = sc.next();
		System.out.print("Data Nascimento: ");
		Date birthDate = sdf.parse(sc.next());
		System.out.print("Salário: ");
		Double salary = sc.nextDouble();
		System.out.print("ID Departamento (1/2/3/4): ");
		int idDepartment = sc.nextInt();
		Department department = new Department(2,null);
		sellerDao.insert(new Seller(null,name, email, birthDate, salary, department));
		
		
	}

}
