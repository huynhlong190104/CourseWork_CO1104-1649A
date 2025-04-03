package CW;

import java.util.Scanner;

// Implementing BookList with ArrayList_ADT
class BookList implements ArrayList_ADT<Book> {
    private Book[] bookArray;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public BookList() {
        this.bookArray = new Book[INITIAL_CAPACITY];
        this.size = 0;
    }

    // add book function
    @Override
    public boolean add(Book book) {
        if (size == bookArray.length) {
            expandCapacity();
        }
        bookArray[size++] = book;
        return true;
    }

    @Override
    public boolean remove(String isbn) {
        for (int i = 0; i < size; i++) {
            if (bookArray[i].isbn.equals(isbn)) {
                for (int j = i; j < size - 1; j++) {
                    bookArray[j] = bookArray[j + 1];
                }
                bookArray[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(String isbn) {
        for (int i = 0; i < size; i++) {
            if (bookArray[i].isbn.equals(isbn)) {
                return true;
            }
        }
        return false;
    }


    // expand the size
    private void expandCapacity() {
        int newSize = bookArray.length * 2;
        Book[] newBooks = new Book[newSize];

        for (int i = 0; i < bookArray.length; i++) {
            newBooks[i] = bookArray[i];
        }

        bookArray = newBooks;
    }

    public Book get(int index) {
        if (index < 0 || index >= size) return null;
        return bookArray[index];
    }

    public int size() {
        return size;
    }

    // display all the available books
    public void displayAllBook() {
        System.out.println("\nAvailable Books :");
        for (int i = 0; i < size; i++) {
            bookArray[i].displayBook();
        }
    }
}

// Implementing OrderQueue using linked list
class OrderQueue implements LinkQueue_ADT<Order> {
    static class Node {
        Order order;
        Node next;

        Node(Order order) {
            this.order = order;
            this.next = null;
        }
    }

    Node head;
    private Node tail;
    private int size;

    public OrderQueue() {
        this.head = this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void offer(Order order) {
        //long startTime = System.nanoTime();
        Node newNode = new Node(order);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        //long endTime = System.nanoTime(); // Lấy thời gian kết thúc
        //long duration = endTime - startTime;
        //duration = duration/ 1000000;
        //System.out.println("Thời gian chạy: " + duration + " ms");

    }




    @Override
    public Order poll() {
        if (isEmpty()) {
            System.out.println("No orders to process.");
            return null;
        }
        Order removedOrder = head.order;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return removedOrder;
    }

    @Override
    public Order peek() {
        return (head != null) ? head.order : null;
    }

    @Override
    public int size() {
        return size;
    }

    // LOGIC DISPLAY ORDER
    public void displayAllOrders() {
        if (isEmpty()) {
            System.out.println("No orders placed yet.");
            return;
        }
        System.out.println("\nAll Orders:");
        Node current = head;
        while (current != null) {
            current.order.displayOrderInformation();
            current = current.next;
        }
    }
}

// Class Book
class Book {
    String title;
    String author;
    double price;
    int quantity;
    String isbn;

    public Book(String title, String author, double price, int quantity, String isbn) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.isbn = isbn;
    }

    // DISPLAY BOOK
    public void displayBook() {

        System.out.println("📖 " + title + " | ✍ " + author + " | 💲 $" + price +
                " | 🛒 Quantity: " + quantity + " | 🔢 ISBN: " + isbn);
    }
}

// Implementing OrderQueue using linked list


// CLASS ORDER
class Order {
    int orderNumber;
    String customerName;
    String customerAddress;
    Book[] books;
    int[] quantities;
    int bookCount;
    double totalPrice;
    String status;

    public Order(int orderNumber, String customerName, String customerAddress, Book[] books, int[] quantities, int bookCount, double totalPrice) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.books = books;
        this.quantities = quantities;
        this.bookCount = bookCount;
        this.totalPrice = totalPrice;
        this.status = "processing";
    }

    // FUNCTION FOR STATUS COMPLETED
    public void completeStatusOrder() {
        this.status = "Completed";
    }


    private void quickSortBooksInOrder(int left, int right) {
        if (left >= right) return;

        int pivot = quantities[(left + right) / 2];
        int i = left, j = right;

        while (i <= j) {
            while (quantities[i] < pivot) i++; // Tìm phần tử lớn hơn pivot
            while (quantities[j] > pivot) j--; // Tìm phần tử nhỏ hơn pivot
            if (i <= j) {
                // Hoán đổi số lượng
                int tempQty = quantities[i];
                quantities[i] = quantities[j];
                quantities[j] = tempQty;

                // Hoán đổi luôn sách tương ứng
                Book tempBook = books[i];
                books[i] = books[j];
                books[j] = tempBook;

                i++;
                j--;
            }
        }

        quickSortBooksInOrder(left, j); // Đệ quy phần trái
        quickSortBooksInOrder(i, right); // Đệ quy phần phải
    }

    // DISPLAY ORDER INFORMATION OUT FUNCTION
    public void displayOrderInformation() {
        quickSortBooksInOrder(0, bookCount - 1); // Sắp xếp trước khi hiển thị

        System.out.println("\n===== 🛍 Order Summary =====");
        System.out.println("🆔 Order Number: " + orderNumber);
        System.out.println("👤 Customer: " + customerName);
        System.out.println("🏠 Address: " + customerAddress);
        System.out.println("📚 Books Ordered:");

        for (int i = 0; i < bookCount; i++) {
            System.out.println("- " + books[i].title + " (ISBN: " + books[i].isbn + ") x " + quantities[i]);
        }

        System.out.println("💲 Total Price: $" + String.format("%.2f", totalPrice));
        System.out.println("📌 Status: " + status);
        System.out.println("=========================");
    }
}

// CLASS TRANSACTION QUEUE
class TransactionQueue {
    OrderQueue completedOrders = new OrderQueue();
    // ADD TRANSACTION FUNCTION
    public void addTransaction(Order transactionOrder) {
        completedOrders.offer(transactionOrder);
    }

    // DISPLAY TRANSACTION FUNCTION
    public void displayTransactions() {
        System.out.println("\n===== 📜 Transaction History =====");
        completedOrders.displayAllOrders();
    }
}

// MENU CLASS
public class BookstoreMenu {
    static BookList bookList = new BookList();
    static OrderQueue orderQueue = new OrderQueue();
    static TransactionQueue transactionQueue = new TransactionQueue();

    // FUNCTION INITALIZE BOOK
    private static void initializeBooks() {
        bookList.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 10.99, 5, "GAT1"));
        bookList.add(new Book("To Kill a Mockingbird", "Harper Lee", 8.99, 3, "TKM2"));
        bookList.add(new Book("1984", "George Orwell", 9.99, 7, "ORW3"));
        bookList.add(new Book("Pride and Prejudice", "Jane Austen", 7.99, 4, "PAP4"));
        bookList.add(new Book("Moby-Dick", "Herman Melville", 11.99, 2, "MOB5"));
    }

    // DISPLAY MENU
    private static void displayMenu() {
        System.out.println("\nWelcome to the Online Bookstore!");
        System.out.println("1. Browse Books & Place Order");
        System.out.println("2. Track All Orders");
        System.out.println("3. Process an Order");
        System.out.println("4. View Transactions");
        System.out.println("5. Track User's Order");
        System.out.println("6. Add Book");
        System.out.println("7. Remove Book");
        System.out.println("8. Exit");
        System.out.print("➡️ Enter your choice (1-8): ");
    }

    // GET VALID CHOICE FOR DISPLAY MENU
    private static int getValidChoiceMenu(Scanner scanner) {
        int choice = -1;

        while (true) {
            try {
                String input = scanner.nextLine().trim();

                // Kiểm tra input chỉ chứa số từ 1-6
                if (input.matches("[1-8]")) {
                    choice = Integer.parseInt(input);
                    break;
                } else {
                    System.out.print("❌ Invalid choice! Please enter a number between 1 and 8: ");
                }
            } catch (Exception e) {
                System.out.print("❌ Invalid input! Please enter a number (1-8): ");
                scanner.nextLine(); // Xóa bộ nhớ đệm để tránh lỗi lặp vô hạn
            }
        }

        return choice;
    }

    private static void addBookToSystem(Scanner scanner) {

        String title, author, isbn;
        double price = -1;
        int quantity = -1;

        bookList.displayAllBook();
        // Ask for book details
        System.out.print("Enter book title: ");
        title = scanner.nextLine();

        System.out.print("Enter book author: ");
        author = scanner.nextLine();

        // Loop until a unique ISBN is entered
        while (true) {
            System.out.print("Enter book ISBN: ");
            isbn = scanner.nextLine().toUpperCase();

            // Check if ISBN already exists
            if (bookList.contains(isbn)) {
                System.out.println("❌ ISBN already exists! Please use a different ISBN.");
            } else {
                break;  // Exit the loop when a unique ISBN is entered
            }
        }

        // Validate price
        while (price <= 0) {
            System.out.print("Enter book price: ");
            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble();
                if (price <= 0) {
                    System.out.println("❌ Price must be greater than 0. Please try again.");
                }
            } else {
                System.out.println("❌ Invalid price. Please enter a valid number.");
                scanner.next(); // Consume invalid input
            }
        }

        // Validate quantity
        while (quantity <= 0) {
            System.out.print("Enter book quantity: ");
            if (scanner.hasNextInt()) {
                quantity = scanner.nextInt();
                if (quantity <= 0) {
                    System.out.println("❌ Quantity must be greater than 0. Please try again.");
                }
            } else {
                System.out.println("❌ Invalid quantity. Please enter a valid number.");
                scanner.next(); // Consume invalid input
            }
        }

        scanner.nextLine(); // Clear buffer after numeric input

        // Create the new Book object and add it to the list
        Book newBook = new Book(title, author, price, quantity, isbn);
        if (bookList.add(newBook)) {
            System.out.println("✅ Book added successfully.");
        } else {
            System.out.println("❌ Failed to add book.");
        }
    }

    private static void removeBookFromSystem(Scanner scanner) {
        bookList.displayAllBook();

        System.out.print("Enter book ISBN to remove: ");
        String isbn = scanner.nextLine().toUpperCase();

        // Check if book exists
        if (!bookList.contains(isbn)) {
            System.out.println("❌ Book with ISBN " + isbn + " not found.");
            return;
        }

        System.out.print("Are you sure you want to remove the book with ISBN " + isbn + "? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes")) {
            if (bookList.remove(isbn)) {
                System.out.println("✅ Book removed successfully.");
            } else {
                System.out.println("❌ Failed to remove book.");
            }
        } else {
            System.out.println("❌ Action cancelled.");
        }
    }

    // BROWSE AND ORDER BOOK FUNCTION
    private static void browseAndOrder(Scanner scanner) {
        System.out.println("========================================================");
        bookList.displayAllBook();
        System.out.println("========================================================");

        String customerName = getValidInputNameAddress(scanner, "Enter your name: ");
        String customerAddress = getValidInputNameAddress(scanner, "Enter your address: ");

        System.out.println("\n✅ Name: " + customerName);
        System.out.println("✅ Address: " + customerAddress);

        Book[] orderedBooks = new Book[10];
        int[] orderedQuantities = new int[10];
        int bookCount = 0;
        double totalPrice = 0;

        while (true) {
            System.out.print("\nEnter the ISBN of the book you want to order: ");
            String isbnInput = scanner.nextLine().toUpperCase();

            Book selectedBook = null;
            for (int i = 0; i < bookList.size(); i++) {
                Book book = bookList.get(i);
                if (book.isbn.equalsIgnoreCase(isbnInput)) {
                    selectedBook = book;
                    break;
                }
            }

            if (selectedBook == null) {
                System.out.println("❌ Invalid ISBN. Please try again.");
                continue;
            }

            if (selectedBook.quantity == 0) {
                System.out.println("❌ This book is out of stock.");
                continue;
            } else if (selectedBook.quantity == 1) {
                System.out.println("⚠️ Only 1 copy left in stock.");
            } else {
                System.out.println("📖 Available quantity: " + selectedBook.quantity);
            }

            int orderQuantity = -1;

            while (true) {
                System.out.print("Enter quantity: ");

                if (scanner.hasNextInt()) {
                    orderQuantity = scanner.nextInt();
                    scanner.nextLine();

                    if (orderQuantity > 0 && orderQuantity <= selectedBook.quantity) {
                        break;
                    } else if (orderQuantity > selectedBook.quantity) {
                        System.out.println("❌ Not enough stock. Only " + selectedBook.quantity + " copies available.");
                    } else {
                        System.out.println("❌ Invalid quantity. Please enter a positive number.");
                    }
                } else {
                    System.out.println("❌ Invalid input! Please enter a valid number.");
                    scanner.next();
                }
            }

            orderedBooks[bookCount] = selectedBook;
            orderedQuantities[bookCount] = orderQuantity;
            totalPrice += orderQuantity * selectedBook.price;
            selectedBook.quantity -= orderQuantity;
            bookCount++;

            while (true) {
                System.out.print("\nDo you want to order another book? (yes/no): ");
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("yes")) {
                    break; // Continue ordering
                } else if (input.equals("no")) {
                    Order newOrder = new Order(orderQueue.size() + 1, customerName, customerAddress, orderedBooks, orderedQuantities, bookCount, totalPrice);
                    orderQueue.offer(newOrder);
                    System.out.println("\n✅ Order placed successfully!");
                    newOrder.displayOrderInformation();
                    return; // Exit function
                } else {
                    System.out.println("❌ Invalid input! Please enter 'yes' or 'no'.");
                }
            }



        }



    }

    // FUNCTION GET VALID INPUT
    private static String getValidInputNameAddress(Scanner scanner, String message) {
        String input;

        while (true) {
            System.out.print(message);
            input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                break; // Hợp lệ thì thoát vòng lặp
            }
            System.out.println("❌ Invalid input! This field cannot be empty. Please try again.");
        }

        return input;
    }

    // SEARCH ORDER FUNCTION  AND VALIDATION
    private static void searchMenuOrders(Scanner scanner) {
        int choice = -1;
        while (true) {
            try {
                System.out.println("\n🔎 Search Order by:");
                System.out.println("1. Order Number");
                System.out.println("2. Customer Name");
                System.out.println("3. Customer Address");
                System.out.println("4. Book ISBN");
                System.out.print("➡️ Enter your choice: ");

                String input = scanner.nextLine().trim(); // Đọc input và loại bỏ khoảng trắng thừa

                // Kiểm tra nếu input chỉ chứa số từ 1-4
                if (input.matches("[1-4]")) {
                    choice = Integer.parseInt(input);
                    break; // Nếu hợp lệ, thoát vòng lặp
                } else {
                    System.out.println("❌ Invalid choice! Please enter a number between 1 and 4.");
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please enter a valid number (1-4).");
                scanner.nextLine(); // Xóa bộ nhớ đệm của Scanner để tránh lỗi lặp vô hạn
            }
        }

        int orderNumber = -1;
        String name = null, address = null, isbn = null;

        switch (choice) {
            case 1:
                System.out.print("🔢 Enter order number: ");
                orderNumber = scanner.nextInt();
                scanner.nextLine();
                break;
            case 2:
                System.out.print("👤 Enter customer name: ");
                name = scanner.nextLine();
                break;
            case 3:
                System.out.print("🏠 Enter customer address: ");
                address = scanner.nextLine();
                break;
            case 4:
                System.out.print("📚 Enter book ISBN: ");
                isbn = scanner.nextLine();
                break;
            default:
                System.out.println("❌ Invalid choice.");
                return;
        }

        Order[] matchingOrders = new Order[100];
        int matchCount = 0;

        matchCount = searchOrdersInQueue(orderQueue, orderNumber, isbn, address, name, matchingOrders, matchCount);
        matchCount = searchOrdersInQueue(transactionQueue.completedOrders, orderNumber, isbn, address, name, matchingOrders, matchCount);

        if (matchCount > 0) {
            System.out.println("\n✅ Found Orders:");
            for (int i = 0; i < matchCount; i++) {
                matchingOrders[i].displayOrderInformation();
            }
        } else {
            System.out.println("❌ No matching orders found.");
        }
    }

    // LOGIC FOR SEARCH FUNCTION
    private static int searchOrdersInQueue(OrderQueue queue, int orderNumber, String isbn, String address, String name, Order[] matchingOrders, int matchCount) {
        OrderQueue.Node current = queue.head;
        while (current != null) {
            Order order = current.order;
            if ((orderNumber > 0 && order.orderNumber == orderNumber) ||
                    (isbn != null && containsISBN(order, isbn)) ||
                    (order.customerAddress.equalsIgnoreCase(address)) ||
                    (order.customerName.equalsIgnoreCase(name))) {
                matchingOrders[matchCount] = order;
                matchCount++;
            }
            current = current.next;
        }
        return matchCount;
    }

    private static boolean containsISBN(Order order, String isbn) {
        for (Book book : order.books) {
            if (book != null && book.isbn.equalsIgnoreCase(isbn)) {
                return true;
            }
        }
        return false;
    }


    // TRACK ORDERS
    private static void trackOrders() {
        orderQueue.displayAllOrders();
    }

    // PROCESS ORDERS FUNCTION
    private static void processOrders() {
        if (!orderQueue.isEmpty()) {
            System.out.println("\nProcessing order...");
            Order processedOrder = orderQueue.poll();
            processedOrder.completeStatusOrder(); // Mark order as Completed
            transactionQueue.addTransaction(processedOrder); // Move to transactions
            processedOrder.displayOrderInformation();
            System.out.println("✅ Order has been completed and recorded in transactions.");
        } else {
            System.out.println("❌ No orders to process.");
        }
    }
    private static void viewTransactions() {
        transactionQueue.displayTransactions();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeBooks();
        while(true) {
            displayMenu();
            int choice = getValidChoiceMenu(scanner);
            switch (choice) {
                case 1:
                    browseAndOrder(scanner);
                    break;
                case 2:
                    trackOrders();
                    break;
                case 3:
                    processOrders();
                    break;
                case 4:
                    viewTransactions();
                    break;
                case 5:
                    searchMenuOrders(scanner);
                    break;
                case 6:
                    addBookToSystem(scanner);
                    break;
                case 7:
                    removeBookFromSystem(scanner);
                    break;
                case 8:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
