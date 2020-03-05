package com.jyotirmayadas.dynamicarray;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class DynamicArray<T> {

    private T[] arr;
    private int len = 0;    // length user thinks of array is
    private int capacity = 0;   // actual array size

    public DynamicArray() {
        this(16);
    }

    public DynamicArray(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        this.capacity = capacity;
        this.arr = (T[]) new Object[capacity];
    }

    public int size() {
        return len;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T get(int index) {
        return arr[index];
    }

    public void set(int index, T element) {
        arr[index] = element;
    }

    public void clear() {
        IntStream.range(0, capacity)
                .forEach(value -> this.arr[value] = null);
    }

    public void add(T element) {

        // Time to resize
        if (len + 1 >= capacity) {
            if (capacity == 0) capacity = 1;
            else capacity *= 2; // double the size

            T[] newArr = (T[]) new Object[capacity];

            IntStream.range(0, len)
                    .forEach(index -> newArr[index] = arr[index]);

            arr = newArr;
        }

        arr[len++] = element;
    }

    // Removes an element at the specified index in this array.
    public T removeAt(int rmIndex) {
        if (rmIndex >= len || rmIndex < 0) throw new IndexOutOfBoundsException();
        T data = arr[rmIndex];
        T[] new_arr = (T[]) new Object[len - 1];
        for (int i = 0, j = 0; i < len; i++, j++)
            if (i == rmIndex) j--; // Skip over rm_index by fixing j temporarily
            else new_arr[j] = arr[i];
        arr = new_arr;
        capacity = --len;
        return data;
    }

    public int indexOf(Object obj) {
        for (int i = 0; i < len; i++) {
            if (obj == null) {
                if (arr[i] == null) return i;
            } else {
                if (obj.equals(arr[i])) return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DynamicArray.class.getSimpleName() + "[", "]")
                .add("arr=" + Arrays.toString(arr))
                .add("len=" + len)
                .add("capacity=" + capacity)
                .toString();
    }

    //  Example usage
    public static void main(String[] args) {
        DynamicArray<Integer> integerDynamicArray = new DynamicArray<>(10);
        System.out.println(integerDynamicArray.len);
        System.out.println(integerDynamicArray.capacity);

        integerDynamicArray.add(10);
        integerDynamicArray.add(20);
        integerDynamicArray.add(-30);

        System.out.println(integerDynamicArray);

        System.out.println(integerDynamicArray.isEmpty());
        System.out.println(integerDynamicArray.indexOf(20));
        System.out.println(integerDynamicArray.removeAt(0));

        System.out.println(integerDynamicArray);
    }
}