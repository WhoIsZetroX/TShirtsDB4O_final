import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Entities.*;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Constraint;
import com.db4o.query.Query;
import javafx.beans.binding.IntegerBinding;

import javax.print.attribute.standard.MediaSize;

/**
 * @author Joan Anton Perez Branya
 * @since 19/02/2017
 *
 */

public class TShirtsDB4O {
	public static ArrayList<Order> orders;
	static ObjectContainer db;
	static int option=1, create, list, update;
	static String confirm, creditCardCreation="";
	static int customerCreation=0;
	static Scanner sc = new Scanner(System.in);

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
		fa.readArticlesFile("articles.csv");
		fa.readCreditCardsFile("creditCards.csv");
		fa.readCustomersFile("customers.csv");
		fa.readOrdersFile("orders.csv");
		fa.readOrderDetailsFile("orderDetails.csv");
		orders = fa.orders;
		try {

			File file = new File("orders.db");
			String fullPath = file.getAbsolutePath();
			db = Db4o.openFile(fullPath);

			///////////////////////////

			TSM.addOrders();//

			do {
				System.out.println("\n\n### MENU ###\n" +
						"1- Create data\n" +
						"2- Read data\n" +
						"3- Update data\n" +
						"4- Delete data\n" +
                        "5- Exit\n");
				option = sc.nextInt(); sc.nextLine();
				switch (option) {
					case 1:
						System.out.println("\n1- Article\n" +
								"2- CreditCard\n" +
								"3- Customer\n" +
								"4- Order\n" +
								"5- Set Order detail to order\n" +
								"6- Back <--\n");
						create=sc.nextInt();sc.nextLine();
						switch (create){
							case 1:
								System.out.println("Product id: ");
								int productId = sc.nextInt(); sc.nextLine();
								System.out.println("Product name: ");
								String productName = sc.nextLine();
								System.out.println("Product color: ");
								String productColor = sc.nextLine();
								System.out.println("Product size: ");
								String productSize = sc.nextLine();
								System.out.println("Product price: ");
								double productPrice = sc.nextDouble(); sc.nextLine();
								System.out.println("Are you sure? [Y/N]");
								confirm = sc.nextLine();
								if (confirm.equals("Y")||confirm.equals("y")) {
									TSM.addArticle(productId, productName, productColor, productSize, productPrice);
									System.out.println("Product added!");
									TSM.llistaArticlesById(productId);
								}else if (confirm.equals("N")||confirm.equals("n")){
									System.out.println(productId + " - " + productName + " - " + productColor + " - " + productSize + " - " + productPrice);
									System.out.println("Product NOT added :(");
								}else {System.out.println("Choose a valid option. [Y/N]-[y/n]");}
								break;
							case 2:
								TSM.createNewCreditCard();
								break;
							case 3:
								TSM.createNewCustomer();
								break;
							case 4:
								System.out.println("Order id: ");
								int idOrder = sc.nextInt(); sc.nextLine();
								SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
								System.out.println("Order date: [dd-MM-yyyy]");
								String orderDate = sc.nextLine();
								System.out.println("Order delivery date: [dd-MM-yyyy] ");
								String deliveryDate = sc.nextLine();
								System.out.println("1- Create new customer\n" +
										"2- Use an existing customer");
								int cOption = sc.nextInt(); sc.nextLine();
								if (cOption==1){
									customerCreation = TSM.createNewCustomer();
								}else if (cOption==2) {
									TSM.listCustomers();
									System.out.println("Customer id: ");
									customerCreation = sc.nextInt(); sc.nextLine();
								}else { break; }
								System.out.println("1- Set order detail\n" +
										"2- Not set order detail");
								int odOption = sc.nextInt(); sc.nextLine();
								Set<OrderDetail> orderDetail = new Set<OrderDetail>() {
									@Override
									public int size() {
										return 0;
									}

									@Override
									public boolean isEmpty() {
										return false;
									}

									@Override
									public boolean contains(Object o) {
										return false;
									}

									@Override
									public Iterator<OrderDetail> iterator() {
										return null;
									}

									@Override
									public Object[] toArray() {
										return new Object[0];
									}

									@Override
									public <T> T[] toArray(T[] a) {
										return null;
									}

									@Override
									public boolean add(OrderDetail orderDetail) {
										return false;
									}

									@Override
									public boolean remove(Object o) {
										return false;
									}

									@Override
									public boolean containsAll(Collection<?> c) {
										return false;
									}

									@Override
									public boolean addAll(Collection<? extends OrderDetail> c) {
										return false;
									}

									@Override
									public boolean retainAll(Collection<?> c) {
										return false;
									}

									@Override
									public boolean removeAll(Collection<?> c) {
										return false;
									}

									@Override
									public void clear() {

									}
								};
								if (odOption ==1){
									OrderDetail orderD = new OrderDetail();
									System.out.println("Article id: ");
									int lala =sc.nextInt(); sc.nextLine();
									Article article = TSM.llistaArticlesById2(lala);
									orderD.setArticle(article);
									System.out.println("Quantity: ");
									int quantity = sc.nextInt(); sc.nextLine();
									orderD.setQuantity(quantity);
                                    System.out.println("Price: ");
                                    int price = sc.nextInt(); sc.nextLine();
									orderD.setPriceEach(price);
									orderDetail.add(orderD);
								}else { break; }

								System.out.println("Are you sure? [Y/N]");
								confirm = sc.nextLine();
								if (confirm.equals("Y")||confirm.equals("y")) {
									TSM.addOrder(idOrder, formatter.parse(orderDate), formatter.parse(deliveryDate), customerCreation, orderDetail);
									System.out.println("Customer added!");
									TSM.listOrderById(idOrder);
								}else if (confirm.equals("N")||confirm.equals("n")){
									Set<OrderDetail> orderDetails = new Set<OrderDetail>() {
										@Override
										public int size() {
											return 0;
										}

										@Override
										public boolean isEmpty() {
											return false;
										}

										@Override
										public boolean contains(Object o) {
											return false;
										}

										@Override
										public Iterator<OrderDetail> iterator() {
											return null;
										}

										@Override
										public Object[] toArray() {
											return new Object[0];
										}

										@Override
										public <T> T[] toArray(T[] a) {
											return null;
										}

										@Override
										public boolean add(OrderDetail orderDetail) {
											return false;
										}

										@Override
										public boolean remove(Object o) {
											return false;
										}

										@Override
										public boolean containsAll(Collection<?> c) {
											return false;
										}

										@Override
										public boolean addAll(Collection<? extends OrderDetail> c) {
											return false;
										}

										@Override
										public boolean retainAll(Collection<?> c) {
											return false;
										}

										@Override
										public boolean removeAll(Collection<?> c) {
											return false;
										}

										@Override
										public void clear() {

										}
									};
									TSM.addOrder(idOrder, formatter.parse(orderDate), formatter.parse(deliveryDate), customerCreation, orderDetails);
									System.out.println("Customer added!");
									TSM.listOrderById(idOrder);
								}else {System.out.println("Choose a valid option. [Y/N]-[y/n]");}
								
								break;
							case 5:
								TSM.listOrders();
								Set<OrderDetail> orderDetail2 = new Set<OrderDetail>() {
									@Override
									public int size() {
										return 0;
									}

									@Override
									public boolean isEmpty() {
										return false;
									}

									@Override
									public boolean contains(Object o) {
										return false;
									}

									@Override
									public Iterator<OrderDetail> iterator() {
										return null;
									}

									@Override
									public Object[] toArray() {
										return new Object[0];
									}

									@Override
									public <T> T[] toArray(T[] a) {
										return null;
									}

									@Override
									public boolean add(OrderDetail orderDetail) {
										return false;
									}

									@Override
									public boolean remove(Object o) {
										return false;
									}

									@Override
									public boolean containsAll(Collection<?> c) {
										return false;
									}

									@Override
									public boolean addAll(Collection<? extends OrderDetail> c) {
										return false;
									}

									@Override
									public boolean retainAll(Collection<?> c) {
										return false;
									}

									@Override
									public boolean removeAll(Collection<?> c) {
										return false;
									}

									@Override
									public void clear() {

									}
								};
								System.out.println("Order id to add an order detail");
								int orderId = sc.nextInt(); sc.nextLine();
								OrderDetail orderD = new OrderDetail();
								System.out.println("Article id: ");
								int lala =sc.nextInt(); sc.nextLine();
								Article article = TSM.llistaArticlesById2(lala);
								orderD.setArticle(article);
								System.out.println("Quantity: ");
								int quantity = sc.nextInt(); sc.nextLine();
								orderD.setQuantity(quantity);
                                System.out.println("Price: ");
                                int price = sc.nextInt(); sc.nextLine();
								orderD.setPriceEach(price);
								orderDetail2.add(orderD);
								TSM.addOrderDetail(orderId, orderDetail2);
								break;
							case 6:
								System.out.println("<--");
								break;
								default:
									System.out.println("Invalid option");
						}
						break;
					case 2:
                        System.out.println("\n1- Articles\n" +
                                "2- CreditCards\n" +
                                "3- Customers\n" +
                                "4- Orders\n" +
                                "5- Back <--\n");
                        list = sc.nextInt(); sc.nextLine();
                        switch (list) {
                            case 1:
                                TSM.listArticlesSwitch();
                                //TSM.llistaArticlesByName("CALCETINES EJECUTIVOS 'JACKSON 3PK'");//
                                break;
                            case 2:
                                TSM.listCreditCardsSwitch();
                                break;
                            case 3:
                                TSM.listCustomersSwitch();
                                //TSM.llistaCustomerByName("Laura");
                                break;
                            case 4:
                                TSM.listOrdersSwitch();
                                //TSM.showOrdersByCustomerName("Laura");
                                //TSM.retrieveOrderContentById_Order(2);
                                break;
                            case 5:
                                System.out.println("<--");
                                break;
                                default:
                                    System.out.println("Invalid option");
                        }
					case 3:
                        System.out.println("\n1- Articles\n" +
                                "2- CreditCards\n" +
                                "3- Customers\n" +
                                "4- Orders\n" +
                                "5- Back <--\n");
                        update = sc.nextInt(); sc.nextLine();
                        switch (update) {
                            case 1:

                                break;
                            case 2:

                                break;
                            case 3:

                                break;
                            case 4:

                                break;
                            case 5:
                                System.out.println("<--");
                                break;
                            default:
                                System.out.println("Invalid option");
                        }
						TSM.updatePriceArticle(7, 12.00);
						TSM.changeCreditCardToCustomer(1);
						break;
					case 4:
						TSM.deletingArticlesByName("POLO BÁSICO 'MANIA'");
						TSM.deleteArticleById(7);
						TSM.deleteCustomerbyId(2);
						TSM.deleteOrderContentById_Order(2);
						break;
					case 5:
						TSM.clearDatabase();
						db.close();
						break;

						default:
							System.out.println( "Invalid option" );
				}
			}while (option!=0);

			///////////////////////////


//11,5,6
			/*TSM.addOrders();//
			TSM.listOrders();//
			TSM.listArticles();//
			TSM.addArticle(7, "CALCETINES EJECUTIVOS 'JACKSON 3PK'", "gris", "45", 18.00);//
			TSM.updatePriceArticle(7, 12.00);//
			TSM.llistaArticlesByName("CALCETINES EJECUTIVOS 'JACKSON 3PK'");//
			TSM.deletingArticlesByName("POLO BÁSICO 'MANIA'");//
			TSM.deleteArticleById(7);//
			TSM.listArticles();//
			TSM.listCustomers();//
			TSM.changeCreditCardToCustomer(1); // //TODO: Falta hacer que el usuario pueda cambiar como quiera los datos
			TSM.listCustomers();//
			TSM.llistaCustomerByName("Laura");//
			TSM.showOrdersByCustomerName("Laura");//
			TSM.showCreditCardByCustomerName("Laura");//
			TSM.deleteCustomerbyId(2);//
			TSM.retrieveOrderContentById_Order(2);//
			TSM.deleteOrderContentById_Order(2);//
			TSM.retrieveOrderContentById_Order(2);//
			TSM.listCustomers();//
			TSM.clearDatabase();//
			TSM.listOrders();//*/

		} catch (Exception e) { e.printStackTrace(); }
	}

	/**
	 * Select Customer using customer id and next generate a new credit card and
	 * update customer using the new credit card
	 * 
	 * @param i
	 *            idCustomer
	 */
	public void changeCreditCardToCustomer(int i) {
		System.out.println("\nActualizando tarjeta de credito al cliente");
		ObjectSet<Customer> result = db.queryByExample(new Customer(i, null, null, null, null, null));
		Customer customer = new Customer();
		while (result.hasNext()){
			customer = result.next();
		}
		Customer c = customer;
		CreditCard creditCard = new CreditCard("lala", "lala", 1, 2);
		c.setCreditCard(creditCard);
		db.set(c);
		System.out.println(db.get(c));
	}

	/**
	 * Select Article using id and next update price
	 * 
	 * @param id
	 *            article
	 * @param newPrice
	 */
	public void updatePriceArticle(int id, double newPrice) {
        System.out.println("\nActualizando precio del articulo");
        ObjectSet<Article> result = db.queryByExample(new Article(id, null, null, null, 0));
        while (result.hasNext()){
            Article article = result.next();
            article.setRecommendedPrice((float) newPrice);
            db.set(article);
            System.out.println(db.get(article));
        }
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
        System.out.println("\nAñadiendo articulo");
        Article article = new Article(i, string, string2, string3, (float)d);
		db.store(article);
        System.out.println(db.get(article));
    }

	public void addCreditCard(String string, String string2, int i, int d) {
		System.out.println("\nAdding credit card...");
		CreditCard creditCard = new CreditCard(string, string2, i, d);
		db.store(creditCard);
		System.out.println(db.get(creditCard));
	}

	public void addCustomer(int idCustomer, String name, String address, String email, String phone, String creditCard) {
		System.out.println("\nAdding customer...");
		CreditCard creditCardo = lala(creditCard);
		Customer customer = new Customer(idCustomer, name, address, email, phone, creditCardo);
		db.store(customer);
		System.out.println(db.get(customer));
	}

	public void addOrder(int idOrder, Date orderDate, Date deliveryDate, int customers, Set<OrderDetail> orderDetails) {
		System.out.println("\nAdding order...");
		Customer customer = lolo(customers);
		//Set<OrderDetail> orderDetail = lulu(orderDetails);
		Order order = new Order(idOrder, orderDate, deliveryDate, customer, orderDetails);
		db.store(order);
		listOrderById(idOrder);
		//System.out.println(db.get(order));
	}

	/**
	 * Delete an article using idArticle
	 * 
	 * @param i
	 *            idArticle
	 */
	public void deleteArticleById(int i) {
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
		System.out.println("\nDelete Order Content By Id_Order");
		ObjectSet<Order> result = db.queryByExample(new Order(i, null, null, null, null));
			Order or = result.next();
			db.delete(or);
			System.out.println(db.get(or));
	}

	/**
	 * Select Order using his id and order details
	 * 
	 * @param i
	 *            idOrder
	 */
	public void retrieveOrderContentById_Order(int i) {
		System.out.println("\nRetrieve Order Content By Id_Order");
		ObjectSet<Order> result = db.queryByExample(new Order(i, null, null, null, null));
		while (result.hasNext()){
			System.out.println(result.next().toString());
		}
	}

    public void retrieveOrderDetailsById_Order(int i) {
        System.out.println("\nRetrieve Order Content By Id_Order");
        ObjectSet<Order> result = db.queryByExample(new Order(i, null, null, null, null));
        while (result.hasNext()){
            System.out.println(result.next().getDetails().toString());
        }
    }

	/**
	 * Delete Customer using idCustomer
	 * 
	 * @param i
	 *            idCustomer
	 */
	public void deleteCustomerbyId(int i) {
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
        System.out.println("\nShow credit card by customer name");
        ObjectSet<Customer> result = db.queryByExample(new Customer(0, string, null, null, null, null));
        while (result.hasNext()){
            System.out.println(result.next().getCreditCard());
        }
	}

    public void showCreditCardByCustomerId(int string) {
        System.out.println("\nShow credit card by customer id");
        ObjectSet<Customer> result = db.queryByExample(new Customer(string, null, null, null, null, null));
        while (result.hasNext()){
            System.out.println(result.next().getCreditCard());
        }
    }

	/**
	 * Method to list Oders and orderdetails from the database using the
	 * customer name
	 */
	public void showOrdersByCustomerName(String string) {
		List<Order> articles = db.query(new com.db4o.query.Predicate<Order>() {
			@Override
			public boolean match(Order article) {
				return article.getCustomer().getName().compareTo(string)==0;
			}
		});
		System.out.println("\nShow Orders By Customer Name: "+articles.size());
		for (Order article : articles) System.out.println(article.toString());

	}

	/** delete all objects from the whole database */
	public void clearDatabase() {
        System.out.println("\nELIMINANDO!");
        ObjectSet<Order> result =  db.queryByExample(Order.class);
        System.out.println("Orders: "+result.size());
        while (result.hasNext()) {
            db.delete(result.next());
        }
        ObjectSet<Customer> result2 =  db.queryByExample(Customer.class);
        System.out.println("Customers: "+result2.size());
        while (result2.hasNext()) {
            db.delete(result2.next());
        }
        ObjectSet<Article> result3 =  db.queryByExample(Article.class);
        System.out.println("Articles: "+result3.size());
        while (result3.hasNext()) {
            db.delete(result3.next());
        }
        ObjectSet<CreditCard> result4 =  db.queryByExample(CreditCard.class);
        System.out.println("Credits cards: "+result4.size());
        while (result4.hasNext()) {
            db.delete(result4.next());
        }
        ObjectSet<OrderDetail> result5 =  db.queryByExample(OrderDetail.class);
        System.out.println("Order details: "+result5.size());
        while (result5.hasNext()) {
            db.delete(result5.next());
        }
    }

	/**
	 * Delete Article using article name
	 * 
	 * @param string
	 *            Article name
	 */
	public void deletingArticlesByName(String string) {
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
        List<Article> articles = db.query(new com.db4o.query.Predicate<Article>() {
            @Override
            public boolean match(Article article) {
                return article.getName().compareTo(string)==0;
            }
        });
        System.out.println("\nListar articulos por nombre: "+articles.size());
        for (Article article : articles) System.out.println(article.toString());
    }

    public void llistaArticlesByColour(String string) {
        List<Article> articles = db.query(new com.db4o.query.Predicate<Article>() {
            @Override
            public boolean match(Article article) {
                return article.getColour().compareTo(string)==0;
            }
        });
        System.out.println("\nListar articulos por nombre: "+articles.size());
        for (Article article : articles) System.out.println(article.toString());
    }

    public void llistaArticlesBySize(String string) {
        List<Article> articles = db.query(new com.db4o.query.Predicate<Article>() {
            @Override
            public boolean match(Article article) {
                return article.getSize().compareTo(string)==0;
            }
        });
        System.out.println("\nListar articulos por nombre: "+articles.size());
        for (Article article : articles) System.out.println(article.toString());
    }

    public void llistaArticlesByPrice(float flo) {
       /* List<Article> articles = db.query(new com.db4o.query.Predicate<Article>() {
            @Override
            public boolean match(Article article) {
                return article.getRecommendedPrice().eq(flo)==0;
            }
        });
        System.out.println("\nListar articulos por nombre: "+articles.size());
        for (Article article : articles) System.out.println(article.toString());*/
        ObjectSet<Article> result = db.queryByExample(new Article(0, null, null, null, flo));
        while (result.hasNext()){
            System.out.println(result.next().toString());
        }
    }

	public void llistaArticlesById(int i) {
		ObjectSet<Article> result = db.queryByExample(new Article(i, null, null, null, 0));
		while (result.hasNext()){
			System.out.println(result.next());
		}

	}

	public Article llistaArticlesById2(int i) {
		ObjectSet<Article> result2 = db.queryByExample(new Article(i, null, null, null, 0));
		Article article = null;
		List l = new ArrayList();

		while (result2.hasNext()){
			l.addAll(Collections.singleton(result2.next()));
		}
		for (Object list : l) System.out.println(list.toString());

		return article;
	}


	public void listCreditCardsByNumbercard(String s) {
		ObjectSet<CreditCard> result = db.queryByExample(new CreditCard(s, null, 0, 0));
		while (result.hasNext()){
			System.out.println(result.next());
		}
	}

	public CreditCard lala(String s) {
		ObjectSet<CreditCard> result = db.queryByExample(new CreditCard(s, null, 0, 0));
		while (result.hasNext()){
			return result.next();
		}
		return null;
	}

	public Customer lolo(int s) {
		ObjectSet<Customer> result = db.queryByExample(new Customer(s, null, null, null, null, null));
		while (result.hasNext()){
			return result.next();
		}
		return null;
	}

	/*public OrderDetail lulu(int s) {
		ObjectSet<OrderDetail> result = db.queryByExample(new OrderDetail(s, 0, 0, null, null));
		while (result.hasNext()){
			return result.next();
		}
		return null;
	}*/

	public void llistaCustomerById(int s) {
		ObjectSet<Customer> result = db.queryByExample(new Customer(s, null, null, null, null, null));
		while (result.hasNext()){
			System.out.println(result.next());
		}
	}

	/** Method to list Customers from the database using their name */
	public void llistaCustomerByName(String string) {
        List<Customer> customers = db.query(new com.db4o.query.Predicate<Customer>() {
            @Override
            public boolean match(Customer customer) {
                return customer.getName().compareTo(string)==0;
            }
        });
        System.out.println("\nCustomers by name: "+customers.size());
        for (Customer customer : customers) System.out.println(customer.toString());

	}

    public void llistaCustomerByAddress(String string) {
        List<Customer> customers = db.query(new com.db4o.query.Predicate<Customer>() {
            @Override
            public boolean match(Customer customer) {
                return customer.getAddress().compareTo(string)==0;
            }
        });
        System.out.println("\nCustomers by address: "+customers.size());
        for (Customer customer : customers) System.out.println(customer.toString());

    }

    public void llistaCustomerByEmail(String string) {
        List<Customer> customers = db.query(new com.db4o.query.Predicate<Customer>() {
            @Override
            public boolean match(Customer customer) {
                return customer.getEmail().compareTo(string)==0;
            }
        });
        System.out.println("\nCustomers by email: "+customers.size());
        for (Customer customer : customers) System.out.println(customer.toString());

    }

    public void llistaCustomerByPhone(String string) {
        List<Customer> customers = db.query(new com.db4o.query.Predicate<Customer>() {
            @Override
            public boolean match(Customer customer) {
                return customer.getPhone().compareTo(string)==0;
            }
        });
        System.out.println("\nCustomers by phone: "+customers.size());
        for (Customer customer : customers) System.out.println(customer.toString());

    }

    public void llistaCustomerByCreditCard(String string) {
        List<Customer> customers = db.query(new com.db4o.query.Predicate<Customer>() {
            @Override
            public boolean match(Customer customer) {
                return customer.getCreditCard().getNumber().compareTo(string)==0;
            }
        });
        System.out.println("\nCustomers by credit card: "+customers.size());
        for (Customer customer : customers) System.out.println(customer.toString());

    }

	public void listOrderById(int s) {
		ObjectSet<Order> result = db.queryByExample(new Order(s, null, null, null, null));
		while (result.hasNext()){
			System.out.println(result.next());
		}
	}

	public void addOrderDetail(int s, Set<OrderDetail> orderDetails) {
		ObjectSet<Order> result = db.queryByExample(new Order(s, null, null, null, null));
        Order order = new Order();
		//List l = new ArrayList();
		while (result.hasNext()){
			//l.addAll(Collections.singleton(result.next()));
			//db.set(result.next().setDetails(orderDetails));
            order = result.next();
		}
		//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //System.out.println(order.get(0)+" - "+order.get(1)+" - "+order.get(2)+" - "+order.get(3)+" - "+ order.get(4)+" LALALA");

		order.setDetails(orderDetails);
        db.set(order);
        /*try {
			order = new Order(Integer.parseInt(l.get(0).toString()),formatter.parse(l.get(1).toString()),formatter.parse(l.get(2).toString()),(Customer) l.get(3), (Set<OrderDetail>) l.get(4));
            //System.out.println(Integer.parseInt(l.get(0).toString())+" - "+formatter.parse(l.get(1).toString())+" - "+formatter.parse(l.get(2).toString())+" - "+(Customer) l.get(3)+" - "+ (Set<OrderDetail>) l.get(4));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		order.setDetails(orderDetails);
		db.set(order);*/


       /* Customer customer = new Customer();
        while (result.hasNext()){
            customer = result.next();
        }
        Customer c = customer;
        CreditCard creditCard = new CreditCard("lala", "lala", 1, 2);
        c.setCreditCard(creditCard);
        db.set(c);
        System.out.println(db.get(c));*/

	}

	/** Method to list all Customers from the database */
	public void listCustomers() {
        System.out.println("\nCustomers");
        ObjectSet<Customer> result =  db.queryByExample(Customer.class);
        System.out.println(result.size());
        while (result.hasNext()) {
            System.out.println(result.next());
        }
	}

    public void listCustomersId() {
        System.out.println("\nCustomers id");
        ObjectSet<Customer> result =  db.queryByExample(Customer.class);
        System.out.println(result.size());
        while (result.hasNext()) {
            System.out.println("Id: "+result.next().getIdCustomer());
        }
    }

    public void listCustomersName() {
        System.out.println("\nCustomers name");
        ObjectSet<Customer> result =  db.queryByExample(Customer.class);
        System.out.println(result.size());
        while (result.hasNext()) {
            System.out.println("Name: "+result.next().getName());
        }
    }

	/** Method to list all Articles from the database */
	public void listArticles() {
		System.out.println("\nArticulos");
		ObjectSet<Article> result = db.queryByExample(Article.class);
		System.out.println(result.size());
		while (result.hasNext()) {
			System.out.println(result.next());
		}
	}

	public void listCreditCards() {
		System.out.println("\nCredit cards");
		ObjectSet<CreditCard> result = db.queryByExample(CreditCard.class);
		System.out.println(result.size());
		while (result.hasNext()) {
			System.out.println(result.next());
		}
	}

	/** Method to add all orders from ArrayList and store them into database */
	public void addOrders() {
		for(int i =0;i<orders.size();i++) {
			db.store(orders.get(i));
		}
	}

	/** Method to list all Orders from the database */
	public void listOrders() {
		System.out.println("\nOrdenes");
		ObjectSet<Order> result =  db.queryByExample(Order.class);
		System.out.println(result.size());
		while (result.hasNext()) {
			System.out.println(result.next());
		}

	}

	public String createNewCreditCard(){
		System.out.println("Credit card number: ");
		String number = sc.nextLine();
		System.out.println("Security number: ");
		String securityNumber = sc.nextLine();
		System.out.println("Last Month: ");
		int lastMonth = sc.nextInt(); sc.nextLine();
		System.out.println("Last year: ");
		int lastYear = sc.nextInt(); sc.nextLine();
		System.out.println("Are you sure? [Y/N]");
		confirm = sc.nextLine();
		if (confirm.equals("Y")||confirm.equals("y")) {
			addCreditCard(number, securityNumber, lastMonth, lastYear);
			System.out.println("Credit card added!");
			listCreditCardsByNumbercard(number);
		}else if (confirm.equals("N")||confirm.equals("n")){
			System.out.println(number + " - " + securityNumber + " - " + lastMonth + " - " + lastYear);
			System.out.println("Credit card NOT added :(");
		}else {System.out.println("Choose a valid option. [Y/N]-[y/n]");}
		return number;
	}

	public int createNewCustomer(){
		System.out.println("Costumer id: ");
		int idCustomer = sc.nextInt(); sc.nextLine();
		System.out.println("Customer name: ");
		String name = sc.nextLine();
		System.out.println("Customer address: ");
		String address = sc.nextLine();
		System.out.println("Customer mail: ");
		String email = sc.nextLine();
		System.out.println("Customer phone: ");
		String phone = sc.nextLine();
		System.out.println("1- Create new credit card\n" +
				"2- Use an existing credit card");
		int ccOption = sc.nextInt(); sc.nextLine();
		if (ccOption==1){
			creditCardCreation = createNewCreditCard();

			System.out.println("Are you sure? [Y/N]");
			confirm = sc.nextLine();
			if (confirm.equals("Y")||confirm.equals("y")) {
				addCustomer(idCustomer, name, address, email, phone, creditCardCreation);
				System.out.println("Customer added!");
				llistaCustomerById(idCustomer);
				return idCustomer;
			}else if (confirm.equals("N")||confirm.equals("n")){
				System.out.println(idCustomer + " - " + name + " - " + address + " - " + email + " - " + phone + " - " + creditCardCreation);
				System.out.println("Customer NOT added :(");
			}else {System.out.println("Choose a valid option. [Y/N]-[y/n]");}
			
		}else if (ccOption==2) {
			listCreditCards();
			System.out.println("Customer credit card: ");
			creditCardCreation = sc.nextLine();
			System.out.println("Are you sure? [Y/N]");
			confirm = sc.nextLine();
			if (confirm.equals("Y")||confirm.equals("y")) {
				addCustomer(idCustomer, name, address, email, phone, creditCardCreation);
				System.out.println("Customer added!");
				llistaCustomerById(idCustomer);
				return idCustomer;
			}else if (confirm.equals("N")||confirm.equals("n")){
				System.out.println(idCustomer + " - " + name + " - " + address + " - " + email + " - " + phone + " - " + creditCardCreation);
				System.out.println("Customer NOT added :(");
			}else {System.out.println("Choose a valid option. [Y/N]-[y/n]");}
		}
		return idCustomer;

	}

	public void listArticlesSwitch(){
        System.out.println("\n1- List all\n" +
                "2- List by id\n" +
                "3- List by name\n" +
                "4- List by colour\n" +
                "5- List by size\n" +
                "6- List by price\n" +
                "7- Back <--\n");
        int listOption = sc.nextInt(); sc.nextLine();
        if (listOption==1){
            listArticles();
        }else if (listOption==2) {
            System.out.println("Article id: ");
            int artId = sc.nextInt(); sc.nextLine();
            llistaArticlesById(artId);
        }else if (listOption==3){
            System.out.println("Article name: ");
            String artName = sc.nextLine();
            llistaArticlesByName(artName);
        }else if (listOption==4){
            System.out.println("Article colour: ");
            String artCol = sc.nextLine();
            llistaArticlesByColour(artCol);
        }else if (listOption==5){
            System.out.println("Article size: ");
            String artSize = sc.nextLine();
            llistaArticlesBySize(artSize);
        }else if (listOption==6){
            System.out.println("Article price: ");
            float artPrice = sc.nextFloat(); sc.nextLine();
            llistaArticlesByPrice(artPrice);
        }else if (listOption==7){
            System.out.println("<--");
        }else{
            System.out.println("Invalid option");
        }
    }

    public void listCreditCardsSwitch(){
        System.out.println("\n1- List all credit cards\n" +
                "2- List credit cards by number card\n" +
                "3- List credit cards by customer id\n" +
                "4- List credit card by customer name\n" +
                "5- Back <--\n");
        int listCards = sc.nextInt(); sc.nextLine();
        if (listCards==1) {
            listCreditCards();
        }else if (listCards==2) {
            System.out.println("Number card: ");
            String nc = sc.nextLine();
            listCreditCardsByNumbercard(nc);
        }else if (listCards==3) {
            listCustomersId();
            System.out.println("Customer id: ");
            int ci = sc.nextInt(); sc.nextLine();
            showCreditCardByCustomerId(ci);
        }else if (listCards==4) {
            listCustomersName();
            System.out.println("Customer name: ");
            String cn = sc.nextLine();
            showCreditCardByCustomerName(cn);
            //TSM.showCreditCardByCustomerName("Laura");
        }else if (listCards==5){
            System.out.println("<--");
        }else{
            System.out.println("Invalid option");
        }
    }

    public void listCustomersSwitch(){
        System.out.println("\n1- List all\n" +
                "2- List customers by id\n" +
                "3- List customers by name\n" +
                "4- List customers by address\n" +
                "5- List customers by email\n" +
                "6- List customers by phone\n" +
                "7- List customers by creditCard\n" +
                "8- Back <--");
        int listCustomers = sc.nextInt(); sc.nextLine();
        if (listCustomers==1) {
            listCustomers();
        }else if (listCustomers==2) {
            listCustomersId();
            System.out.println("Customer id: ");
            int custId = sc.nextInt(); sc.nextLine();
            llistaCustomerById(custId);
        }else if (listCustomers==3) {
            listCustomersName();
            System.out.println("Customer name: ");
            String custName = sc.nextLine();
            llistaCustomerByName(custName);
        }else if (listCustomers==4) {
            System.out.println("Customer address: ");
            String custAddress = sc.nextLine();
            llistaCustomerByAddress(custAddress);
        }else if (listCustomers==5) {
            System.out.println("Customer email: ");
            String custemail = sc.nextLine();
            llistaCustomerByEmail(custemail);
        }else if (listCustomers==6) {
            System.out.println("Customer phone: ");
            String custphone = sc.nextLine();
            llistaCustomerByPhone(custphone);
        }else if (listCustomers==7) {
            System.out.println("Customer credit card: ");
            String custcc = sc.nextLine();
            llistaCustomerByCreditCard(custcc);
        }else if (listCustomers==8) {
            System.out.println("<--");
        }else{
            System.out.println("Invalid option");
        }
    }

    public void listOrdersByDate(String order){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        ObjectSet<Order> result = null;
        try {
            result = db.queryByExample(new Order(0, formatter.parse(order), null, null, null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while (result.hasNext()){
            System.out.println(result.next());
        }

    }

    public void listOrdersByDeliveryDate(String order){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        ObjectSet<Order> result = null;
        try {
            result = db.queryByExample(new Order(0, null, formatter.parse(order), null, null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while (result.hasNext()){
            System.out.println(result.next());
        }

    }

    public void listOrdersSwitch(){
        System.out.println("1- List all" +
                "2- List orders by id" +
                "3- List orders by order date" +
                "4- List orders by delivery date" +
                "5- List orders by costumer name" +
                "6- Retrieve order details by id order" +
                "7- Back <--");
        int listorder = sc.nextInt(); sc.nextLine();

        if (listorder==1){
            listOrders();
        }else if (listorder==2){
            System.out.println("Order id");
            int ordid = sc.nextInt(); sc.nextLine();
            retrieveOrderContentById_Order(ordid);
        }else if (listorder==3){
            System.out.println("Order date [31-12-2018]");
            String ordid = sc.nextLine();
            listOrdersByDate(ordid);
        }else if (listorder==4){
            System.out.println("Order delivery date [31-12-2018]");
            String ordid = sc.nextLine();
            listOrdersByDeliveryDate(ordid);
        }else if (listorder==5){
            System.out.println("Customer name: ");
            String ordcname = sc.nextLine();
            showOrdersByCustomerName(ordcname);
        }else if (listorder==6){
            System.out.println("Order id: ");
            int ordid = sc.nextInt(); sc.nextLine();
            retrieveOrderDetailsById_Order(ordid);
        }else if (listorder==7){
            System.out.println("<--");
        }else{
            System.out.println("Invalid option");
        }
    }

}
