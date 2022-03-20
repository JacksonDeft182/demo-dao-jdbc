package application;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	
	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Department department = new Department();
		
		
		System.out.println("---------------FindByID-DEPARTMENT----------------");
		
		System.out.print("Digite ID para Consulta: ");
		int idDep = sc.nextInt();
		department = departmentDao.findById(idDep);
		System.out.println(department);
		
		
		System.out.println("---------------FindAll-DEPARTMENT-----------------");
		
		List<Department> departmentList = departmentDao.findAll();
		departmentList.forEach(System.out::println);
		
		System.out.println("---------------FindAll-INSERIR-----------------");
		
		System.out.println("Insira nome do departamento: ");
		String nameDep = sc.next();
		department.setName(nameDep);
		departmentDao.insert(department); 
		
		System.out.println("---------------DELETE-DEPARTMENT-----------------");
		
		System.out.print("Digite ID para deleção: ");
		idDep = sc.nextInt();
		departmentDao.deleteById(idDep);

		System.out.println("---------------UPDATE-DEPARTMENT-----------------");
		
		System.out.print("Digite ID para Atualização: ");
		idDep = sc.nextInt();
		
		department = departmentDao.findById(idDep);
		
		if(department != null) {
			System.out.println("Digite novo nome do Departamento");
			String name = sc.next();
			department.setName(name);
			departmentDao.update(department);
		}
		else {
			System.out.println("ID de departamento não existe!!");
		}
		
		System.out.println("========== TEST 1: Seller FindById ==========");
		
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		System.out.println("");
		
		System.out.println("========== TEST 2: Seller FindByDepartment ==========");

		department = new Department(2,null);
		List<Seller> sellerList = sellerDao.findByDepartment(department);
		sellerList.forEach(System.out::println);
		System.out.println("");
		
		System.out.println("========== TEST 3: Seller FindByALL ==========");

		sellerList = sellerDao.findAll();
		sellerList.forEach(System.out::println); 
		System.out.println("");
		
		
		System.out.println("========== TEST 4: Seller UPDATE ==========");
		
		seller = sellerDao.findById(9);
		seller.setName("Chino Moreno");
		sellerDao.update(seller);
		System.out.println("Update Completes");
		System.out.println("---------------------------------------------------------");
		
		sellerList = sellerDao.findAll();
		sellerList.forEach(System.out::println);
		System.out.println("");
		
		System.out.println("========== TEST 5: Seller DELETE ==========");
		
		sellerDao.deleteById(12);

		sc.close();
	}

}
