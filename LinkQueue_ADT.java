package CW;

public interface LinkQueue_ADT<Order> {
    void offer(Order order);
    Order poll();
    Order peek();
    int size();
    boolean  isEmpty();

}
