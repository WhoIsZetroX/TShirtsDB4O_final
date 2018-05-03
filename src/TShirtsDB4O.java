import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.sound.midi.Soundbank;

import Entities.Customer;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import Entities.Article;
import Entities.Order;

/**
 * @author Joan Anton Perez Branya
 * @since 19/02/2017
 *
 */

public class TShirtsDB4O {
	public static ArrayList<Order> orders;
	static ObjectContainer db;


	/**
	 * Implement TODO methods and run to test
	 * 
	 * @param args
	 *            no args
	 * @throws IOException
	 *             in order to read files
	 * @throws ParseException
	 *             in order to parse data formats
	 */
	public static void main(String[] args) throws IOException, ParseException {
		TShirtsDB4O TSM = new TShirtsDB4O();
		FileAccessor fa = new FileAccessor();
		fa.readArticlesFile("articles.csv"); //TODO: Está mal, no mete todos los articulos
		fa.readCreditCardsFile("creditCards.csv");
		fa.readCustomersFile("customers.csv");
		fa.readOrdersFile("orders.csv");
		fa.readOrderDetailsFile("orderDetails.csv");
		orders = fa.orders;
		try {

			File file = new File("orders.db");
			String fullPath = file.getAbsolutePath();
			db = Db4o.openFile(fullPath);
//11,5,6
			TSM.addOrders();//
			TSM.listOrders();//
			TSM.listArticles();//
			TSM.addArticle(7, "CALCETINES EJECUTIVOS 'JACKSON 3PK'", "gris", "45", 18.00);//
            TSM.updatePriceArticle(7, 12.00);
			TSM.llistaArticlesByName("CALCETINES EJECUTIVOS 'JACKSON 3PK'");//
			TSM.deletingArticlesByName("POLO BÁSICO 'MANIA'");//
            TSM.deleteArticleById(7);//
			TSM.listArticles();//
			TSM.listCustomers();//
			TSM.changeCreditCardToCustomer(1);
			TSM.listCustomers();//
			TSM.llistaCustomerByName("Laura");//
			TSM.showOrdersByCustomerName("Laura");
			TSM.showCreditCardByCustomerName("Laura");//
			TSM.deleteCustomerbyId(2);//
			TSM.retrieveOrderContentById_Order(2);
			TSM.deleteOrderContentById_Order(2);
			TSM.retrieveOrderContentById_Order(2);
			TSM.listCustomers();//
			TSM.clearDatabase();// Creo xd //
			TSM.listOrders();//

		} finally {
			// close database
			db.close();
		}
	}

	/**
	 * Select Customer using customer id and next generate a new credit card and
	 * update customer using the new credit card
	 * 
	 * @param i
	 *            idCustomer
	 */
	public void changeCreditCardToCustomer(int i) {
		// TODO Auto-generated method stub

	}

	/**
	 * Select Article using id and next update price
	 * 
	 * @param id
	 *            article
	 * @param newPrice
	 */
	public void updatePriceArticle(int id, double newPrice) {
		// TODO Auto-generated method stub

	}

	/**
	 * Add a new article into database
	 * 
	 * @param i
	 *            article id
	 * @param string
	 *            article name
	 * @param string2
	 *            article colour
	 * @param string3
	 *            article size
	 * @param d
	 *            article price
	 */
	public void addArticle(int i, String string, String string2, String string3, double d) {
		// TODO Auto-generated method stub
		Article article = new Article(i, string, string2, string3, (float)d);
		db.store(article);
	}

	/**
	 * Delete an article using idArticle
	 * 
	 * @param i
	 *            idArticle
	 */
	public void deleteArticleById(int i) {
		// TODO Auto-generated method stub
		ObjectSet<Article> result = db.queryByExample(new Article(i, null, null, null, 0));
		while (result.hasNext()){
			db.delete(result.next());
		}
	}

	/**
	 * Delete Order and its orderdetails using idOrder
	 * 
	 * @param i
	 *            idOrder
	 */
	public void deleteOrderContentById_Order(int i) {
		// TODO Auto-generated method stub

	}

	/**
	 * Select Order using his id and order details
	 * 
	 * @param i
	 *            idOrder
	 */
	public void retrieveOrderContentById_Order(int i) {
		// TODO Auto-generated method stub

	}

	/**
	 * Delete Customer using idCustomer
	 * 
	 * @param i
	 *            idCustomer
	 */
	public void deleteCustomerbyId(int i) {
		// TODO Auto-generated method stub
		ObjectSet<Customer> result = db.queryByExample(new Customer(i, null, null, null, null, null));
		while (result.hasNext()){
			db.delete(result.next());
		}
	}

	/**
	 * Select Customer using customer name and next select the credit card
	 * values
	 * 
	 * @param string
	 *            customer name
	 */
	public void showCreditCardByCustomerName(String string) {
		// TODO Auto-generated method stub
        System.out.println("\nShow credit card by customer name");
        ObjectSet<Customer> result = db.queryByExample(new Customer(0, string, null, null, null, null));
        while (result.hasNext()){
            System.out.println(result.next().getCreditCard());
        }
	}

	/**
	 * Method to list Oders and orderdetails from the database using the
	 * customer name
	 */
	public void showOrdersByCustomerName(String string) {
		// TODO Auto-generated method stub
        /*List<Customer> customers = db.query(new com.db4o.query.Predicate<Customer>() {
            @Override
            public boolean match(Customer customer) {
                return customer.getName().compareTo(string)==0;
            }
        });
        ObjectSet<Order> result = db.queryByExample(Order.class);
        while (result.hasNext()) {
            System.out.println("\nOrders by customer name: " + orders.size());
            for (Customer customer : customers) {
                Order orderss = result.next();
                try {
                    if (customer.getName().equals(orderss.getCustomer().getName())) System.out.println(orderss);
                } catch (Exception e){}
            }
        }*/
	}

	/** delete all objects from the whole database */
	public void clearDatabase() {
		// TODO Auto-generated method stub
        System.out.println("\nELIMINANDO!");
        ObjectSet<Order> result =  db.queryByExample(Order.class);
        System.out.println(result.size());
        while (result.hasNext()) {
            db.delete(result.next());
        }
        ObjectSet<Customer> result2 =  db.queryByExample(Customer.class);
        System.out.println(result2.size());
        while (result2.hasNext()) {
            db.delete(result2.next());
        }
        ObjectSet<Article> result3 =  db.queryByExample(Article.class);
        System.out.println(result3.size());
        while (result3.hasNext()) {
            db.delete(result3.next());
        }
    }

	/**
	 * Delete Article using article name
	 * 
	 * @param string
	 *            Article name
	 */
	public void deletingArticlesByName(String string) {
		// TODO Auto-generated method stub
        ObjectSet<Article> result = db.queryByExample(new Article(0, string, null, null, 0));
        System.out.println("\nEliminando articulos por nombre: "+result.size());
        while (result.hasNext()){
            Article article = result.next();
            System.out.println(article);
            db.delete(article);
        }

	}

	/** Method to list Articles from the database using their name */
	public void llistaArticlesByName(String string) {
		// TODO Auto-generated method stub
        List<Article> articles = db.query(new com.db4o.query.Predicate<Article>() {
            @Override
            public boolean match(Article article) {
                return article.getName().compareTo(string)==0;
            }
        });
        System.out.println("\nListar articulos por nombre: "+articles.size());
        for (Article article : articles) System.out.println(article.toString());
    }

	/** Method to list Customers from the database using their name */
	public void llistaCustomerByName(String string) {
		// TODO Auto-generated method stub

        List<Customer> customers = db.query(new com.db4o.query.Predicate<Customer>() {
            @Override
            public boolean match(Customer customer) {
                return customer.getName().compareTo(string)==0;
            }
        });
        System.out.println("\nCustomers by name: "+customers.size());
        for (Customer customer : customers) System.out.println(customer.toString());

	}

	/** Method to list all Customers from the database */
	public void listCustomers() {
		// TODO Auto-generated method stub
        System.out.println("\nCustomers");
        ObjectSet<Customer> result =  db.queryByExample(Customer.class);
        System.out.println(result.size());
        while (result.hasNext()) {
            System.out.println(result.next());
        }
	}

	/** Method to list all Articles from the database */
	public void listArticles() {
		// TODO Auto-generated method stub
		System.out.println("\nArticulos");
		ObjectSet<Article> result = db.queryByExample(Article.class);
		System.out.println(result.size());
		while (result.hasNext()) {
			System.out.println(result.next());
			
		}
	}

	/** Method to add all orders from ArrayList and store them into database */
	public void addOrders() {
		// TODO Auto-generated method stub
		for(int i =0;i<orders.size();i++) {
			db.store(orders.get(i));
		}
	}

	/** Method to list all Orders from the database */
	public void listOrders() {
		// TODO Auto-generated method stub
		System.out.println("\nOrdenes");
		ObjectSet<Order> result =  db.queryByExample(Order.class);
		System.out.println(result.size());
		while (result.hasNext()) {
			System.out.println(result.next());
		}

	}
}
