package yang;

public interface IPriorQueue<T> {
    public T getTop();
    public void add(T data);
    public void remove(int position);
    public int getSize();
}
